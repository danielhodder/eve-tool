delimiter $$
CREATE FUNCTION calculate_materials (materialAmount INT(11), baseWasteFactor INT(11), materialEfficiency INT(11))
  RETURNS INT(11) DETERMINISTIC
BEGIN
  # Can't use a double here as we get annoying rounding errors...
  DECLARE wasteEffect INT;
  IF materialEfficiency < 0 THEN
    SET wasteEffect = ROUND(materialAmount * baseWasteFactor / 100 * (1 - materialEfficiency));
  ELSE
    SET wasteEffect = ROUND(materialAmount * baseWasteFactor / 100 * (1 / (materialEfficiency + 1)));
  END IF;
  RETURN materialAmount + wasteEffect;
END$$

delimiter ;

CREATE TABLE Blueprint (
  id int(11) NOT NULL AUTO_INCREMENT,
  blueprintTypeID int(11) NOT NULL,
  numberPerRun int(11) NOT NULL,
  hours int(11) NOT NULL,
  saleValue decimal(65,2) NOT NULL,
  materialEfficiency int(11) NOT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB;

CREATE VIEW BlueprintSubTypeRequirements AS
  SELECT
    bp.id as blueprintId,
    itm.materialTypeID as materialTypeID,
    sum(itm.quantity) as rawQuantity
  FROM Blueprint bp
    JOIN `eve-dump`.invBlueprintTypes ibt ON ibt.blueprintTypeID = bp.blueprintTypeID
    JOIN `eve-dump`.invTypes it ON it.typeID = ibt.blueprintTypeID
    JOIN `eve-dump`.ramTypeRequirements ram on ram.typeID = it.typeID
    JOIN `eve-dump`.invTypes ramT ON ram.requiredTypeID = ramT.typeID
    JOIN `eve-dump`.invGroups ramG ON ramG.groupID = ramT.groupID
    JOIN `eve-dump`.ramActivities ramA on ramA.activityID = ram.activityID
    JOIN `eve-dump`.invTypeMaterials itm on itm.typeID = ramT.typeID
  WHERE
    ramA.activityName = 'Manufacturing'
    AND ramG.groupName != 'Tool'
  GROUP BY bp.id, itm.materialTypeID;

CREATE VIEW BlueprintTypes AS
  SELECT
    bp.id as blueprintId,
    itm.materialTypeID as materialTypeID,
    calculate_materials(itm.quantity - ifnull(bstr.rawQuantity, 0), ibt.wasteFactor, bp.materialEfficiency) as units,
    mat.typeName
  FROM Blueprint bp
    JOIN `eve-dump`.invBlueprintTypes ibt ON ibt.blueprintTypeID = bp.blueprintTypeID
    JOIN `eve-dump`.invTypes it ON it.typeID = ibt.productTypeID
    JOIN `eve-dump`.invTypeMaterials itm on itm.typeID = it.typeID
    JOIN `eve-dump`.invTypes mat ON mat.typeID = itm.materialTypeID
    LEFT OUTER JOIN BlueprintSubTypeRequirements bstr 
      ON bstr.blueprintId = bp.id
      AND bstr.materialTypeID = itm.materialTypeID
  WHERE
    bstr.rawQuantity IS NULL OR itm.quantity - bstr.rawQuantity != 0
  UNION
  SELECT 
    bp.id as blueprintId,
    ram.requiredTypeID as materialTypeID,
    ram.quantity as units,
    ramT.typeName
  FROM Blueprint bp
    JOIN `eve-dump`.invBlueprintTypes ibt ON ibt.blueprintTypeID = bp.blueprintTypeID
    JOIN `eve-dump`.invTypes it ON it.typeID = ibt.blueprintTypeID
    JOIN `eve-dump`.ramTypeRequirements ram on ram.typeID = it.typeID
    JOIN `eve-dump`.invTypes ramT ON ram.requiredTypeID = ramT.typeID
    JOIN `eve-dump`.invGroups ramG ON ramG.groupID = ramT.groupID
    JOIN `eve-dump`.invCategories ramC ON ramC.categoryID = ramG.categoryID
    JOIN `eve-dump`.ramActivities ramA on ramA.activityID = ram.activityID
  WHERE
    ramA.activityName = 'Manufacturing'
    AND ramC.categoryName = 'Commodity';


CREATE TABLE Type (
  id int(11) NOT NULL AUTO_INCREMENT,
  typeID int(11) NOT NULL,
  cost decimal(65,2) NOT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB;


CREATE VIEW BlueprintTypeCosts AS
  select 
    bp.id AS blueprintId,
    it.typeName AS blueprintName,
    mt.typeName AS typeName,
    bt.units AS units,
    (bt.units * t.cost) AS cost
  from Blueprint bp
    join BlueprintTypes bt on bt.blueprintId = bp.id
    left outer join Type t on t.typeID = bt.materialTypeID
    JOIN `eve-dump`.invBlueprintTypes ibt ON ibt.blueprintTypeID = bp.blueprintTypeID
    JOIN `eve-dump`.invTypes it ON it.typeID = ibt.productTypeID
    JOIN `eve-dump`.invTypes mt ON mt.typeID = bt.materialTypeID;

CREATE VIEW BlueprintCosts AS
  select
    bp.id AS blueprintId,
    btc.blueprintName AS blueprintName,
    sum(btc.cost) AS materialCost,
    (((ral.costPerHour * bp.hours) + ral.costInstall) / bp.numberPerRun) AS otherCost
  from Blueprint bp
    left join BlueprintTypeCosts btc on btc.blueprintId = bp.id
    JOIN `eve-dump`.ramAssemblyLines ral ON ral.assemblyLineID = 1 # They're all the same!
  group by bp.id;


CREATE VIEW BlueprintSummary AS
  select
    bp.id AS blueprintId,
    bc.blueprintName AS blueprintName,
    cast((bc.materialCost + bc.otherCost) as decimal(65,2)) AS cost,
    bp.saleValue AS saleValue,
    cast((bp.saleValue - (bc.materialCost + bc.otherCost)) as decimal(65,2)) AS profit,
    cast(((100 * (bp.saleValue - (bc.materialCost + bc.otherCost))) / bp.saleValue) as decimal(5,2)) AS profitPercentage
  from Blueprint bp
    join BlueprintCosts bc on bp.id = bc.blueprintId
  group by bp.id;



