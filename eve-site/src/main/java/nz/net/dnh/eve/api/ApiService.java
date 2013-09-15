package nz.net.dnh.eve.api;

import java.util.Collection;

/**
 * Defines the things that can be executed against the EVE Online API
 * 
 * @author Daniel Hodder (danielh)
 * 
 */
public interface ApiService {
	/**
	 * Gets all the corporation's assets from the EVE Api. This retains the hierarchy of containers in it's response
	 * 
	 * @return
	 */
	Collection<AssetType> getCorporationAssets();
}
