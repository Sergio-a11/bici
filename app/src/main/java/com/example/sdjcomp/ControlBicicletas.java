package com.example.sdjcomp;

public class ControlBicicletas {
    private int id, idBicicleta, Marca_id, Tipo_id;
    private String cedulaPropietario, fechaRegistro, lugarRegistro, numSerie, color, Estudiante_id, created_date, last_modified_date, status;

    public ControlBicicletas(int id, int idBicicleta, int marca_id, int tipo_id, String cedulaPropietario, String fechaRegistro, String lugarRegistro, String numSerie, String color, String Estudiante_id, String created_date, String last_modified_date, String status) {
        this.id = id;
        this.idBicicleta = idBicicleta;
        Marca_id = marca_id;
        Tipo_id = tipo_id;
        this.cedulaPropietario = cedulaPropietario;
        this.fechaRegistro = fechaRegistro;
        this.lugarRegistro = lugarRegistro;
        this.numSerie = numSerie;
        this.color = color;
        this.created_date = created_date;
        this.last_modified_date = last_modified_date;
        this.status = status;
        this.Estudiante_id = Estudiante_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdBicicleta() {
        return idBicicleta;
    }

    public void setIdBicicleta(int idBicicleta) {
        this.idBicicleta = idBicicleta;
    }

    public int getMarca_id() {
        return Marca_id;
    }

    public void setMarca_id(int marca_id) {
        Marca_id = marca_id;
    }

    public int getTipo_id() {
        return Tipo_id;
    }

    public void setTipo_id(int tipo_id) {
        Tipo_id = tipo_id;
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

    public String getNumSerie() {
        return numSerie;
    }

    public void setNumSerie(String numSerie) {
        this.numSerie = numSerie;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getCreated_date() {
        return created_date;
    }

    public void setCreated_date(String created_date) {
        this.created_date = created_date;
    }

    public String getLast_modified_date() {
        return last_modified_date;
    }

    public void setLast_modified_date(String last_modified_date) {
        this.last_modified_date = last_modified_date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEstudiante_id() {
        return Estudiante_id;
    }

    public void setEstudiante_id(String estudiante_id) {
        Estudiante_id = estudiante_id;
    }
}
