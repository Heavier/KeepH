/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import gestion.GestorKeep;
import gestion.GestorUsuario;
import hibernate.Keep;
import hibernate.Usuario;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Random;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;

/**
 *
 * @author 2dam
 */
@WebServlet(name = "controlador", urlPatterns = {"/go"})
public class controlador extends HttpServlet {

    enum Camino {
        forward, redirect, print;
    }

    class Destino {

        public Camino camino;
        public String url;
        private String texto;

        private Destino() {
        }

        public Destino(Camino camino, String url, String texto) {
            this.camino = camino;
            this.url = url;
            this.texto = texto;
        }
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String tabla = request.getParameter("tabla"); // Persona, idioma, etc
        String op = request.getParameter("op"); // login, create, read, update, delete
        String accion = request.getParameter("accion"); // view, do
        String origen = request.getParameter("origen"); // android, web
        Destino destino = handle(request, response, tabla, op, accion, origen);
        if (destino == null) {
            destino = new Destino(Camino.forward, "/WEB-INF/index.jsp", "");
        }
        if (destino.camino == Camino.forward) {
            getServletContext().getRequestDispatcher(destino.url).forward(request, response);
        } else if (destino.camino == Camino.redirect) {
            response.sendRedirect(destino.url);
        } else { // Android
            response.setContentType("text/html;charset=UTF-8");
            try (PrintWriter out = response.getWriter()) {
                out.println(destino.texto);
            } catch (Exception e) {
            }
        }
    }

    private Destino handle(HttpServletRequest request, HttpServletResponse response, String tabla, String op, String accion, String origen) {
        if (origen == null) {
            origen = "";
        }
        if (tabla == null || op == null || accion == null) {
            tabla = "usuario";
            op = "read";
            accion = "view";
        }
        switch (tabla) {
            case "usuario":
                return handleusuario(request, response, op, accion, origen);
            case "keep":
                return handlekeep(request, response, op, accion, origen);
            default:
        }
        return null;
    }

    private Destino handleusuario(HttpServletRequest request, HttpServletResponse response,
            String op, String accion, String origen) {
        JSONObject obj;
        Destino destino = new Destino();
        switch (op) {
            case "login":
                if (origen.compareTo("android") == 0) {
                    obj = GestorUsuario.getLogin(request.getParameter("login"), request.getParameter("pass"));
                    return new Destino(Camino.print, "", obj.toString());
                    // http://192.168.208.36:8080/Keep/go?tabla=usuario&op=login&accion=&login=juan&pass=juan&origen=android
                }
            case "create":
                if (accion.compareTo("view") == 0) {
                    destino.camino = Camino.forward;
                    destino.url = "/WEB-INF/usuario/create.jsp";
                    return destino;
                } else {
                    Usuario u = new Usuario(request.getParameter("login"), request.getParameter("pass"));
                    obj = GestorUsuario.addUsuario(u);
                    return new Destino(Camino.print, "", obj.toString());
                }
            case "read":
                // Mostrar listado
                if (origen.compareTo("android") == 0) {
                    obj = GestorUsuario.getJSONUsuarios();
                    request.setAttribute("json", destino.texto);
                    return new Destino(Camino.print, "/WEB-INF/json.jsp", obj.toString());
                } else {
                    destino.camino = Camino.forward;
                    destino.url = "/WEB-INF/usuario/view.jsp";
                    List<Usuario> list = gestion.GestorUsuario.getUsuarios();
                    request.setAttribute("listado", list);
                    return destino;
                }
            case "delete":
                // Eliminar objeto 
                String login = request.getParameter("login");
                gestion.GestorUsuario.delete(login);
                return new Destino(Camino.print, "go", "");
        }
        return null;
    }

    private Destino handlekeep(HttpServletRequest request, HttpServletResponse response,
            String op, String accion, String origen) {
        Destino destino = new Destino();
        switch (op) {
            case "create":
                    Usuario usu = GestorUsuario.getUsuario(request.getParameter("login"));
                if (origen.compareTo("android") == 0) {

                    Keep kp = new Keep(usu, request.getParameter("idAndroid"),
                            request.getParameter("contenido"), request.getParameter("ruta"), request.getParameter("estado"));
                    JSONObject obj = GestorKeep.addKeep(kp, usu);
                    return new Destino(Camino.print, "", obj.toString());
                }
                if (accion.compareTo("view") == 0) {
                    destino.camino = Camino.forward;
                    destino.url = "/WEB-INF/keep/create.jsp";
                    return destino;
                } else {
                    Usuario usur = GestorUsuario.getUsuario(request.getParameter("login"));
                    Random rn = new Random();
                    Keep k = new Keep(usur, rn.nextInt(), request.getParameter("contenido"), "no", "estable");
                    JSONObject obj = GestorKeep.addKeep(k, usur);
                    return new Destino(Camino.print, "", obj.toString());
                }
            case "read":
                // Mostrar listado
                if (origen.compareTo("android") == 0) {
                    JSONObject obj = GestorKeep.getSJONKeeps(request.getParameter("login"));
                    request.setAttribute("json", destino.texto);
                    return new Destino(Camino.print, "/WEB-INF/json.jsp", obj.toString());
                } else {
                    destino.camino = Camino.forward;
                    destino.url = "/WEB-INF/keep/view.jsp";
                    List<Keep> list = gestion.GestorKeep.getKeeps(request.getParameter("login"));
                    request.setAttribute("listado", list);
                    return destino;
                }
            case "delete":
                // Eliminar objeto
                String id = request.getParameter("id");
                gestion.GestorKeep.delete(Integer.parseInt(id));
                return new Destino(Camino.redirect, "go?tabla=keep&op=read&accion=view", null);
        }
        return null;
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    /*
    1   tabla : keep, usuario
    2   op : CRUD, login
    3   accion : view, do
    4   origen : android, web
     */
}
