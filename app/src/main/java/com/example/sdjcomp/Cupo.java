package com.example.sdjcomp;

public class Cupo {
    private int idCupo;
    private String seccion;
    private boolean estado;

    public Cupo() {
    }

    public Cupo(int idCupo, String seccion, boolean estado) {
        this.idCupo = idCupo;
        this.seccion = seccion;
        this.estado = estado;
    }

    public Cupo( String seccion, boolean estado) {
        this.seccion = seccion;
        this.estado = estado;
    }

    public int getIdCupo() {
        return idCupo;
    }

    public void setIdCupo(int idCupo) {
        this.idCupo = idCupo;
    }

    public String getSeccion() {
        return seccion;
    }

    public void setSeccion(String seccion) {
        this.seccion = seccion;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }
}
