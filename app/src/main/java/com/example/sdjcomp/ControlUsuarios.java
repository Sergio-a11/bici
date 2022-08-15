package com.example.sdjcomp;

public class ControlUsuarios {
    private int id, Pseguridad, Rol_id;
    private String codigo, nombre, correo, clave, Rseguridad, created_date, last_modified_date, status;

    public ControlUsuarios(int id, int pseguridad, int rol_id, String codigo, String nombre, String correo, String clave, String rseguridad, String created_date, String last_modified_date, String status) {
        this.id = id;
        Pseguridad = pseguridad;
        Rol_id = rol_id;
        this.codigo = codigo;
        this.nombre = nombre;
        this.correo = correo;
        this.clave = clave;
        Rseguridad = rseguridad;
        this.created_date = created_date;
        this.last_modified_date = last_modified_date;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPseguridad() {
        return Pseguridad;
    }

    public void setPseguridad(int pseguridad) {
        Pseguridad = pseguridad;
    }

    public int getRol_id() {
        return Rol_id;
    }

    public void setRol_id(int rol_id) {
        Rol_id = rol_id;
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

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getRseguridad() {
        return Rseguridad;
    }

    public void setRseguridad(String rseguridad) {
        Rseguridad = rseguridad;
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
}
