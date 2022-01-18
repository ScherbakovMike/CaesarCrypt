<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Crypt new data</title>
</head>
<body>
<div align="center">
    <h2>Crypt new data</h2>
    <form:form action="crypt" method="post" modelAttribute="cryptedData">
        <table border="0" cellpadding="5">
            <tr>
                <td>Text to crypt: </td>
                <td><form:textarea cols="40" rows="5" path="source_text" /></td>
            </tr>
            <tr>
                <td>Key: </td>
                <td><form:input path="key" value="15"/></td>
            </tr>
            <tr>
                <td colspan="2"><input type="submit" value="Crypt"></td>
            </tr>
        </table>
    </form:form>
</div>
</body>
</html>