<%@ page contentType="text/html;charset=UTF-8" %>
<html lang="ru">
<head>
    <title>Edit meal</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Edit meal</h2>
<form action="${pageContext.request.contextPath}/meals" method="post">
    <input type="hidden" name="id" value=<%=request.getAttribute("id")%>>
    <input type="hidden" name="isExcess" value=<%=request.getAttribute("isExcess")%>>
    <p>
        <label for="date">DateTime: </label>
        <input type="datetime-local" id="date" name="date" value=<%=request.getAttribute("date")%>>
    </p>
    <p>
        <label for="description">Description: </label>
        <input type="text" id="description" name="description" value=<%=request.getAttribute("description")%>>
    </p>
    <p>
        <label for="calories">Calories: </label>
        <input type="text" id="calories" name="calories" value=<%=request.getAttribute("calories")%>>
    </p>
    <p>
        <input type="submit" name="save" value="Save">
        <a href="${pageContext.request.contextPath}/meals"> <button type="button">Cancel</button></a>
    </p>
</form>
</body>
</html>