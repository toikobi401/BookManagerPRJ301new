<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Search Books</title>
</head>
<body>
    <form action="${pageContext.request.contextPath}/book/search" method="post">
        <table>
            <tr>
                <td>Book Name</td>
                <td><input type="text" name="bookName"></td>
            </tr>
            <tr>
                <td>Is Available</td>
                <td>
                    <input type="radio" name="available" value="true"> True
                    <input type="radio" name="available" value="false"> False
                    <input type="radio" name="available" value="all" id="all" checked> All
                </td>
            </tr>
            <tr>
                <td>
                    Published from
                    <input type="date" name="publishedFrom">
                </td>
                <td>
                    to
                    <input type="date" name="publishedTo">
                </td>
            </tr>
            <tr>
                <td>Authors</td>
                <td>
                    <c:forEach var="author" items="${sessionScope.authors}">
                        <input type="checkbox" name="authors" value="${author.authorID}">
                        <c:out value="${author.authorName}" /><br>
                    </c:forEach>
                </td>
            </tr>
            <tr>
                <td></td>
                <td><input type="submit" value="Search"></td>
            </tr>
        </table>
    </form>
</body>
</html>