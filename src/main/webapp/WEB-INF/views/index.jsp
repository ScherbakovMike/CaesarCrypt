<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01
Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Crypto Manager</title>

    <style>
        table {
            width: 100%;
            border-collapse: collapse;
            border: 1px solid black;
        }

        td, th {
            text-align: center;
            vertical-align: middle;
            border: 1px solid black;
        }
    </style>
</head>
<body>
<div align="center">
    <h2>Crypto Manager</h2>
    <h3><a href="./new">Crypt new data</a></h3>
    <table>
        <tr>
            <th>ID</th>
            <th>Transaction time</th>
            <th>Source text</th>
            <th>Crypted text</th>
            <th>Crypt key</th>
            <th>Crypt result</th>
            <th>Action</th>
        </tr>
        <c:forEach items="${listCryptedData}" var="cd">
            <tr>
                <td>${cd.id}</td>
                <td>${cd.transaction_time}</td>
                <td>${cd.source_text==null?"":cd.source_text.length()>20?cd.source_text.substring(0,20):cd.source_text}...</td>
                <td>${cd.crypted_text==null?"":cd.crypted_text.length()>20?cd.crypted_text.substring(0,20):cd.crypted_text}...</td>
                <td>${cd.crypt_key}</td>
                <td>${cd.crypt_result}</td>
                <td><a href="./delete?id=${cd.id}">Delete</a><br>
                    <a href="./saveresult?id=${cd.id}">Save result</a><br>
                    <a href="./decrypt_bruteforce?id=${cd.id}">Decrypt by bruteforce</a><br>
                    <a href="./loadDictionary?id=${cd.id}">Decrypt by stat analysis</a><br>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>
</body>
</html>