
import java.io.BufferedReader;
import java.io.InputStreamReader;
import vista_controlador.ConfigFrame;
import vista_controlador.Logon;

/**
 *
 * @author bc
 */
public class Config {

    public static void main(String args[]) {

//        try {
//
//            if (args == null || args.length == 0) {
//                new Principal();
//                Principal.splah.dispose();
//            }
//        } catch (IOException ex) {
//            Logger.getLogger(Run.class.getName()).log(Level.SEVERE, null, ex);
//        }

//        Config.inicializar();

        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                try {
                    BufferedReader b = null;
                    b = new BufferedReader(new InputStreamReader(Runtime.getRuntime().exec("hostname").getInputStream()));
                    String hostname = b.readLine();
                    System.getProperties().put("COMPUTERNAME", hostname);
                    System.getProperties().put("valido", "si");

                    ConfigFrame cf = new ConfigFrame();
                    new Logon(cf, true).setVisible(true);
                    cf.setVisible(true);
//                    new Logon(null, true).setVisible(true);
//                    new ConfigFrameGridController(ConfigFrame.class.getName(), null, Empresa.class.getName(), "");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
