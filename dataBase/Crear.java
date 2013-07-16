

package dataBase;


import com.jswitch.auditoria.modelo.AuditoriaBasica;
import java.util.ArrayList;
import java.util.Date;
import com.jswitch.base.modelo.Dominios;
import com.jswitch.base.modelo.HibernateUtil;
import com.jswitch.base.modelo.entidades.CalendarioBancario;
import com.jswitch.base.modelo.entidades.Usuario;
import com.jswitch.base.modelo.entidades.defaultData.DefaultData;
import com.jswitch.persona.modelo.dominio.TipoActividadEconomica;
import com.jswitch.persona.modelo.dominio.TipoCapacidadEconomica;
import com.jswitch.persona.modelo.dominio.TipoCodigoArea;
import com.jswitch.persona.modelo.dominio.TipoCuentaBancaria;
import com.jswitch.persona.modelo.dominio.TipoDireccion;
import com.jswitch.persona.modelo.dominio.TipoPersona;
import com.jswitch.persona.modelo.dominio.TipoTelefono2;
import com.jswitch.persona.modelo.maestra.Persona;
import com.jswitch.persona.modelo.maestra.PersonaJuridica;
import com.jswitch.persona.modelo.maestra.PersonaNatural;
import com.jswitch.persona.modelo.maestra.Rif;
import com.jswitch.base.modelo.entidades.TipoDocumento;
import com.jswitch.base.modelo.entidades.ValoresEstandares;
import com.jswitch.persona.modelo.dominio.direccion.Estado;
import com.jswitch.persona.modelo.maestra.LNPersonaNatural;
import org.hibernate.Transaction;
import org.hibernate.classic.Session;
import com.jswitch.sms.modelo.SMSPreEscrito;


/**
 * @author Orlando Becerra
 * @author Enrique Becerra
 */
public class Crear {

    protected Session s;
    protected Usuario userDD;
    protected Transaction tx;
    protected DefaultData defaultData;
    protected TipoPersona tpContribuyente;
    protected TipoPersona tpBanco;
    protected TipoPersona tpProveedor;
    protected TipoPersona tpClinica;
    protected TipoPersona tpPrestatario;
    protected TipoPersona tpFiador;
    protected TipoPersona tpSocio;
    protected TipoPersona tpHeredero;
    protected AuditoriaBasica auditoriaActivo;
    protected AuditoriaBasica auditoriaInactivo;

    public Crear() {
    }

    public void open() {
	s = HibernateUtil.getAnnotationConfiguration().
		//setProperty("hibernate.show_sql", "true").
		//setProperty("hibernate.format_sql", "true").
		buildSessionFactory().openSession();
	tx = s.beginTransaction();
    }

    public void crear()
    {
	s = HibernateUtil.getAnnotationConfiguration().
		//setProperty("hibernate.show_sql", "true").
		//setProperty("hibernate.format_sql", "true").
		setProperty("hibernate.hbm2ddl.auto", "create").
		buildSessionFactory().openSession();
	tx = s.beginTransaction();
        tx.commit();
        s.close();
    }

    public void close() {
	tx.commit();
	s.close();
    }

    public void create() {
	defaultData = new DefaultData();
	open();
	datosPorDefecto();
	s.save(defaultData.persona);
	close();

    }

    private void datosPorDefecto() {
	userDD = new Usuario("defaultdata", "defaultdata", new byte[]{}, null, false, false, false);
	s.save(userDD);
	Date d = new Date();
	auditoriaActivo = new AuditoriaBasica(d, userDD.getUserName(), true);
	auditoriaInactivo = new AuditoriaBasica(d, userDD.getUserName(), false);
	tiposSMSPreEscrito(auditoriaActivo);
	tiposPersona(auditoriaActivo, auditoriaInactivo);
	tiposActividadEconomica(auditoriaActivo);
	tipoCuentaBancaria(auditoriaActivo);
	tiposDocumento(auditoriaActivo);
	tiposDireccion(auditoriaActivo);
	tiposTelefono(auditoriaActivo);
	tiposCodigoArea(auditoriaActivo);
	tiposCapacidadEconomica(auditoriaActivo);
	estados(auditoriaActivo);
	//personasDefault(auditoriaActivo);
	valoresEstandares(auditoriaActivo);
	calendarioBancario(auditoriaActivo);
    }

    private void tipoCuentaBancaria(AuditoriaBasica a) {
	ArrayList<TipoCuentaBancaria> list = new ArrayList<TipoCuentaBancaria>(0);
	list.add(new TipoCuentaBancaria("AHORRO", a));
	list.add(new TipoCuentaBancaria("CORRIENTE", a));
	list.add(new TipoCuentaBancaria("TARJETA DE CREDITO", a));
	list.add(new TipoCuentaBancaria("TARJETA DE DEBITO", a));
	for (TipoCuentaBancaria o : list) {
	    s.save(o);
	}
    }

    private void tiposDocumento(AuditoriaBasica a) {
	ArrayList<TipoDocumento> list = new ArrayList<TipoDocumento>(0);
	list.add(new TipoDocumento("CEDULA DE IDENTIDAD", Dominios.Modulos.PERSONAS, a));
	list.add(new TipoDocumento("CARNET DE EMPRESA", Dominios.Modulos.PERSONAS, a));
	list.add(new TipoDocumento("CERTIFICADO MEDICO", Dominios.Modulos.PERSONAS, a));
	list.add(new TipoDocumento("LICENCIA DE CONDUCIR", Dominios.Modulos.PERSONAS, a));
	list.add(new TipoDocumento("PARTIDA DE NACIMIENTO", Dominios.Modulos.PERSONAS, a));
	list.add(new TipoDocumento("FOTO", Dominios.Modulos.PERSONAS, a));
	for (TipoDocumento o : list) {
	    s.save(o);
	}
    }

    private void tiposSMSPreEscrito(AuditoriaBasica a) {
	ArrayList<SMSPreEscrito> list = new ArrayList<SMSPreEscrito>(0);
	list.add(new SMSPreEscrito("BIENVENIDA", ":nombre, reciba una cordial bienvenida a nuestra organizacion en nombre de COMPAÑIA. Queremos que se sienta Seguro. Feliz dia", a));
	list.add(new SMSPreEscrito("CUMPLEAÑOS", "Feliz Cumpleaños :nombre, le desea COMPAÑIA en este día tan especial.", a));
	list.add(new SMSPreEscrito("DOC. ANEXO POR VENCER", "Buen día :nombre, COMPAÑIA le recuerda que su :tipoDocumento vence el dia :fecha. Por favor, tramitelo con antelacion.", a));
	for (SMSPreEscrito o : list) {
	    s.save(o);
	}
    }

    private void tiposCapacidadEconomica(AuditoriaBasica a) {
	ArrayList<TipoCapacidadEconomica> list = new ArrayList<TipoCapacidadEconomica>(0);
	TipoCapacidadEconomica cap = new TipoCapacidadEconomica("Desconocido", a);
	list.add(cap);
	list.add(new TipoCapacidadEconomica("HASTA 5 Mil Bs", a));
	list.add(new TipoCapacidadEconomica("DESDE 10 Mil HASTA 20 MIL Bs", a));
	list.add(new TipoCapacidadEconomica("DESDE 20 Mil HASTA 30 MIL Bs", a));
	list.add(new TipoCapacidadEconomica("MAS DE 30 MIL Bs", a));
	for (TipoCapacidadEconomica o : list) {
	    s.save(o);
	}
	defaultData.persona.setCapacidadEconomica(cap);
    }

    private void tiposDireccion(AuditoriaBasica a) {
	ArrayList<TipoDireccion> list = new ArrayList<TipoDireccion>(0);
	TipoDireccion dir = new TipoDireccion("COBRO", a);
	list.add(dir);
	list.add(new TipoDireccion("PPRINCIPAL", a));
	list.add(new TipoDireccion("HABITACION", a));
	list.add(new TipoDireccion("TRABAJO", a));
	list.add(new TipoDireccion("SUCURSAL", a));
	for (TipoDireccion o : list) {
	    s.save(o);
	}
	defaultData.persona.setDireccion(dir);
    }

    private void tiposTelefono(AuditoriaBasica a) {
	ArrayList<TipoTelefono2> list = new ArrayList<TipoTelefono2>(0);
	TipoTelefono2 tel = new TipoTelefono2("COBRO", a);
	list.add(tel);
	list.add(new TipoTelefono2("PRINCIPAL", a));
	list.add(new TipoTelefono2("HABITACION", a));
	list.add(new TipoTelefono2("TRABAJO", a));
	list.add(new TipoTelefono2("SUCURSAL", a));
	list.add(new TipoTelefono2("MOVIL", a));
	for (TipoTelefono2 o : list) {
	    s.save(o);
	}
	defaultData.persona.setTelefono(tel);
    }

    private void tiposCodigoArea(AuditoriaBasica a) {
	ArrayList<TipoCodigoArea> list = new ArrayList<TipoCodigoArea>(0);
	list.add(new TipoCodigoArea("MOVISTAR 414", 414, a));
	list.add(new TipoCodigoArea("MOVISTAR 424", 424, a));
	list.add(new TipoCodigoArea("MOVILNET 416", 416, a));
	list.add(new TipoCodigoArea("MOVILNET 426", 426, a));
	list.add(new TipoCodigoArea("DIGITEL 412", 412, a));
	list.add(new TipoCodigoArea("TA S/CTBAL. 276", 276, a));
	for (TipoCodigoArea o : list) {
	    s.save(o);
	}
    }

    private void tiposActividadEconomica(AuditoriaBasica a) {
	ArrayList<TipoActividadEconomica> list = new ArrayList<TipoActividadEconomica>(0);
	TipoActividadEconomica act = new TipoActividadEconomica("Desconocido", a);
	list.add(act);
	list.add(new TipoActividadEconomica("CONCEJO COMUNAL", a));
	list.add(new TipoActividadEconomica("AMA DE CASA", a));
	list.add(new TipoActividadEconomica("AGRICULTURA", a));
	list.add(new TipoActividadEconomica("AUTONOMO/PROPIETARIO", a));
	list.add(new TipoActividadEconomica("COMERCIANTE/ARTESANO", a));
	list.add(new TipoActividadEconomica("CONSULTOR/SERV.PROFESIONALES", a));
	list.add(new TipoActividadEconomica("CONTABILIDAD/FINANZAS", a));
	list.add(new TipoActividadEconomica("DIRECCION EJECUTIVA", a));
	list.add(new TipoActividadEconomica("ESTUDIANTE", a));
	list.add(new TipoActividadEconomica("VENTAS/MERCADOTECNIA/PUBLICIDA", a));
	list.add(new TipoActividadEconomica("GOBIERNO/EJERCITO", a));
	list.add(new TipoActividadEconomica("INFORMATICA", a));
	list.add(new TipoActividadEconomica("PESCA", a));
	list.add(new TipoActividadEconomica("SIN EMPLEO/INACTIVIDAD TEMPORAL", a));
	list.add(new TipoActividadEconomica("TRANSPORTE", a));
	list.add(new TipoActividadEconomica("TURISMO", a));
	list.add(new TipoActividadEconomica("MINERIA", a));
	list.add(new TipoActividadEconomica("INDUSTRIA", a));
	list.add(new TipoActividadEconomica("PESCA", a));
	for (TipoActividadEconomica o : list) {
	    s.save(o);
	}
	defaultData.persona.setActividadEconomica(act);
    }

    private void tiposPersona(AuditoriaBasica a, AuditoriaBasica a2) {
	ArrayList<TipoPersona> list = new ArrayList<TipoPersona>(0);
	tpPrestatario = new TipoPersona("JUN", "JUNTA DIRECTIVA", true, a);
	list.add(tpPrestatario);
	tpFiador = new TipoPersona("VIC", "VICEPRESIDENTE", true, a);
	list.add(tpFiador);
	tpSocio = new TipoPersona("JEF", "JEFE DE PATRULLA", true, a);
	list.add(tpSocio);
	tpHeredero = new TipoPersona("PAT", "PATRULLERO", true, a);
	list.add(tpHeredero);
	for (TipoPersona o : list) {
	    s.save(o);
	}
    }

    private void estados(AuditoriaBasica a) {
	ArrayList<Estado> list = new ArrayList<Estado>(0);
	list.add(new Estado("TACHIRA", a));
	list.add(new Estado("DISTRITO FEDERAL", a));
	list.add(new Estado("ANZUATEGUI", a));
	list.add(new Estado("APURE", a));
	list.add(new Estado("ARAGUA", a));
	list.add(new Estado("BARINAS", a));
	list.add(new Estado("BOLIVAR", a));
	list.add(new Estado("CARABOBO", a));
	list.add(new Estado("COJEDES", a));
	list.add(new Estado("FALCON", a));
	list.add(new Estado("GUARICO", a));
	list.add(new Estado("LARA", a));
	list.add(new Estado("MERIDA", a));
	list.add(new Estado("MIRANDA", a));
	list.add(new Estado("MONAGAS", a));
	list.add(new Estado("NUEVA ESPARTA", a));
	list.add(new Estado("PORTUGUEZA", a));
	list.add(new Estado("SUCRE", a));
	list.add(new Estado("TRUJILLO", a));
	list.add(new Estado("YARACUY", a));
	list.add(new Estado("ZULIA", a));
	list.add(new Estado("MONAGAS", a));
	list.add(new Estado("VARGAS", a));
	list.add(new Estado("ANTILLAS", a));
	list.add(new Estado("TERRITORIO DEL TAMACURO", a));
	list.add(new Estado("OTRO PAIS", a));
	for (Estado o : list) {
	    s.save(o);
	}
    }

    private void dataPersona(Persona p, AuditoriaBasica a) {
	p.setAuditoria(a);
	p.setCapacidadEconomica(defaultData.persona.getCapacidadEconomica());
	p.setActividadEconomica(defaultData.persona.getActividadEconomica());
	p.setTipoContribuyente(Dominios.TipoContribuyente.DESCONOCIDO);
	p.setRanking(Dominios.Ranking.B);
    }

    protected void dataPersonaNatural(PersonaNatural pn, AuditoriaBasica a) {
	pn.setTipoNombre(Dominios.TipoNombre.DESCONOCIDO);
	pn.setSexo(Dominios.Sexo.DESCONOCIDO);
	pn.setEstadoCivil(Dominios.EstadoCivil.DESCONOCIDO);
	LNPersonaNatural.generarNombres(pn);
	dataPersona(pn, a);
    }

    private void personasDefault(AuditoriaBasica a) {
	//Persona Natural

	//*************************
	//Persona Juridica Bancos
	PersonaJuridica bcomercantil1 = new PersonaJuridica();
	bcomercantil1.setRif(new Rif(Dominios.TipoCedula.JURIDICO, 2961, 0));
	bcomercantil1.setNombreLargo("MERCANTIL, C.A. BANCO UNIVERSAL");
	bcomercantil1.setNombreCorto("MERCANTIL");
	bcomercantil1.setWeb("http://www.bancomercantil.com");
	bcomercantil1.getTiposPersona().add(tpBanco);
	dataPersona(bcomercantil1, a);
	s.save(bcomercantil1);

	PersonaJuridica bcobicentenario = new PersonaJuridica();
	bcobicentenario.setRif(new Rif(Dominios.TipoCedula.GOBIERNO, 20009148, 7));
	bcobicentenario.setNombreLargo("BANCO BICENTENARIO, C.A. BANCO UNIVERSAL");
	bcobicentenario.setNombreCorto("BICENTENARIO");
	bcobicentenario.setWeb("http://www.bicentenariobu.com/");
	bcobicentenario.getTiposPersona().add(tpBanco);
	dataPersona(bcobicentenario, a);
	s.save(bcobicentenario);

	PersonaJuridica bcovenezuela = new PersonaJuridica();
	bcovenezuela.setRif(new Rif(Dominios.TipoCedula.JURIDICO, 2948, 2));
	bcovenezuela.setNombreLargo("BANCO DE VENEZUELA, S.A BANCO UNIVERSAL");
	bcovenezuela.setNombreCorto("VENEZUELA");
	bcovenezuela.setWeb("http://www.bancodevenezuela.com/");
	bcovenezuela.getTiposPersona().add(tpBanco);
	dataPersona(bcovenezuela, a);
	s.save(bcovenezuela);

	PersonaJuridica bcotesoro = new PersonaJuridica();
	bcotesoro.setRif(new Rif(Dominios.TipoCedula.GOBIERNO, 20005187, 6));
	bcotesoro.setNombreLargo("BANCO DEL TESORO, C.A. BANCO UNIVERSA");
	bcotesoro.setNombreCorto("TESORO");
	bcotesoro.setWeb("http://www.bt.gob.ve/");
	bcotesoro.getTiposPersona().add(tpBanco);
	dataPersona(bcotesoro, a);
	s.save(bcotesoro);

	PersonaJuridica bcobanesco = new PersonaJuridica();
	bcobanesco.setRif(new Rif(Dominios.TipoCedula.JURIDICO, 7013380, 5));
	bcobanesco.setNombreLargo("BANESCO BANCO UNIVERSAL, C.A.");
	bcobanesco.setNombreCorto("BANESCO");
	bcobanesco.setWeb("http://www.banesco.com/");
	bcobanesco.getTiposPersona().add(tpBanco);
	dataPersona(bcobanesco, a);
	s.save(bcobanesco);

	PersonaJuridica bcosofitasa = new PersonaJuridica();
	bcosofitasa.setRif(new Rif(Dominios.TipoCedula.JURIDICO, 9028384, 6));
	bcosofitasa.setNombreLargo("BANCO SOFITASA BANCO UNIVERSAL, C. A.");
	bcosofitasa.setNombreCorto("SOFITASA");
	bcosofitasa.setWeb("http://www.sofitasa.com/index.asp");
	bcosofitasa.getTiposPersona().add(tpBanco);
	dataPersona(bcosofitasa, a);
	s.save(bcosofitasa);

	PersonaJuridica bcobod = new PersonaJuridica();
	bcobod.setRif(new Rif(Dominios.TipoCedula.JURIDICO, 30061946, 0));
	bcobod.setNombreLargo("BANCO OCCIDENTAL DE DESCUENTO, BANCO UNIVERSAL, C.A. ");
	bcobod.setNombreCorto("BOD");
	bcobod.setWeb("http://www.bodinternet.com/Red_de_Oficinas.asp");
	bcobod.getTiposPersona().add(tpBanco);
	dataPersona(bcobod, a);
	s.save(bcobod);

	PersonaJuridica bcoexterior = new PersonaJuridica();
	bcoexterior.setRif(new Rif(Dominios.TipoCedula.JURIDICO, 2950, 4));
	bcoexterior.setNombreLargo("BANCO EXTERIOR,C.A, BANCO UNIVERSAL");
	bcoexterior.setNombreCorto("EXTERIOR");
	bcoexterior.setWeb("http://www.bancoexterior.com/");
	bcoexterior.getTiposPersona().add(tpBanco);
	dataPersona(bcoexterior, a);
	s.save(bcoexterior);

	PersonaJuridica bcocoro = new PersonaJuridica();
	bcocoro.setRif(new Rif(Dominios.TipoCedula.JURIDICO, 7000173, 9));
	bcocoro.setNombreLargo("BANCORO C.A. BANCO UNIVERSAL REGIONAL");
	bcocoro.setNombreCorto("BANCORO");
	bcocoro.setWeb("http://www.bancoro.com/");
	bcocoro.getTiposPersona().add(tpBanco);
	dataPersona(bcocoro, a);
	s.save(bcocoro);

	PersonaJuridica bcobfc = new PersonaJuridica();
	bcobfc.setRif(new Rif(Dominios.TipoCedula.JURIDICO, 30778189, 0));
	bcobfc.setNombreLargo("BFC BANCO FONDO COMUN, C.A. BANCO UNIVERSAL");
	bcobfc.setNombreCorto("BFC");
	bcobfc.setWeb("http://www.bfc.com.ve/");
	bcobfc.getTiposPersona().add(tpBanco);
	dataPersona(bcobfc, a);
	s.save(bcobfc);

	PersonaJuridica bcofederal = new PersonaJuridica();
	bcofederal.setRif(new Rif(Dominios.TipoCedula.JURIDICO, 8511576, 5));
	bcofederal.setNombreLargo("BANCO FEDERAL, C.A.");
	bcofederal.setNombreCorto("FEDERAL");
	bcofederal.setWeb("http://www.bancofederal.com/");
	bcofederal.getTiposPersona().add(tpBanco);
	dataPersona(bcofederal, a);
	s.save(bcofederal);

	PersonaJuridica bcoprovincial = new PersonaJuridica();
	bcoprovincial.setRif(new Rif(Dominios.TipoCedula.JURIDICO, 2967, 9));
	bcoprovincial.setNombreLargo("BANCO PROVINCIAL, S.A. BANCO UNIVERSAL");
	bcoprovincial.setNombreCorto("PROVINCIAL");
	bcoprovincial.setWeb("https://www.provincial.com/");
	bcoprovincial.getTiposPersona().add(tpBanco);
	dataPersona(bcoprovincial, a);
	s.save(bcoprovincial);

	PersonaJuridica bcoguayana = new PersonaJuridica();
	bcoguayana.setRif(new Rif(Dominios.TipoCedula.JURIDICO, 2941, 5));
	bcoguayana.setNombreLargo("BANCO GUAYANA C.A.");
	bcoguayana.setNombreCorto("GUAYANA");
	bcoguayana.setWeb("http://www.bancoguayana.net/");
	bcoguayana.getTiposPersona().add(tpBanco);
	dataPersona(bcoguayana, a);
	s.save(bcoguayana);

	PersonaJuridica bcoiv = new PersonaJuridica();
	bcoiv.setRif(new Rif(Dominios.TipoCedula.JURIDICO, 2957, 1));
	bcoiv.setNombreLargo("BANCO INDUSTRIAL DE VENEZUELA C A");
	bcoiv.setNombreCorto("INDUSTRIAL");
	bcoiv.setWeb("http://www.biv.com.ve//");
	bcoiv.getTiposPersona().add(tpBanco);
	dataPersona(bcoiv, a);
	s.save(bcoiv);

	//*************************
	//Persona Juridica Prestacion de servicios
	PersonaJuridica servmovilnet = new PersonaJuridica();
	servmovilnet.setRif(new Rif(Dominios.TipoCedula.JURIDICO, 30000493, 7));
	servmovilnet.setNombreLargo("TELECOMUNICACIONES MOVILNET, C.A.");
	servmovilnet.setNombreCorto("MOVILNET");
	servmovilnet.setWeb("http://www.movilnet.com.ve/sitio/");
	servmovilnet.getTiposPersona().add(tpProveedor);
	dataPersona(servmovilnet, a);
	s.save(servmovilnet);

	PersonaJuridica servcantv = new PersonaJuridica();
	servcantv.setRif(new Rif(Dominios.TipoCedula.JURIDICO, 30186298, 8));
	servcantv.setNombreLargo("CANTV.NET, C.A.");
	servcantv.setNombreCorto("CANTV");
	servcantv.setWeb("http://www.cantv.net/");
	servcantv.getTiposPersona().add(tpProveedor);
	dataPersona(servcantv, a);
	s.save(servcantv);

	PersonaJuridica servmovistar = new PersonaJuridica();
	servmovistar.setRif(new Rif(Dominios.TipoCedula.JURIDICO, 343994, 0));
	servmovistar.setNombreLargo("TELCEL, C.A.");
	servmovistar.setNombreCorto("MOVISTAR");
	servmovistar.setWeb("http://www.movistar.com.ve/");
	servmovistar.getTiposPersona().add(tpProveedor);
	dataPersona(servmovistar, a);
	s.save(servmovistar);

	PersonaJuridica servdigitel = new PersonaJuridica();
	servdigitel.setRif(new Rif(Dominios.TipoCedula.JURIDICO, 30468971, 3));
	servdigitel.setNombreLargo("CORPORACION DIGITEL, C.A. ");
	servdigitel.setNombreCorto("DIGITEL");
	servdigitel.setWeb("http://www.digitel.com.ve/");
	servdigitel.getTiposPersona().add(tpProveedor);
	dataPersona(servdigitel, a);
	s.save(servdigitel);

	// pendiente revisar rif seniat
	PersonaJuridica servcadafe = new PersonaJuridica();
	servcadafe.setRif(new Rif(Dominios.TipoCedula.JURIDICO, 4366, 3));
	servcadafe.setNombreLargo("CADAFE");
	servcadafe.setNombreCorto("CADAFE");
	servcadafe.setWeb("http://www.google.com");
	servcadafe.getTiposPersona().add(tpProveedor);
	dataPersona(servcadafe, a);
	s.save(servcadafe);

	// pendiente revisar rif seniat
	PersonaJuridica servhso = new PersonaJuridica();
	servhso.setRif(new Rif(Dominios.TipoCedula.GOBIERNO, 20008085, 0));
	servhso.setNombreLargo("HIDROSUROESTE");
	servhso.setNombreCorto("HIDROSUROESTE");
	servhso.setWeb("http://www.google.com");
	servhso.getTiposPersona().add(tpProveedor);
	dataPersona(servhso, a);
	s.save(servhso);

	PersonaJuridica servseniat = new PersonaJuridica();
	servseniat.setRif(new Rif(Dominios.TipoCedula.GOBIERNO, 20000303, 0));
	servseniat.setNombreLargo("SERVICIO NACIONAL INTEGRADO DE ADMINISTRACION ADUANERA Y TRIBUTARIA");
	servseniat.setNombreCorto("SENIAT");
	servseniat.setWeb("http://www.seniat.gob.ve/");
	servseniat.getTiposPersona().add(tpProveedor);
	dataPersona(servseniat, a);
	s.save(servseniat);

	PersonaJuridica servdirectv = new PersonaJuridica();
	servdirectv.setRif(new Rif(Dominios.TipoCedula.JURIDICO, 30259700, 5));
	servdirectv.setNombreLargo("GALAXY ENTERTAINMENT DE VENEZUELA, C.A.");
	servdirectv.setNombreCorto("DIRECTV");
	servdirectv.setWeb("http://www.directv.com.ve/");
	servdirectv.getTiposPersona().add(tpProveedor);
	dataPersona(servdirectv, a);
	s.save(servdirectv);

	PersonaJuridica servnetuno = new PersonaJuridica();
	servnetuno.setRif(new Rif(Dominios.TipoCedula.JURIDICO, 30108335, 0));
	servnetuno.setNombreLargo("NETUNO, C.A");
	servnetuno.setNombreCorto("NETUNO");
	servnetuno.setWeb("https://www.netuno.net/");
	servnetuno.getTiposPersona().add(tpProveedor);
	dataPersona(servnetuno, a);
	s.save(servnetuno);

	//*************************
	//Persona Juridica Lineas Aereas  ASERCA AIRLINES, C.A.
	PersonaJuridica laserca = new PersonaJuridica();
	laserca.setRif(new Rif(Dominios.TipoCedula.JURIDICO, 7503559, 3));
	laserca.setNombreLargo("ASERCA AIRLINES, C.A.");
	laserca.setNombreCorto("ASERCA");
	laserca.setWeb("http://www.asercaairlines.com/");
	laserca.getTiposPersona().add(tpProveedor);
	dataPersona(laserca, a);
	s.save(laserca);

	PersonaJuridica lconviasa = new PersonaJuridica();
	lconviasa.setRif(new Rif(Dominios.TipoCedula.GOBIERNO, 20007774, 3));
	lconviasa.setNombreLargo("CONSORCIO VENEZOLANO DE INDUSTRIAS AERONAUTICAS Y SERVICIOS AEREOS, S.A.");
	lconviasa.setNombreCorto("CONVIASA");
	lconviasa.setWeb("http://www.conviasa.aero/");
	lconviasa.getTiposPersona().add(tpProveedor);
	dataPersona(lconviasa, a);
	s.save(lconviasa);

	PersonaJuridica provmrw = new PersonaJuridica();
	provmrw.setRif(new Rif(Dominios.TipoCedula.JURIDICO, 274758, 7));
	provmrw.setNombreLargo("MENSAJEROS RADIO WORLDWIDE C.A");
	provmrw.setNombreCorto("MRW");
	provmrw.setWeb("http://www.mrw.com.ve/");
	provmrw.getTiposPersona().add(tpProveedor);
	dataPersona(provmrw, a);
	s.save(provmrw);

	PersonaJuridica provzoom = new PersonaJuridica();
	provzoom.setRif(new Rif(Dominios.TipoCedula.JURIDICO, 102174, 4));
	provzoom.setNombreLargo("ZOOM INTERNATIONAL SERVICES C A");
	provzoom.setNombreCorto("ZOOM");
	provzoom.setWeb("http://www.grupozoom.com/");
	provzoom.getTiposPersona().add(tpProveedor);
	dataPersona(provzoom, a);
	s.save(provzoom);

	PersonaJuridica provipostel = new PersonaJuridica();
	provipostel.setRif(new Rif(Dominios.TipoCedula.JURIDICO, 20000043, 0));
	provipostel.setNombreLargo("INSTITUTO POSTAL TELEGRAFICO DE VENEZUELA ");
	provipostel.setNombreCorto("IPOSTEL");
	provipostel.setWeb("http://www.ipostel.gov.ve/");
	provipostel.getTiposPersona().add(tpProveedor);
	dataPersona(provipostel, a);
	s.save(provipostel);
    }

    private void calendarioBancario(AuditoriaBasica a) {
	ArrayList<CalendarioBancario> list = new ArrayList<CalendarioBancario>(0);
	list.add(new CalendarioBancario("AÑO NUEVO", "", new Date(110, 0, 1), false, true, false, a));
	list.add(new CalendarioBancario("DIA DE REYES", "", new Date(110, 0, 4), true, false, false, a));
	list.add(new CalendarioBancario("CARNAVAL", "", new Date(110, 1, 15), false, true, false, a));
	list.add(new CalendarioBancario("CARNAVAL", "", new Date(110, 1, 16), false, true, false, a));
	list.add(new CalendarioBancario("DIA DE SAN JOSE", "", new Date(110, 2, 19), true, false, false, a));
	list.add(new CalendarioBancario("SEMANA SANTA", "", new Date(110, 2, 1), false, true, false, a));
	list.add(new CalendarioBancario("SEMANA SANTA", "", new Date(110, 2, 2), false, true, false, a));
	list.add(new CalendarioBancario("MOVIMIENTO PRECURSOR DE LA INDEPENDENCIA", "", new Date(110, 3, 19), false, true, false, a));
	list.add(new CalendarioBancario("DIA DEL TRABAJADOR", "", new Date(110, 4, 1), false, true, false, a));
	list.add(new CalendarioBancario("ASCENSION DEL SEÑOR", "", new Date(110, 4, 17), true, false, false, a));
	list.add(new CalendarioBancario("CORPUS CHRISTIE", "", new Date(110, 5, 07), true, false, false, a));
	list.add(new CalendarioBancario("BATALLA DE CARABOBO", "", new Date(110, 5, 24), false, true, false, a));
	list.add(new CalendarioBancario("DIA DE SAN PEDRO Y SAN PABLO", "", new Date(110, 5, 28), true, false, false, a));
	list.add(new CalendarioBancario("DIA DE LA INDEPENDECIA", "", new Date(110, 6, 5), false, true, false, a));
	list.add(new CalendarioBancario("NATALICIO DEL LIBERTADOR", "", new Date(110, 6, 24), false, true, false, a));
	list.add(new CalendarioBancario("ASSUNCION DE NUESTRA SEÑORA", "", new Date(110, 7, 15), true, false, false, a));
	list.add(new CalendarioBancario("DIA DE LA RESISTENCIA INDIGENA", "", new Date(110, 9, 12), false, true, false, a));
	list.add(new CalendarioBancario("DIA DE TODOS LOS SANTOS", "", new Date(110, 10, 1), true, false, false, a));
	list.add(new CalendarioBancario("DIA DE LA INMACULADA CONCEPCION", "", new Date(110, 10, 6), true, false, false, a));
	list.add(new CalendarioBancario("NATIVIDAD DEL SEÑOR", "", new Date(110, 12, 25), false, true, false, a));

	for (CalendarioBancario o : list) {
	    s.save(o);
	}
    }

    private void valoresEstandares(AuditoriaBasica auditoriaActivo) {
	ValoresEstandares ve = new ValoresEstandares();
	ve.setIva(12.0);
//	ve.setUt(65.0);
    }

    public static void main(String[] args) {
	try {
	    Crear bd = new Crear();
//	    bd.open();
	    bd.create();
//	    bd.close();
	} catch (Exception ex) {
	    ex.printStackTrace();
	}
    }

}
