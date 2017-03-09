<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <link rel="shortcut icon" type="image/x-icon" href="/static/favicon.ico">
    <title>Spring-boot JSP</title>
</head>
<body>
    <fmt:formatDate value="${createTime}" pattern="yyyy-MM-dd HH:mm:ss" /> <br>
    ${user.userId} <br>
    ${user.userName} <br>
    ${user.sex} <br>
    ${user.age} <br>
    Message: ${message} <br/>
    <img src="/image/Redis.png">
</body>
</html>