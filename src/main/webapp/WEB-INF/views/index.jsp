<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01
Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Crypto Manager</title>
</head>
<body>
<div align="center">
    <h2>Crypto Manager</h2>
    <h3><a href="./new">Crypt new data</a></h3>
    <table border="1" cellpadding="5">
        <tr>
            <th>ID</th>
            <th>DateTime</th>
            <th>Source text</th>
            <th>Crypted text</th>
            <th>Key</th>
            <th>Action</th>
        </tr>
        <c:forEach items="${listCryptedData}" var="cd">
            <tr>
                <td>${cd.id}</td>
                <td>${cd.datetime}</td>
                <td>${cd.source_text}</td>
                <td>${cd.crypted_text}</td>
                <td>${cd.key}</td>
                <td><a href="./delete?id=${cd.id}">Delete</a></td>
            </tr>
        </c:forEach>
    </table>
</div>
</body>
</html>