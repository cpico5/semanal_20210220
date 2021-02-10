package mx.gob.cdmx.semanal_20210206.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import mx.gob.cdmx.semanal_20210206.db.Anotaciones.AutoIncrement;
import mx.gob.cdmx.semanal_20210206.db.Anotaciones.PrimaryKey;

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


    @SerializedName("pregunta_8")  public String pregunta_8 ;


    @SerializedName("pregunta_9")  public String pregunta_9 ;


    @SerializedName("pregunta_c1")  public String pregunta_c1 ;


    @SerializedName("pregunta_c2")  public String pregunta_c2 ;


    @SerializedName("pregunta_c3")  public String pregunta_c3 ;


    @SerializedName("pregunta_c3a")  public String pregunta_c3a ;


    @SerializedName("pregunta_c4")  public String pregunta_c4 ;


    @SerializedName("pregunta_c4a")  public String pregunta_c4a ;


    @SerializedName("pregunta_c4b")  public String pregunta_c4b ;


    @SerializedName("pregunta_c4c")  public String pregunta_c4c ;


    @SerializedName("pregunta_c4d")  public String pregunta_c4d ;


    @SerializedName("pregunta_c5")  public String pregunta_c5 ;


    @SerializedName("pregunta_c5a")  public String pregunta_c5a ;


    @SerializedName("pregunta_c5b")  public String pregunta_c5b ;


    @SerializedName("pregunta_c5c")  public String pregunta_c5c ;


    @SerializedName("pregunta_c5d")  public String pregunta_c5d ;


    @SerializedName("pregunta_c5e")  public String pregunta_c5e ;


    @SerializedName("pregunta_c5f")  public String pregunta_c5f ;


    @SerializedName("pregunta_c5g")  public String pregunta_c5g ;


    @SerializedName("pregunta_c6")  public String pregunta_c6 ;


    @SerializedName("pregunta_c6a")  public String pregunta_c6a ;


    @SerializedName("pregunta_c7")  public String pregunta_c7 ;


    @SerializedName("pregunta_c8")  public String pregunta_c8 ;


    @SerializedName("pregunta_c9")  public String pregunta_c9 ;


    @SerializedName("pregunta_c9a")  public String pregunta_c9a ;


    @SerializedName("pregunta_c9b")  public String pregunta_c9b ;


    @SerializedName("pregunta_c10")  public String pregunta_c10 ;


    @SerializedName("pregunta_c10a")  public String pregunta_c10a ;


    @SerializedName("pregunta_c10b")  public String pregunta_c10b ;


    @SerializedName("pregunta_c11")  public String pregunta_c11 ;



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

    public encuestas(int id, String consecutivo_diario, String equipo, String usuario, String nombre_encuesta, String fecha, String hora, String imei, String seccion, String latitud, String longitud, String pregunta_1, String pregunta_2, String pregunta_3, String pregunta_4, String pregunta_5, String pregunta_6, String pregunta_7, String pregunta_8, String pregunta_9, String pregunta_c1, String pregunta_c2, String pregunta_c3, String pregunta_c3a, String pregunta_c4, String pregunta_c4a, String pregunta_c4b, String pregunta_c4c, String pregunta_c4d, String pregunta_c5, String pregunta_c5a, String pregunta_c5b, String pregunta_c5c, String pregunta_c5d, String pregunta_c5e, String pregunta_c5f, String pregunta_c5g, String pregunta_c6, String pregunta_c6a, String pregunta_c7, String pregunta_c8, String pregunta_c9, String pregunta_c9a, String pregunta_c9b, String pregunta_c10, String pregunta_c10a, String pregunta_c10b, String pregunta_c11, String aporta, String ocupacion, String cuantos_coches, String cuartos, String cuartos_dormir, String focos, String banos, String regadera, String estufa, String edad, String genero, String tipo_vivienda, String tipo_piso, String abandono, String suma, String status, String tiempo, String tipo_captura, String enviado) {
        this.id = id;
        this.consecutivo_diario = consecutivo_diario;
        this.equipo = equipo;
        this.usuario = usuario;
        this.nombre_encuesta = nombre_encuesta;
        this.fecha = fecha;
        this.hora = hora;
        this.imei = imei;
        this.seccion = seccion;
        this.latitud = latitud;
        this.longitud = longitud;
        this.pregunta_1 = pregunta_1;
        this.pregunta_2 = pregunta_2;
        this.pregunta_3 = pregunta_3;
        this.pregunta_4 = pregunta_4;
        this.pregunta_5 = pregunta_5;
        this.pregunta_6 = pregunta_6;
        this.pregunta_7 = pregunta_7;
        this.pregunta_8 = pregunta_8;
        this.pregunta_9 = pregunta_9;
        this.pregunta_c1 = pregunta_c1;
        this.pregunta_c2 = pregunta_c2;
        this.pregunta_c3 = pregunta_c3;
        this.pregunta_c3a = pregunta_c3a;
        this.pregunta_c4 = pregunta_c4;
        this.pregunta_c4a = pregunta_c4a;
        this.pregunta_c4b = pregunta_c4b;
        this.pregunta_c4c = pregunta_c4c;
        this.pregunta_c4d = pregunta_c4d;
        this.pregunta_c5 = pregunta_c5;
        this.pregunta_c5a = pregunta_c5a;
        this.pregunta_c5b = pregunta_c5b;
        this.pregunta_c5c = pregunta_c5c;
        this.pregunta_c5d = pregunta_c5d;
        this.pregunta_c5e = pregunta_c5e;
        this.pregunta_c5f = pregunta_c5f;
        this.pregunta_c5g = pregunta_c5g;
        this.pregunta_c6 = pregunta_c6;
        this.pregunta_c6a = pregunta_c6a;
        this.pregunta_c7 = pregunta_c7;
        this.pregunta_c8 = pregunta_c8;
        this.pregunta_c9 = pregunta_c9;
        this.pregunta_c9a = pregunta_c9a;
        this.pregunta_c9b = pregunta_c9b;
        this.pregunta_c10 = pregunta_c10;
        this.pregunta_c10a = pregunta_c10a;
        this.pregunta_c10b = pregunta_c10b;
        this.pregunta_c11 = pregunta_c11;
        this.aporta = aporta;
        this.ocupacion = ocupacion;
        this.cuantos_coches = cuantos_coches;
        this.cuartos = cuartos;
        this.cuartos_dormir = cuartos_dormir;
        this.focos = focos;
        this.banos = banos;
        this.regadera = regadera;
        this.estufa = estufa;
        this.edad = edad;
        this.genero = genero;
        this.tipo_vivienda = tipo_vivienda;
        this.tipo_piso = tipo_piso;
        this.abandono = abandono;
        this.suma = suma;
        this.status = status;
        this.tiempo = tiempo;
        this.tipo_captura = tipo_captura;
        this.enviado = enviado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getConsecutivo_diario() {
        return consecutivo_diario;
    }

    public void setConsecutivo_diario(String consecutivo_diario) {
        this.consecutivo_diario = consecutivo_diario;
    }

    public String getEquipo() {
        return equipo;
    }

    public void setEquipo(String equipo) {
        this.equipo = equipo;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getNombre_encuesta() {
        return nombre_encuesta;
    }

    public void setNombre_encuesta(String nombre_encuesta) {
        this.nombre_encuesta = nombre_encuesta;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getSeccion() {
        return seccion;
    }

    public void setSeccion(String seccion) {
        this.seccion = seccion;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public String getPregunta_1() {
        return pregunta_1;
    }

    public void setPregunta_1(String pregunta_1) {
        this.pregunta_1 = pregunta_1;
    }

    public String getPregunta_2() {
        return pregunta_2;
    }

    public void setPregunta_2(String pregunta_2) {
        this.pregunta_2 = pregunta_2;
    }

    public String getPregunta_3() {
        return pregunta_3;
    }

    public void setPregunta_3(String pregunta_3) {
        this.pregunta_3 = pregunta_3;
    }

    public String getPregunta_4() {
        return pregunta_4;
    }

    public void setPregunta_4(String pregunta_4) {
        this.pregunta_4 = pregunta_4;
    }

    public String getPregunta_5() {
        return pregunta_5;
    }

    public void setPregunta_5(String pregunta_5) {
        this.pregunta_5 = pregunta_5;
    }

    public String getPregunta_6() {
        return pregunta_6;
    }

    public void setPregunta_6(String pregunta_6) {
        this.pregunta_6 = pregunta_6;
    }

    public String getPregunta_7() {
        return pregunta_7;
    }

    public void setPregunta_7(String pregunta_7) {
        this.pregunta_7 = pregunta_7;
    }

    public String getPregunta_8() {
        return pregunta_8;
    }

    public void setPregunta_8(String pregunta_8) {
        this.pregunta_8 = pregunta_8;
    }

    public String getPregunta_9() {
        return pregunta_9;
    }

    public void setPregunta_9(String pregunta_9) {
        this.pregunta_9 = pregunta_9;
    }

    public String getPregunta_c1() {
        return pregunta_c1;
    }

    public void setPregunta_c1(String pregunta_c1) {
        this.pregunta_c1 = pregunta_c1;
    }

    public String getPregunta_c2() {
        return pregunta_c2;
    }

    public void setPregunta_c2(String pregunta_c2) {
        this.pregunta_c2 = pregunta_c2;
    }

    public String getPregunta_c3() {
        return pregunta_c3;
    }

    public void setPregunta_c3(String pregunta_c3) {
        this.pregunta_c3 = pregunta_c3;
    }

    public String getPregunta_c3a() {
        return pregunta_c3a;
    }

    public void setPregunta_c3a(String pregunta_c3a) {
        this.pregunta_c3a = pregunta_c3a;
    }

    public String getPregunta_c4() {
        return pregunta_c4;
    }

    public void setPregunta_c4(String pregunta_c4) {
        this.pregunta_c4 = pregunta_c4;
    }

    public String getPregunta_c4a() {
        return pregunta_c4a;
    }

    public void setPregunta_c4a(String pregunta_c4a) {
        this.pregunta_c4a = pregunta_c4a;
    }

    public String getPregunta_c4b() {
        return pregunta_c4b;
    }

    public void setPregunta_c4b(String pregunta_c4b) {
        this.pregunta_c4b = pregunta_c4b;
    }

    public String getPregunta_c4c() {
        return pregunta_c4c;
    }

    public void setPregunta_c4c(String pregunta_c4c) {
        this.pregunta_c4c = pregunta_c4c;
    }

    public String getPregunta_c4d() {
        return pregunta_c4d;
    }

    public void setPregunta_c4d(String pregunta_c4d) {
        this.pregunta_c4d = pregunta_c4d;
    }

    public String getPregunta_c5() {
        return pregunta_c5;
    }

    public void setPregunta_c5(String pregunta_c5) {
        this.pregunta_c5 = pregunta_c5;
    }

    public String getPregunta_c5a() {
        return pregunta_c5a;
    }

    public void setPregunta_c5a(String pregunta_c5a) {
        this.pregunta_c5a = pregunta_c5a;
    }

    public String getPregunta_c5b() {
        return pregunta_c5b;
    }

    public void setPregunta_c5b(String pregunta_c5b) {
        this.pregunta_c5b = pregunta_c5b;
    }

    public String getPregunta_c5c() {
        return pregunta_c5c;
    }

    public void setPregunta_c5c(String pregunta_c5c) {
        this.pregunta_c5c = pregunta_c5c;
    }

    public String getPregunta_c5d() {
        return pregunta_c5d;
    }

    public void setPregunta_c5d(String pregunta_c5d) {
        this.pregunta_c5d = pregunta_c5d;
    }

    public String getPregunta_c5e() {
        return pregunta_c5e;
    }

    public void setPregunta_c5e(String pregunta_c5e) {
        this.pregunta_c5e = pregunta_c5e;
    }

    public String getPregunta_c5f() {
        return pregunta_c5f;
    }

    public void setPregunta_c5f(String pregunta_c5f) {
        this.pregunta_c5f = pregunta_c5f;
    }

    public String getPregunta_c5g() {
        return pregunta_c5g;
    }

    public void setPregunta_c5g(String pregunta_c5g) {
        this.pregunta_c5g = pregunta_c5g;
    }

    public String getPregunta_c6() {
        return pregunta_c6;
    }

    public void setPregunta_c6(String pregunta_c6) {
        this.pregunta_c6 = pregunta_c6;
    }

    public String getPregunta_c6a() {
        return pregunta_c6a;
    }

    public void setPregunta_c6a(String pregunta_c6a) {
        this.pregunta_c6a = pregunta_c6a;
    }

    public String getPregunta_c7() {
        return pregunta_c7;
    }

    public void setPregunta_c7(String pregunta_c7) {
        this.pregunta_c7 = pregunta_c7;
    }

    public String getPregunta_c8() {
        return pregunta_c8;
    }

    public void setPregunta_c8(String pregunta_c8) {
        this.pregunta_c8 = pregunta_c8;
    }

    public String getPregunta_c9() {
        return pregunta_c9;
    }

    public void setPregunta_c9(String pregunta_c9) {
        this.pregunta_c9 = pregunta_c9;
    }

    public String getPregunta_c9a() {
        return pregunta_c9a;
    }

    public void setPregunta_c9a(String pregunta_c9a) {
        this.pregunta_c9a = pregunta_c9a;
    }

    public String getPregunta_c9b() {
        return pregunta_c9b;
    }

    public void setPregunta_c9b(String pregunta_c9b) {
        this.pregunta_c9b = pregunta_c9b;
    }

    public String getPregunta_c10() {
        return pregunta_c10;
    }

    public void setPregunta_c10(String pregunta_c10) {
        this.pregunta_c10 = pregunta_c10;
    }

    public String getPregunta_c10a() {
        return pregunta_c10a;
    }

    public void setPregunta_c10a(String pregunta_c10a) {
        this.pregunta_c10a = pregunta_c10a;
    }

    public String getPregunta_c10b() {
        return pregunta_c10b;
    }

    public void setPregunta_c10b(String pregunta_c10b) {
        this.pregunta_c10b = pregunta_c10b;
    }

    public String getPregunta_c11() {
        return pregunta_c11;
    }

    public void setPregunta_c11(String pregunta_c11) {
        this.pregunta_c11 = pregunta_c11;
    }

    public String getAporta() {
        return aporta;
    }

    public void setAporta(String aporta) {
        this.aporta = aporta;
    }

    public String getOcupacion() {
        return ocupacion;
    }

    public void setOcupacion(String ocupacion) {
        this.ocupacion = ocupacion;
    }

    public String getCuantos_coches() {
        return cuantos_coches;
    }

    public void setCuantos_coches(String cuantos_coches) {
        this.cuantos_coches = cuantos_coches;
    }

    public String getCuartos() {
        return cuartos;
    }

    public void setCuartos(String cuartos) {
        this.cuartos = cuartos;
    }

    public String getCuartos_dormir() {
        return cuartos_dormir;
    }

    public void setCuartos_dormir(String cuartos_dormir) {
        this.cuartos_dormir = cuartos_dormir;
    }

    public String getFocos() {
        return focos;
    }

    public void setFocos(String focos) {
        this.focos = focos;
    }

    public String getBanos() {
        return banos;
    }

    public void setBanos(String banos) {
        this.banos = banos;
    }

    public String getRegadera() {
        return regadera;
    }

    public void setRegadera(String regadera) {
        this.regadera = regadera;
    }

    public String getEstufa() {
        return estufa;
    }

    public void setEstufa(String estufa) {
        this.estufa = estufa;
    }

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getTipo_vivienda() {
        return tipo_vivienda;
    }

    public void setTipo_vivienda(String tipo_vivienda) {
        this.tipo_vivienda = tipo_vivienda;
    }

    public String getTipo_piso() {
        return tipo_piso;
    }

    public void setTipo_piso(String tipo_piso) {
        this.tipo_piso = tipo_piso;
    }

    public String getAbandono() {
        return abandono;
    }

    public void setAbandono(String abandono) {
        this.abandono = abandono;
    }

    public String getSuma() {
        return suma;
    }

    public void setSuma(String suma) {
        this.suma = suma;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTiempo() {
        return tiempo;
    }

    public void setTiempo(String tiempo) {
        this.tiempo = tiempo;
    }

    public String getTipo_captura() {
        return tipo_captura;
    }

    public void setTipo_captura(String tipo_captura) {
        this.tipo_captura = tipo_captura;
    }

    public String getEnviado() {
        return enviado;
    }

    public void setEnviado(String enviado) {
        this.enviado = enviado;
    }
}

