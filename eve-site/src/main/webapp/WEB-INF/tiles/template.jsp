<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles-extras" prefix="tilesx"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" lang="en">
	<head>
		<title>EVE Blueprint Tool</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<link href="//netdna.bootstrapcdn.com/twitter-bootstrap/2.3.1/css/bootstrap-combined.min.css" rel="stylesheet">
		<link href="<c:url value="/resources/css/core.css" />" rel="stylesheet" media="screen" />
		
		<script src="http://code.jquery.com/jquery-1.9.1.min.js"></script>
	    <script src="//netdna.bootstrapcdn.com/twitter-bootstrap/2.3.1/js/bootstrap.min.js"></script>
		
		<tilesx:useAttribute id="styles" name="styles" classname="java.util.List" ignore="true" />
		<c:forEach var="cssName" items="${styles}">
			<link type="text/css" href="<c:url value="/resources/css/${cssName}"/>" rel="stylesheet" media="screen" />
		</c:forEach>
		
		<!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
	    <!--[if lt IE 9]>
	      <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
	    <![endif]-->
	</head>
	
	<body>
		<tiles:insertAttribute name="header"  defaultValue="" />
		
		<!-- Page content -->
		
		<div class="container">
			<tiles:insertAttribute name="body" defaultValue="" />
		</div>
		
		<!-- End of page content -->
		
		<tiles:insertAttribute name="footer"  defaultValue="" />
	</body>
</html>