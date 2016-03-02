<%-- 
    Document   : create
    Created on : 02-mar-2016, 12:11:13
    Author     : 2dam
--%>
<%
    String s = (String) request.getParameter("login");
    %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Crear usuario nuevo</h1>
        <form name="formulario" action="go?tabla=keep&op=create&accion=do" method="POST">
            <textarea name="contenido" rows="4" cols="20"></textarea>
            <input type="hidden" name="login" value="<%= s %>"/>
            <input type="submit" value="Crear" name="submit"/>            
        </form>
    </body>
</html>
