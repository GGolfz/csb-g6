<%--
  Created by IntelliJ IDEA.
  User: ggolfz
  Date: 4/4/2020 AD
  Time: 21:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>$Title$</title>
  </head>
  <body>
  <form method="GET" action="GetOwnerServlet">
    <input type="text" name="ownerID">
    <input type="submit">
  </form>
  <form method="GET" action="GetAddressServlet">
    <input type="text" name="ownerID">
    <input type="submit">
  </form>
  </body>
</html>
