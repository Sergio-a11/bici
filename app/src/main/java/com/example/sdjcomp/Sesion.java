package com.example.sdjcomp;

import android.app.Application;

public class Sesion extends Application {
    private String codigo,nombre,correo;
    private int Rol_id;
    private boolean validado;

    public Sesion() {
    }

    public Sesion(String codigo, String nombre, int rol_id,String Correo, boolean Validado) {
        this.codigo = codigo;
        this.nombre = nombre;
        Rol_id = rol_id;
        this.correo = Correo;
        this.validado = Validado;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getRol_id() {
        return Rol_id;
    }

    public void setRol_id(int rol_id) {
        Rol_id = rol_id;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public boolean isValidado() {
        return validado;
    }

    public void setValidado(boolean validado) {
        this.validado = validado;
    }
}
