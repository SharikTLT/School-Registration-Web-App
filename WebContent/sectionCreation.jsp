<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Section Creation</title>
</head>
<body>
<h1>Section Creation</h1>
<h2>Please select teacher, subject, and schedule</h2>
<form action="${pageContext.request.contextPath}/SectionCreation" method="post">
<p>Teacher:
<select name="facultyNumber">
<c:forEach var="faculty" items="${facultyList}">
 <option value="${ faculty.facultyNumber }"> ${ faculty.facultyNumber }</option>
</c:forEach>
</select>
</p>
<input type="submit" value="Submit">
</form>
</body>
</html>