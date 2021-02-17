

package mx.gob.cdmx.federal10_20210217.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import mx.gob.cdmx.federal10_20210217.db.Anotaciones.AutoIncrement;
import mx.gob.cdmx.federal10_20210217.db.Anotaciones.PrimaryKey;

public class encuestas implements Serializable {

    @PrimaryKey
    @AutoIncrement
    public int id;
    @SerializedName("consecutivo_diario ")  public String consecutivo_diario ;
    @SerializedName("equipo ")  public String equipo ;
    @SerializedName("usuario ")  public String usuario ;
    @SerializedName("nombre_encuesta ")  public String nombre_encuesta ;
    @SerializedName("fecha ")  public String fecha ;
    @SerializedName("hora ")  public String hora ;
    @SerializedName("imei ")  public String imei ;
    @SerializedName("seccion ")  public String seccion ;
    @SerializedName("latitud ")  public String latitud ;
    @SerializedName("longitud ")  public String longitud ;


    @SerializedName("pregunta_1")  public String pregunta_1 ;
    @SerializedName("pregunta_2")  public String pregunta_2 ;
    @SerializedName("pregunta_3")  public String pregunta_3 ;
    @SerializedName("pregunta_4")  public String pregunta_4 ;
    @SerializedName("pregunta_5")  public String pregunta_5 ;
    @SerializedName("pregunta_6")  public String pregunta_6 ;
    @SerializedName("pregunta_7")  public String pregunta_7 ;
    @SerializedName("pregunta_covi")  public String pregunta_covi ;
    @SerializedName("pregunta_covi1")  public String pregunta_covi1 ;
    @SerializedName("pregunta_covi2")  public String pregunta_covi2 ;
    @SerializedName("pregunta_covi3")  public String pregunta_covi3 ;
    @SerializedName("pregunta_covi4")  public String pregunta_covi4 ;
    @SerializedName("pregunta_8")  public String pregunta_8 ;
    @SerializedName("pregunta_9")  public String pregunta_9 ;
    @SerializedName("pregunta_10")  public String pregunta_10 ;
    @SerializedName("pregunta_11")  public String pregunta_11 ;
    @SerializedName("pregunta_12")  public String pregunta_12 ;
    @SerializedName("pregunta_13")  public String pregunta_13 ;
    @SerializedName("pregunta_14")  public String pregunta_14 ;
    @SerializedName("pregunta_15")  public String pregunta_15 ;
    @SerializedName("pregunta_16")  public String pregunta_16 ;
    @SerializedName("pregunta_16a")  public String pregunta_16a ;
    @SerializedName("pregunta_16b")  public String pregunta_16b ;
    @SerializedName("pregunta_17")  public String pregunta_17 ;
    @SerializedName("pregunta_18")  public String pregunta_18 ;
    @SerializedName("pregunta_19")  public String pregunta_19 ;
    @SerializedName("pregunta_19a")  public String pregunta_19a ;
    @SerializedName("pregunta_19b")  public String pregunta_19b ;
    @SerializedName("pregunta_20")  public String pregunta_20 ;
    @SerializedName("pregunta_21")  public String pregunta_21 ;
    @SerializedName("pregunta_22")  public String pregunta_22 ;
    @SerializedName("pregunta_23a")  public String pregunta_23a ;
    @SerializedName("pregunta_23b")  public String pregunta_23b ;
    @SerializedName("pregunta_23c")  public String pregunta_23c ;
    @SerializedName("pregunta_24")  public String pregunta_24 ;
    @SerializedName("pregunta_24_1")  public String pregunta_24_1 ;
    @SerializedName("pregunta_24_1a")  public String pregunta_24_1a ;
    @SerializedName("pregunta_24_1b")  public String pregunta_24_1b ;
    @SerializedName("pregunta_24_2")  public String pregunta_24_2 ;
    @SerializedName("pregunta_24_2a")  public String pregunta_24_2a ;
    @SerializedName("pregunta_24_2b")  public String pregunta_24_2b ;
    @SerializedName("pregunta_24_3")  public String pregunta_24_3 ;
    @SerializedName("pregunta_24_3a")  public String pregunta_24_3a ;
    @SerializedName("pregunta_24_3b")  public String pregunta_24_3b ;
    @SerializedName("pregunta_25")  public String pregunta_25 ;
    @SerializedName("pregunta_26")  public String pregunta_26 ;
    @SerializedName("pregunta_27")  public String pregunta_27 ;
    @SerializedName("pregunta_28")  public String pregunta_28 ;
    @SerializedName("pregunta_29")  public String pregunta_29 ;

    @SerializedName("aporta  ")  public String aporta  ;
    @SerializedName("ocupacion  ")  public String ocupacion  ;
    @SerializedName("cuantos_coches  ")  public String cuantos_coches  ;
    @SerializedName("cuartos  ")  public String cuartos  ;
    @SerializedName("cuartos_dormir  ")  public String cuartos_dormir  ;
    @SerializedName("focos  ")  public String focos  ;
    @SerializedName("banos  ")  public String banos  ;
    @SerializedName("regadera  ")  public String regadera  ;
    @SerializedName("estufa  ")  public String estufa  ;
    @SerializedName("edad  ")  public String edad  ;
    @SerializedName("genero  ")  public String genero  ;
    @SerializedName("tipo_vivienda  ")  public String tipo_vivienda  ;
    @SerializedName("tipo_piso  ")  public String tipo_piso  ;
    @SerializedName("abandono ")  public String abandono ;
    @SerializedName("suma ")  public String suma ;
    @SerializedName("status ")  public String status ;
    @SerializedName("tiempo ")  public String tiempo ;
    @SerializedName("tipo_captura ")  public String tipo_captura ;
    @SerializedName("enviado ")  public String enviado ;


    public encuestas() {
    }




}

