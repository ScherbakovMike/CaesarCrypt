<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
    <title>Stat analisys decrypt</title>
</head>
<body>
<div align="center">
    <h2>Crypt new data</h2>
    <form:form action="decrypt_statanalysis" method="post" modelAttribute="cryptedData" enctype="multipart/form-data">
        <table border="0" cellpadding="5">
            <tr>
                <td><label>Select a dictionary file</label></td>
                <td><input type="file" name="file" /></td>
            </tr>
            <tr>
                <td>Crypted item id: </td>
                <td>${cryptedData.id}
                    <form:hidden path="id"/>
                </td>
            </tr>
            <tr>
                <td colspan="2"><input type="submit" value="Decrypt"></td>
            </tr>
        </table>
    </form:form>
</div>
</body>
</html>
