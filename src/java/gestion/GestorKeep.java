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

public class GestorKeep {

    public static JSONObject addKeep(Keep kp, Usuario usuario) {
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        sesion.beginTransaction();
        kp.setUsuario(usuario);
        sesion.save(kp);
        Long id = ((BigInteger) sesion.createSQLQuery("select last_insert_id()").uniqueResult()).longValue();
        sesion.getTransaction().commit();
        sesion.flush();
        sesion.close();
        JSONObject ob = new JSONObject();
        ob.put("r", id);
        return ob;
    }
    
    public static List<Keep> getKeeps(String login){
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        sesion.beginTransaction();
        String sql = "from Keep where login = :login";
        Query q = sesion.createQuery(sql);
        q.setString("login", login);
        List<Keep> keeps = q.list();
        sesion.getTransaction().commit();
        sesion.flush();
        sesion.close();
        return keeps;
    }
    
    public static Keep getKeep(String id){
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        sesion.beginTransaction();
        String sql = "from Keep where id = :id";
        Query q = sesion.createQuery(sql);
        q.setString("id", id);
        Keep ke = (Keep) q.uniqueResult();
        sesion.getTransaction().commit();
        sesion.flush();
        sesion.close();
        return ke;
    }
    
    public static JSONObject getSJONKeeps(String login) {
        
        List<Keep> list = gestion.GestorKeep.getKeeps(login);

        System.out.println(list + System.lineSeparator());

        JSONArray arraySon = new JSONArray();
        int count = 0;
        for (Keep p : list) {
            JSONObject objetoSon = new JSONObject();
            objetoSon.put("id", p.getId());
            objetoSon.put("contenido", p.getContenido());
            objetoSon.put("ruta", p.getRuta());
            arraySon.put(count, objetoSon);
            count++;
        }
        
        JSONObject objeto2 = new JSONObject();
        objeto2.put("keeps", arraySon);
        System.out.println(objeto2 + System.lineSeparator());
        return objeto2;
    }
    
    public static void delete(int id) {
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        sesion.beginTransaction();
        
        sesion.delete(getKeep(String.valueOf(id)));
        
        sesion.getTransaction().commit();
        sesion.flush();
        sesion.close();
    }
}
