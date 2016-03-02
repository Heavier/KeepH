<%-- 
    Document   : create
    Created on : 02-mar-2016, 12:11:13
    Author     : 2dam
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Crear usuario nuevo</h1>
        <form name="formulario" action="go?tabla=usuario&op=create&accion=do" method="POST">
        <input type="text" name="login" placeholder="Nombre de usuario" />
        <input type="text" name="pass" placeholder="Contrase&ntilde;a de usuario" />
        <input type="submit" value="Crear" name="submit"/>
        </form>
    </body>
</html>
