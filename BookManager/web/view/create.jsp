<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Create New Book</title>
</head>
<body>
    <h2>Create a New Book</h2>
    <form action="${pageContext.request.contextPath}/book/create" method="POST">
        <label for="BookName">Book Name:</label>
        <input type="text" id="BookName" name="BookName" required>
        <br><br>
        
        <label>Authors:</label><br>
        <c:forEach var="author" items="${sessionScope.authors}">
            <input type="checkbox" name="authors" value="${author.authorID}">
            ${author.authorName}<br>
        </c:forEach>
        <br>
        Is Available
        <br>
        <input type="radio" name="availability" value="True" checked> True
        <input type="radio" name="availability" value="False"> False

        <input type="submit" value="Save">
    </form>
</body>
</html>