<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles-extras" prefix="tilesx"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<div class="row-fluid">
	<div class="span12">
		<h1>Login</h1>
		
		<c:if test="${not empty failed}">
			<div class="alert alert-error">
				Your login attempt was not successful, try again.<br /> Caused :
				${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}
			</div>
		</c:if>
	</div>
	
	<div class="span12 center">	
		<c:url value='/login' var="url" />
		<form:form name='f' action="${url}" method='POST'>
			<fieldset class="block-fields">
				<input type='text' id="j_username" name='username' placeholder="Username" class="input-xlarge" />
				<input type='password' id="j_password" name='password' placeholder="Password" class="input-xlarge" />
				<button type="submit" class="btn btn-primary btn-block">Login</button>
			</fieldset>
		</form:form>
	</div>
</div>