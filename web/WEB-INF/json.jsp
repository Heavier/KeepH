<%-- 
    Document   : json
    Created on : 03-feb-2016, 14:24:45
    Author     : 2dam
--%>
<%
    String cadenaJSON = (String) request.getAttribute("json");
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        
    </head>
    <body>
        <%
            cadenaJSON.toString();
        %>
    </body>
</html>
