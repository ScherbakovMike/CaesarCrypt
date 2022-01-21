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
    <form:form action="save" method="post" modelAttribute="cryptedData" enctype="multipart/form-data">
        <table border="0" cellpadding="5">
            <tr>
                <td><label>Select a file to crypt</label></td>
                <td><input type="file" name="file" /></td>
            </tr>
            <tr>
                <td>Key: </td>
                <td><form:input path="crypt_key"/></td>
            </tr>
            <tr>
                <td colspan="2"><input type="submit" value="Crypt"></td>
            </tr>
        </table>
    </form:form>
</div>
</body>
</html>