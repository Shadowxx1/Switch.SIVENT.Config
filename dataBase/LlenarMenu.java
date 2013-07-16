package dataBase;

import com.jswitch.auditoria.modelo.AuditoriaBasica;
import com.jswitch.base.controlador.General;
import com.jswitch.base.modelo.HibernateUtil;
import com.jswitch.rol.modelo.Item;
import com.jswitch.rol.modelo.MenuByRol;
import com.jswitch.rol.modelo.Rol;
import java.util.Date;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author adrian
 */
public class LlenarMenu {

    public static void llenarItems() {
        Session s = null;

        try {
            s = HibernateUtil.getSessionFactorySinIntercertor().openSession();
            Transaction tx = s.beginTransaction();

            Date d = new Date();
            AuditoriaBasica a = new AuditoriaBasica(d, "default", true);

            Item root = new Item("root", null, a);

            // <editor-fold defaultstate="collapsed" desc="Personas">
            // Personas
            Item personas = new Item("Personas", "folder.png", a);
            {
                Item presonasGrid = new Item("Personas",
                        "PER", "menu/screenshot1.png", "getPersonas", a);
                Item presonaNueva = new Item("Nuevo",
                        "PER", "menu/add.png", "getPersonaNueva", a);
                Item buscar = new Item("Buscar",
                        "PER", "menu/search (2).png", "getBuscarPersona", a);
                Item historialAsegurado = new Item("Historial Asegurado",
                        "PER", "menu/search (1).png", "getHistorialAsegurado", a);
                personas.getItems().add(presonaNueva);
                presonaNueva.setItem(personas);
                personas.getItems().add(presonasGrid);
                presonasGrid.setItem(personas);
                personas.getItems().add(buscar);
                buscar.setItem(personas);
                personas.getItems().add(historialAsegurado);
                historialAsegurado.setItem(personas);
            }
            root.getItems().add(personas);
            personas.setItem(root);
            // </editor-fold>

            // <editor-fold defaultstate="collapsed" desc="Inventario">
            // Personas
            Item inventario = new Item("Inventario", "folder.png", a);
            {
                Item almacenes = new Item("Almacenes",
                        "INVENT", "menu/screenshot1.png", "getAlmacenes", a);
                Item productos = new Item("Productos", "folder.png", a);
                {
                    Item nuevoProducto = new Item("Nuevo",
                            "INVENT", "menu/add.png", "getNuevoProducto", a);
                    productos.getItems().add(nuevoProducto);
                    nuevoProducto.setItem(productos);

                    Item todosProducto = new Item("Todos",
                            "INVENT", "menu/search (2).png", "getTodosProductos", a);
                    productos.getItems().add(todosProducto);
                    todosProducto.setItem(productos);

                    Item buscarProducto = new Item("Buscar",
                            "INVENT", "menu/search (2).png", "getBuscarProducto", a);
                    productos.getItems().add(buscarProducto);
                    buscarProducto.setItem(productos);
                }
                inventario.getItems().add(almacenes);
                almacenes.setItem(inventario);
                inventario.getItems().add(productos);
                productos.setItem(inventario);
            }

            root.getItems().add(inventario);
            inventario.setItem(root);
            // </editor-fold>

            // <editor-fold defaultstate="collapsed" desc="Mensajes de Texto">
            Item sms = new Item("Mensaje de Texto (SMS)", "folder.png", a);
            {
                Item masivoA = new Item("Masivo a:", "folder.png", a);
                sms.getItems().add(masivoA);
                masivoA.setItem(sms);

                Item smsReciboPend = new Item("Buzon de Entrada",
                        "SMSEntrada", "menu/inbox.png", "getSMSRecibidos", a);
                sms.getItems().add(smsReciboPend);
                smsReciboPend.setItem(sms);

                Item smsSolo = new Item("SMS Individual",
                        "SMS", "menu/send_sms.png", "getSMSSolo", a);
                sms.getItems().add(smsSolo);
                smsSolo.setItem(sms);

                Item smsPreEscrito = new Item("Plantillas",
                        "SMS", "menu/paper.png", "getSMSPreEscritos", a);
                sms.getItems().add(smsPreEscrito);
                smsPreEscrito.setItem(sms);

                Item smsAutomaticos = new Item("SMS Automaticos",
                        "SMS", "menu/send_sms.png", "getSMSAutomaticos", a);
                sms.getItems().add(smsAutomaticos);
                smsAutomaticos.setItem(sms);

                Item smsCumple = new Item("Cumpleañeros",
                        "SMS", "menu/cumple.png", "getSMSCumpleayeros", a);
                masivoA.getItems().add(smsCumple);
                smsCumple.setItem(masivoA);

//                Item smsGiro = new Item("Giros de Financiamiento",
//                        "SMS", "menu/financiamiento.png", "getSMSGiros");
//                masivoA.getItems().add(smsGiro);
//                smsGiro.setItem(masivoA);

                Item smsDocumentos = new Item("Vencimiento de Documentos anexos",
                        "SMS", "menu/card.png", "getSMSDocumentos", a);
                masivoA.getItems().add(smsDocumentos);
                smsDocumentos.setItem(masivoA);

//                Item smsRenovacion = new Item("Vencimiento de Recibos",
//                        "SMS", "menu/Notepad.png", "getSMSRecibos");
//                masivoA.getItems().add(smsRenovacion);
//                smsRenovacion.setItem(masivoA);

                Item smstodos = new Item("Todos los contactos",
                        "SMS", "menu/todos.png", "getSMSRTodos", a);
                masivoA.getItems().add(smstodos);
                smstodos.setItem(masivoA);
//                Item smsReciboPend = new Item("Aviso Vcto. Recibos Pendientes",
//                        "SMS", null, "getSMSREciboPend");
//                xxx.getItems().add(smsReciboPend);

                Item smsEnviados = new Item("Buzon de Salida",
                        "SMS", "menu/outbox masa.png", "getSMSEnviados", a);
                sms.getItems().add(smsEnviados);
                smsEnviados.setItem(sms);

                Item masa = new Item("Buzon Salida Masivos",
                        "SMS", "menu/outbox masa.png", "getSMSMasa", a);
                sms.getItems().add(masa);
                masa.setItem(sms);
            }

            root.getItems().add(sms);
            sms.setItem(root);
            // </editor-fold>

            // <editor-fold defaultstate="collapsed" desc="Mantenimiento">
            //Mantenimiento
            Item mantenimiento = new Item("Mantenimiento", "folder.png"/*"advancedsettings.png"*/, a);
            {
                Item roles = new Item("Roles", "folder.png", a);
                {
                    Item rol = new Item("Lista de Roles",
                            "REPORT", "menu/screenshot4.png", "getNuevoRol", a);
                    Item rolOp = new Item("Configurar Roles",
                            "REPORT", "menu/services.png", "getRoles", a);

                    roles.getItems().add(rol);
                    rol.setItem(roles);

                    roles.getItems().add(rolOp);
                    rolOp.setItem(roles);
                }
                mantenimiento.getItems().add(roles);
                roles.setItem(mantenimiento);
                //Mantenimiento Personas
                Item manPer = new Item("Personas", "folder.png", a);
                {
                    Item actEco = new Item("Tipo Actividad Economica",
                            "MAN", "menu/Briefcase.png", "getTipoActividadEconomica", a);
                    Item capEco = new Item("Tipo Capacidad Economica",
                            "MAN", "menu/money.png", "getTipoCapacidadEconomica", a);
                    Item tipPer = new Item("Tipo Persona",
                            "MAN", "menu/employee.png", "getTipoPersona", a);
                    Item tipTel = new Item("Tipo Telefono",
                            "MAN", "menu/call.png", "getTipoTelefono", a);
                    Item tipDir = new Item("Tipo Direccion",
                            "MAN", "menu/Home.png", "getTipoDireccion", a);
                    Item tipCtB = new Item("Tipo Cuenta Bancaria",
                            "MAN", "menu/piggy_bank.png", "getTipoCuentaBancaria", a);
                    Item codigoArea = new Item("Codigos de Area",
                            "MAN", "menu/Maps.png", "getCodigoArea", a);
                    manPer.getItems().add(actEco);
                    actEco.setItem(manPer);
                    manPer.getItems().add(capEco);
                    capEco.setItem(manPer);
                    manPer.getItems().add(tipPer);
                    tipPer.setItem(manPer);
                    manPer.getItems().add(tipTel);
                    tipTel.setItem(manPer);
                    manPer.getItems().add(tipDir);
                    tipDir.setItem(manPer);
                    manPer.getItems().add(tipCtB);
                    tipCtB.setItem(manPer);
                    manPer.getItems().add(codigoArea);
                    codigoArea.setItem(manPer);
                }
                mantenimiento.getItems().add(manPer);
                manPer.setItem(mantenimiento);

                // mantenimiento Sistema
                Item mantSistema = new Item("Sistema", "folder.png", a);
                {
                    Item usuario = new Item("Usuarios",
                            "MAN", "menu/user.png", "getUsuarios", a);
                    mantSistema.getItems().add(usuario);
                    usuario.setItem(mantSistema);

                    Item empresa = new Item("Empresa",
                            "MAN", "menu/company.png", "getEmpresa", a);
                    mantSistema.getItems().add(empresa);
                    empresa.setItem(mantSistema);

                    Item configuracion = new Item("Configuracion",
                            "MAN", "menu/advancedsettings.png", "getConfiguracion", a);
                    mantSistema.getItems().add(configuracion);
                    configuracion.setItem(mantSistema);

                    if (General.usuario.getAdministrador()) {
                        Item licencias = new Item("Licencias",
                                "MAN", "menu/paper.png", "getLicencias", a);
                        mantSistema.getItems().add(licencias);
                        licencias.setItem(mantSistema);
                    }

                    Item configLnF = new Item("Config Tiny LnF",
                            "MAN", "menu/mycomputer.png", "getConfigLnF", a);
                    mantSistema.getItems().add(configLnF);
                    configLnF.setItem(mantSistema);
                }
                mantenimiento.getItems().add(mantSistema);
                mantSistema.setItem(mantenimiento);

                Item tipDocAnex = new Item("Documentos Anexos",
                        "MAN", "menu/anexo.png", "getTipoDocAnex", a);
                mantenimiento.getItems().add(tipDocAnex);
                tipDocAnex.setItem(mantenimiento);

                Item cuentasBanc = new Item("Cuentas Bancarias",
                        "MAN", "menu/piggy_bank.png", "AUN_NO_FUN", a);
                mantenimiento.getItems().add(cuentasBanc);
                cuentasBanc.setItem(mantenimiento);
//                cuentasBanc.getAuditoria().setActivo(Boolean.FALSE);

                Item encabezado = new Item("Encabezados de Reporte",
                        "MAN", "menu/printer.png", "getEncabezado", a);
//                if (General.usuario.getAdministrador()) {
                mantenimiento.getItems().add(encabezado);
                encabezado.setItem(mantenimiento);
//                }
            }

            root.getItems().add(mantenimiento);
            mantenimiento.setItem(root);
            // </editor-fold>

            // <editor-fold defaultstate="collapsed" desc="Comunicados">
            //Comunicados
            Item comunicados = new Item("Comunicados y Solicitudes",
                    "COMU", "menu/email.png", "getComunicadosGrid", a);
            root.getItems().add(comunicados);
            comunicados.setItem(root);
            // </editor-fold>

            // <editor-fold defaultstate="collapsed" desc="Reportes">
            //Reportes

            Item reportes = new Item("Reportes",
                    "REPORT", "menu/printer1.png", "getReporteH", a);
            root.getItems().add(reportes);
            reportes.setItem(root);
            // </editor-fold>

            // <editor-fold defaultstate="collapsed" desc="Cambiar Contraseña">
            //Reportes

            Item changePass = new Item("Cambiar Contraseña",
                    "REPORT", "menu/key.png", "changePass", a);
            root.getItems().add(changePass);
            changePass.setItem(root);
            // </editor-fold>

            // <editor-fold defaultstate="collapsed" desc="Calendario">
            Item calendario = new Item("Calendario",
                    "HELP", "menu/calendar.png", "getCalendario", a);

            root.getItems().add(calendario);
            calendario.setItem(root);
            // </editor-fold>

            // <editor-fold defaultstate="collapsed" desc="Soporte Tecnico">
            Item helpCenter = new Item("Soporte Técnico",
                    "HelpCenter", "menu/call-us.png", "getHelpCenter", a);

            root.getItems().add(helpCenter);
            helpCenter.setItem(root);
            // </editor-fold>

            // <editor-fold defaultstate="collapsed" desc="Sincronizar">
            Item sincro = new Item("Sincronizar",
                    "SINC", "menu/update.png", "getSincronizar", a);
//            sincro.getAuditoria().setActivo(Boolean.FALSE);
            root.getItems().add(sincro);
            sincro.setItem(root);
            // </editor-fold>

            // <editor-fold defaultstate="collapsed" desc="Salir">
            Item exit = new Item("Salir",
                    "HELP", "menu/exit.png", "getExit", a);

            root.getItems().add(exit);
            exit.setItem(root);
            // </editor-fold>

//            Item prueba = new Item("Prueba",
//                    "PRUEBA", "menu/exit.png", "getPrueba");
//
//            root.getItems().add(prueba);

            s.save(root);
            tx.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            s.close();
        }

    }

    public static void updateMenu() {
        printMenuIds(getRootItem(), 0);

        Session s = null;
        try {
            s = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = s.beginTransaction();
            Item i = (Item) optenerData(Item.class.getName()
                    + " where id=122").get(0);
            Item item = new Item("Nueva Carpeta", null);
            i.getItems().add(1, item);
            s.saveOrUpdate(i);

            java.util.List roles = optenerData(Rol.class.getName());
            for (Object object : roles) {
                Rol rol = (Rol) object;
                fillMenu(rol, item, s);
            }

            tx.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            s.close();
        }

    }

    public static void printMenuIds(Item i, int level) {
        System.out.print(i.getId());
        for (int k = 0; k < level; k++) {
            System.out.print(" ");
        }
        System.out.println(i.getNombre());
        for (Item item : i.getItems()) {
            printMenuIds(item, level + 1);
        }
    }

    public static void llenarRoles() {
        Session s = null;
        Date d = new Date();
        AuditoriaBasica audit = (new AuditoriaBasica(d, "defaultData", true));
        AuditoriaBasica audit1 = (new AuditoriaBasica(d, "defaultData", true));
        try {
            s = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = null;

            // <editor-fold defaultstate="collapsed" desc="Roles">
            tx = s.beginTransaction();
            Rol root = new Rol("Administrador del Sistema");
            {
                root.setAuditoria(audit);
                root.getAuditoria().setActivo(Boolean.FALSE);
                root.getAuditoria().setVisible2(Boolean.FALSE);
                s.save(root);
            }
            Rol secretaria = new Rol("Usuario");
            {
                secretaria.setAuditoria(audit1);
                secretaria.getAuditoria().setActivo(Boolean.TRUE);
                secretaria.getAuditoria().setVisible2(Boolean.TRUE);
                s.save(secretaria);
            }
            tx.commit();
            // </editor-fold>

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            s.close();
        }

    }

    public static void llenarMenuByRoles() {
        Session s = null;
        try {
            s = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = null;

            // <editor-fold defaultstate="collapsed" desc="Roles">
            tx = s.beginTransaction();
            java.util.List roles = optenerData(Rol.class.getName());
            Item items = (Item) optenerData(Item.class.getName()
                    + " where nombre='root'").get(0);
            for (Object object : roles) {
                Rol rol = (Rol) object;
                for (Item item : items.getItems()) {
                    fillMenu(rol, item, s);
                }
            }

            tx.commit();
            // </editor-fold>

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            s.close();
        }

    }

    static void fillMenu(Rol rol, Item items, Session s) {
        MenuByRol nuevo = new MenuByRol(items.getId(), rol.getId(), items.getAuditoria().getActivo());
        s.save(nuevo);
        for (Item item : items.getItems()) {
            fillMenu(rol, item, s);
        }
    }

    static java.util.List optenerData(String valueObjectClassName) {

        Session s = null;
        java.util.List mensajes = null;
        try {
            s = HibernateUtil.getSessionFactory().openSession();
            Transaction t = s.beginTransaction();
            {
                String hql = "FROM " + valueObjectClassName;
                Query query = s.createQuery(hql);
                mensajes = query.list();
            }
            t.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            s.close();
        }
        return mensajes;
    }

    public static void saveData(Object o) {
        Session s = null;
        try {
            s = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = null;

            tx = s.beginTransaction();
            s.save(o);
            tx.commit();

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            s.close();
        }
    }

    public static void updateData(Object o) {
        Session s = null;
        try {
            s = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = null;
            tx = s.beginTransaction();
            s.update(o);
            tx.commit();

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            s.close();
        }
    }

    public static void addMenu(Item menu) {
        Item root = new LlenarMenu().getRootItem();
        root.getItems().add(menu);
        updateData(root);
        Session s = null;
        try {
            s = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = null;

            tx = s.beginTransaction();
            java.util.List roles = optenerData(Rol.class.getName());
            for (Object object : roles) {
                Rol rol = (Rol) object;
                fillMenu(rol, menu, s);
                for (Item item : menu.getItems()) {
                    fillMenu(rol, item, s);
                }
            }

            tx.commit();

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            s.close();
        }
    }

    private static Item getRootItem() {
        Session s = null;
        try {
            s = HibernateUtil.getSessionFactory().openSession();
            Item items = (Item) optenerData(Item.class.getName()
                    + " where nombre='root'").get(0);
            return items;
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            s.close();
        }
        return null;
    }
}
