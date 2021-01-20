package mx.gob.cdmx.covid_atencion;

public class Nombre  {

/*
    Álvaro Obregón
    Azcapotzalco
    Benito Juárez
    Coyoacán
    Cuajimalpa de Morelos
    Cuauhtémoc
    Gustavo A. Madero
    Iztacalco
    Iztapalapa
    La Magdalena Contreras
    Miguel Hidalgo
    Milpa Alta
    Tláhuac
    Tlalpan
    Venustiano Carranza
    Xochimilco
    */


    public static final String customURL = "https://opinion.cdmx.gob.mx/encuestas/";
    public static final String customURLcatalogos = "https://opinion.cdmx.gob.mx/encuestas/catalogos/";
    public static final String encuesta = "covid_atencion";
    public static final String USUARIO = "usuario";
    public static final String ALCALDIA = "Todas";
    public static final String PADRON = "padron";
    public static final String LATITUDE = "latitude";
    public static final String LONGITUDE = "longitude";
    public static final String IMEI = "imei";

    public String nombreEncuesta(){

        final String nombreEncuesta = "covid_atencion";
        return nombreEncuesta;
    }

    public String nombreDatos(){

        final String nombreEncuesta = "datos_covid_atencion";
        return nombreEncuesta;
    }

}	 