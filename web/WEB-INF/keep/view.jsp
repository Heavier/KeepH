<%@page import="hibernate.Usuario"%>
<%@page import="gestion.GestorUsuario"%>
<%@page import="hibernate.Keep"%>
<%@page import="java.util.List"%>

<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%

    List<Keep> lista = (List<Keep>) request.getAttribute("listado");
    GestorUsuario gu = new GestorUsuario();
    List<Usuario> lus = gu.getUsuarios();
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="../css/style.css"/>
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Lista de notas</h1>

        <br>
        <a href="go?tabla=usuario&op=read&accion=view">Ir a usuarios</a>
        <br>
        <br>
        <table>
            <thead>
                <tr>
                    <th>Contenido</th>
                </tr>
            </thead>
            <tbody>
                <%for (Keep p : lista) {
                %>    
                <tr>
                    <td><%= p.getContenido() %></td>
                    <td><a href="go?tabla=keep&op=delete&accion=do&id=<%= p.getId()%>" class="borrar">Eliminar</a></td>
                </tr>
                <%
                    }
                %>
            </tbody>
        </table>
            <br>
            <br>
            <br>
            <br>
                <%for (Usuario o : lus) {
                %>    
                <tr>
                    <td><%= o.getLogin() %></td>
                    <td><a href="go?tabla=keep&op=read&accion=view&login=<%= o.getLogin() %>">Seleccionar</a></td>
                    <td><a href="go?tabla=keep&op=create&accion=view&login=<%= o.getLogin() %>">Nueva nota</a></td>
                <br>    
                </tr>
                <%
                    }
                %>
        <script>
            var elementos = document.getElementsByClassName("borrar");
            for(var i = 0; i<elementos.length;i++){
                agregarEventoClick(elementos[i]);
            }
        function agregarEventoClick(elemento){
            elemento.addEventListener("click", function(event){
                var respuesta = confirm("Seguro?");
                if(!respuesta){
                    event.preventDefault();
                }
            });
        }
        </script>
    </body>
</html>
