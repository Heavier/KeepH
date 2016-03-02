<%@page import="hibernate.Usuario"%>
<%@page import="java.util.List"%>

<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%

    List<Usuario> lista = (List<Usuario>) request.getAttribute("listado");

%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Lista de usuarios</h1>

        <a href="go?tabla=usuario&op=create&accion=view">Crear un usuario nuevo</a>
        <br>
        <a href="go?tabla=keep&op=read&accion=view">Ir a notas</a>
        <br>
        <br>
        <table>
            <thead>
                <tr>
                    <th>Login</th>
                    <th>Password</th>
                </tr>
            </thead>
            <tbody>
                <%for (Usuario p : lista) {
                %>    
                <tr>
                    <td><%= p.getLogin() %></td>
                    <td><%= p.getPass() %></td>
                    <td><a href="go?tabla=usuario&op=delete&accion=do&login=<%= p.getLogin()%>" class="borrar">Eliminar</a></td>
                </tr>
                <%
                    }
                %>
            </tbody>

        </table>
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
