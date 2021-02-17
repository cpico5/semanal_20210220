package mx.gob.cdmx.federal10_20210217.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import mx.gob.cdmx.federal10_20210217.db.Anotaciones.PrimaryKey;

public class Usuarios implements Serializable {

    @PrimaryKey

    @SerializedName("usuario")
    public String usuario;
    @SerializedName("password")
    public String password;



    public Usuarios() {
    }

    public Usuarios(String usuario, String password) {
        this.usuario = usuario;
        this.password = password;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
