<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Search Results</title>
</head>
<body>
    <h1>Search Results</h1>
    <table border="1">
        <tr>
            <th>Book ID</th>
            <th>Book Name</th>
            <th>Created By</th>
            <th>Created Date</th>
            <th>Availability</th>
        </tr>
        <c:forEach var="book" items="${requestScope.books}">
            <tr>
                <td>${book.bookID}</td>
                <td>${book.bookName}</td>
                <td>${book.createdby}</td>
                <td>${book.createdAt}</td>
                <td>${book.available}</td>
            </tr>
        </c:forEach>
    </table>
    <a href="${pageContext.request.contextPath}/book/search">Back to Search</a>
</body>
</html>