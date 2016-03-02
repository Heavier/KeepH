/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestion;

import hibernate.HibernateUtil;
import hibernate.Keep;
import hibernate.Usuario;
import java.math.BigInteger;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author 2dam
 */
public class GestorUsuario {

    public static JSONObject getLogin(String usuario, String password) {
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        sesion.beginTransaction();
        String sql = "from Usuario where login = :login AND pass = :pass";
        Query q = sesion.createQuery(sql);
        q.setString("login", usuario);
        q.setString("pass", password);
        List<Usuario> usuarios = q.list();
        sesion.getTransaction().commit();
        sesion.flush();
        sesion.close();
        JSONObject ob = new JSONObject();
        if (usuarios.isEmpty()) {
            ob.put("r", false);
        } else {
            ob.put("r", true);
        }
        return ob;
    }
    
    public static JSONObject addUsuario(Usuario u) {
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        sesion.beginTransaction();
        sesion.save(u);
        Long id = ((BigInteger) sesion.createSQLQuery("select last_insert_id()").uniqueResult()).longValue();
        sesion.getTransaction().commit();
        sesion.flush();
        sesion.close();
        JSONObject ob = new JSONObject();
        ob.put("r", id);
        return ob;
    }
    
    public static List<Usuario> getUsuarios(){
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        sesion.beginTransaction();
        String sql = "from Usuario";
        Query q = sesion.createQuery(sql);
        List<Usuario> usuarios = q.list();
        sesion.getTransaction().commit();
        sesion.flush();
        sesion.close();
        return usuarios;
    }
    
    public static Usuario getUsuario(String login){
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        sesion.beginTransaction();
        String sql = "from Usuario where login = :login";
        Query q = sesion.createQuery(sql);
        q.setString("login", login);
        Usuario usu = (Usuario) q.uniqueResult();
        sesion.getTransaction().commit();
        sesion.flush();
        sesion.close();
        return usu;
    }
    
    public static JSONObject getJSONUsuarios() {
        
        List<Usuario> list = gestion.GestorUsuario.getUsuarios();

        System.out.println(list + System.lineSeparator());

        JSONArray arraySon = new JSONArray();
        int count = 0;
        for (Usuario p : list) {
            JSONObject objetoSon = new JSONObject();
            objetoSon.put("login", p.getLogin());
            objetoSon.put("pass", p.getPass());
            arraySon.put(count, objetoSon);
            count++;
        }
        
        JSONObject objeto2 = new JSONObject();
        objeto2.put("usuarios", arraySon);
        System.out.println(objeto2 + System.lineSeparator());
        return objeto2;
    }

    public static void delete(String login) {
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        sesion.beginTransaction();
        
        List<Keep> keeps = getKeeps(login);
        for (Keep keep : keeps) {
            sesion.delete(keep);
        }
        
        sesion.delete(getUsuario(login));
        
        sesion.getTransaction().commit();
        sesion.flush();
        sesion.close();
    }

    private static List<Keep> getKeeps(String login) {
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        sesion.beginTransaction();
        String sql = "from Keep where login = :login ";
        Query q = sesion.createQuery(sql);
        q.setString("login", login);
        List<Keep> keeps = q.list();
        sesion.getTransaction().commit();
        sesion.flush();
        sesion.close();
        return keeps;
    }
}
