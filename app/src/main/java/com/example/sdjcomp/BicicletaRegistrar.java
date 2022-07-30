package com.example.sdjcomp;

public class BicicletaRegistrar {
    private String cedulaPropietario;
    private String fechaRegistro;
    private String lugarRegistro;
    private int Marca_id;
    private String numSerie;
    private int Tipo_id;
    private String color;
    private String Estudiante_id;

    public BicicletaRegistrar() {
    }

    public BicicletaRegistrar(String cedulaPropietario, String fechaRegistro, String lugarRegistro, int marca, String numSerie, int tipo, String color, String estudiante_id) {
        this.cedulaPropietario = cedulaPropietario;
        this.fechaRegistro = fechaRegistro;
        this.lugarRegistro = lugarRegistro;
        this.Marca_id = marca;
        this.numSerie = numSerie;
        this.Tipo_id = tipo;
        this.color = color;
        Estudiante_id = estudiante_id;
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

    public int getMarca() {
        return Marca_id;
    }

    public void setMarca(int marca) {
        this.Marca_id = marca;
    }

    public String getNumSerie() {
        return numSerie;
    }

    public void setNumSerie(String numSerie) {
        this.numSerie = numSerie;
    }

    public int getTipo() {
        return Tipo_id;
    }

    public void setTipo(int tipo) {
        this.Tipo_id = tipo;
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
