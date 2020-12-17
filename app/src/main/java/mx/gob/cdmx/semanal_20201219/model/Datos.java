package mx.gob.cdmx.semanal_20201219.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import mx.gob.cdmx.semanal_20201219.db.Anotaciones.PrimaryKey;

public class Datos implements Serializable {



    @PrimaryKey

    @SerializedName("seccion")
    public String seccion;
    @SerializedName("distrito")
    public String distrito;
    @SerializedName("municipio")
    public String municipio;
    @SerializedName("delegacion")
    public String delegacion;
    @SerializedName("equipo")
    public String equipo;
    @SerializedName("coordinador")
    public String coordinador;
    @SerializedName("nombre_encuesta")
    public String nombre_encuesta;

    public Datos() {
    }

    public Datos(String seccion, String distrito, String municipio, String delegacion, String equipo, String coordinador, String nombre_encuesta) {
        this.seccion = seccion;
        this.distrito = distrito;
        this.municipio = municipio;
        this.delegacion = delegacion;
        this.equipo = equipo;
        this.coordinador = coordinador;
        this.nombre_encuesta = nombre_encuesta;
    }

    public String getSeccion() {
        return seccion;
    }

    public void setSeccion(String seccion) {
        this.seccion = seccion;
    }

    public String getDistrito() {
        return distrito;
    }

    public void setDistrito(String distrito) {
        this.distrito = distrito;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getDelegacion() {
        return delegacion;
    }

    public void setDelegacion(String delegacion) {
        this.delegacion = delegacion;
    }

    public String getEquipo() {
        return equipo;
    }

    public void setEquipo(String equipo) {
        this.equipo = equipo;
    }

    public String getCoordinador() {
        return coordinador;
    }

    public void setCoordinador(String coordinador) {
        this.coordinador = coordinador;
    }

    public String getNombre_encuesta() {
        return nombre_encuesta;
    }

    public void setNombre_encuesta(String nombre_encuesta) {
        this.nombre_encuesta = nombre_encuesta;
    }
}
