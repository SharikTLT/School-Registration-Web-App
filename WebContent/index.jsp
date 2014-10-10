<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Home</title>
</head>
<body>

<div>
You are logged-in as <strong>${ user.name }</strong>, with User ID <strong>${ user.userId }</strong> and type <strong>${ user.type }</strong>.
</div>

<div>
<h2>What would you like to do?</h2>
<ul>
	<li><strong><a href="${pageContext.request.contextPath}/showSections">Enlist Sections</a></strong></li>
</ul>
</div>

</body>
</html>