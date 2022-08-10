package com.example.sdjcomp;

public class Rol {
    private int codigo;
    private String rol;
    public Rol(){

    }

    public Rol(int codigo, String rol){
        this.codigo = codigo;
        this.rol = rol;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
}
