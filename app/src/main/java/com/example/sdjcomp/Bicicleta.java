package com.example.sdjcomp;

public class Bicicleta {
    private int idBicicleta;
    private String cedulaPropietario;
    private String fechaRegistro;
    private String lugarRegistro;
    private int Marca_id;
    private String numSerie;
    private int Tipo_id;
    private String color;
    private String Estudiante_id;

    public Bicicleta() {
    }

    public Bicicleta(String cedulaPropietario, String fechaRegistro, String lugarRegistro, int marca_id, String numSerie, int tipo_id, String color, String estudiante_id) {
        this.cedulaPropietario = cedulaPropietario;
        this.fechaRegistro = fechaRegistro;
        this.lugarRegistro = lugarRegistro;
        Marca_id = marca_id;
        this.numSerie = numSerie;
        Tipo_id = tipo_id;
        this.color = color;
        Estudiante_id = estudiante_id;
    }

    public Bicicleta(int idBicicleta, String cedulaPropietario, String fechaRegistro, String lugarRegistro, int marca_id, String numSerie, int tipo_id, String color, String estudiante_id) {
        this.idBicicleta = idBicicleta;
        this.cedulaPropietario = cedulaPropietario;
        this.fechaRegistro = fechaRegistro;
        this.lugarRegistro = lugarRegistro;
        Marca_id = marca_id;
        this.numSerie = numSerie;
        Tipo_id = tipo_id;
        this.color = color;
        Estudiante_id = estudiante_id;
    }

    public int getIdBicicleta() {
        return idBicicleta;
    }

    public void setIdBicicleta(int idBicicleta) {
        this.idBicicleta = idBicicleta;
    }

    public String getCedulaPropietario() {
        return cedulaPropietario;
    }

    public void setCedulaPropietario(String cedulaPropietario) {
        this.cedulaPropietario = cedulaPropietario;
    }

    public String getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(String fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public String getLugarRegistro() {
        return lugarRegistro;
    }

    public void setLugarRegistro(String lugarRegistro) {
        this.lugarRegistro = lugarRegistro;
    }

    public int getMarca_id() {
        return Marca_id;
    }

    public void setMarca_id(int marca_id) {
        Marca_id = marca_id;
    }

    public String getNumSerie() {
        return numSerie;
    }

    public void setNumSerie(String numSerie) {
        this.numSerie = numSerie;
    }

    public int getTipo_id() {
        return Tipo_id;
    }

    public void setTipo_id(int tipo_id) {
        Tipo_id = tipo_id;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getEstudiante_id() {
        return Estudiante_id;
    }

    public void setEstudiante_id(String estudiante_id) {
        Estudiante_id = estudiante_id;
    }
}
