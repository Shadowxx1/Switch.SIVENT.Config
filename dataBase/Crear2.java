package dataBase;

import com.jswitch.auditoria.modelo.AuditoriaBasica;
import com.jswitch.base.controlador.licencia.Equipo;
import com.jswitch.base.modelo.Clave;
import com.jswitch.base.modelo.entidades.Empresa;
import com.jswitch.base.modelo.entidades.Usuario;

/**
 *
 * @author bc
 */
public class Crear2 extends Crear {

    public Crear2() {
    }

    @Override
    public void create() {
        empresa();
        Usuario u = new Usuario("Nelson Moncada", Clave.leer().getUsuarioAdmin(), Equipo.encodeText(Clave.leer().getPassAdmin()), new AuditoriaBasica(), false, true, true);
        s.save(u);
        super.create();
    }

    private void empresa() {
        Empresa empresa = new Empresa("V18372924-5", "NombreEmpresa", "nombreEmpresajSipolee@gmail.com", "jsipoleeSoporte@gmail.com", "/jSipolEE/DocDigital", "./Reportes");
        s.save(empresa);
    }

    public static void main(String[] args) {
        try {
            Crear bd = new Crear2();
            bd.open();
            bd.create();
            bd.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
