package dataBase;

import com.jswitch.base.modelo.HibernateUtil;
import com.jswitch.base.modelo.entidades.Usuario;
import com.jswitch.rol.modelo.Item;
import com.jswitch.rol.modelo.MenuByRol;
import com.jswitch.rol.modelo.Rol;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.Query;
import org.hibernate.Transaction;
import org.hibernate.classic.Session;

/**
 *
 * @author Nelson Moncada
 */
public class Menu {

    public Menu() {
        try {
            open();
            delete();
            create();
            close();
        } catch (Exception ex) {
            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {
        new Menu();
    }

    public void delete() {
        Session s = null;

        try {
            s = HibernateUtil.getSessionFactory().openSession();

               Transaction t = s.beginTransaction();

            s.createQuery("UPDATE " + Usuario.class.getName() + " SET rol = NULL").executeUpdate();

            System.out.println("----Roles desligados---");

            System.out.println(s.createQuery("DELETE FROM " + Rol.class.getName()).executeUpdate());
            System.out.println("----Roles Eliminados---");

            System.out.println(s.createQuery("DELETE FROM " + MenuByRol.class.getName()).executeUpdate());
            System.out.println("----MenuByRols Eliminados---");

            List<Item> lista = (List<Item>) s.createQuery("FROM " + Item.class.getName()).list();
            borrarItems(s, lista);

            t.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            s.close();
        }
    }

    public void borrarItems(Session s, List<Item> lista) {
        for (Item item : lista) {
            borrarItems(s, item.getItems());
            s.delete(item);
        }
    }

    public List getData(String fullClassName) {
        List data = null;
        try {
            Query query = s.createQuery("FROM " + fullClassName);
            data = query.list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }
    Session s;
    Transaction t;

    public void open() throws Exception {
        java.util.List mensajes = null;
        try {
            s = HibernateUtil.getSessionFactorySinIntercertor().openSession();
            t = s.beginTransaction();

        } catch (Exception e) {
            throw e;
        }
    }

    public void close() throws Exception {
        try {
            t.commit();
        } catch (Exception e) {
            throw e;
        } finally {
            s.close();
        }
    }

    public void create() {
        System.out.println("Proceso completado");
        LlenarMenu.llenarItems();
        System.out.println("Items");
        LlenarMenu.llenarRoles();
        System.out.println("Roles");
        LlenarMenu.llenarMenuByRoles();
        System.out.println("Menu by rol");
        setAllUserRoot();
        System.out.println("set rol to users");
    }

    private void setAllUserRoot() {
        java.util.List usuarios = getData(Usuario.class.getName());
        Rol admin = (Rol) getData(Rol.class.getName()).get(0);
        Rol rootRol = (Rol) getData(Rol.class.getName()).get(1);
        for (Usuario usuario : (List<Usuario>)usuarios) {
            if (usuario.getUserName().compareTo("nelson") == 0) {
                usuario.setRol(admin);
                usuario.setSuperusuario(Boolean.TRUE);
                usuario.setModificarPermisos(Boolean.TRUE);
                s.update(usuario);
            } else {
                usuario.setRol(rootRol);
                s.update(usuario);
            }
        }
    }
}
