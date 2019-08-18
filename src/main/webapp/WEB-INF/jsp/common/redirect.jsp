<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script>
var alertMsg = '${alertMsg}'.trim();
var historyBack = '${historyBack}' == 'true';
var locationReplaceUrl = '${locationReplaceUrl}'.trim();

if ( alertMsg ) {
	alert(alertMsg);
}

if ( locationReplaceUrl ) {
	location.replace(locationReplaceUrl);
}

if ( historyBack ) {
	history.back();
}
</script>

</head>
<body>
</body>
</html>