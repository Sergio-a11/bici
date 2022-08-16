package com.example.sdjcomp;

import java.time.LocalDateTime;

public class ControlParqueaderos {

    private int id, idCupo, idParqueadero, Bicicleta_idBicicleta, Marca_id, Tipo_id;
    private String seccion, cedulaPropietario, fechaRegistro, lugarRegistro, numSerie, color, Estudiante_id,  nombre, correo, status;
    private String arrived_time, departure_time;

    public ControlParqueaderos(int id, int idCupo, int idParqueadero, int bicicleta_idBicicleta, int marca_id, int tipo_id, String seccion, String cedulaPropietario, String fechaRegistro, String lugarRegistro, String numSerie, String color, String estudiante_id, String nombre, String correo, String status, String arrived_time, String departure_time) {
        this.id = id;
        this.idCupo = idCupo;
        this.idParqueadero = idParqueadero;
        Bicicleta_idBicicleta = bicicleta_idBicicleta;
        Marca_id = marca_id;
        Tipo_id = tipo_id;
        this.seccion = seccion;
        this.cedulaPropietario = cedulaPropietario;
        this.fechaRegistro = fechaRegistro;
        this.lugarRegistro = lugarRegistro;
        this.numSerie = numSerie;
        this.color = color;
        Estudiante_id = estudiante_id;
        this.nombre = nombre;
        this.correo = correo;
        this.status = status;
        this.arrived_time = arrived_time;
        this.departure_time = departure_time;
    }

    public ControlParqueaderos(int idCupo, int idParqueadero, String seccion, String cedulaPropietario, String lugarRegistro, String numSerie, String color, String estudiante_id, String nombre, String status, String arrived_time, String departure_time) {
        this.idCupo = idCupo;
        this.idParqueadero = idParqueadero;
        this.seccion = seccion;
        this.cedulaPropietario = cedulaPropietario;
        this.lugarRegistro = lugarRegistro;
        this.numSerie = numSerie;
        this.color = color;
        Estudiante_id = estudiante_id;
        this.nombre = nombre;
        this.status = status;
        this.arrived_time = arrived_time;
        this.departure_time = departure_time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdCupo() {
        return idCupo;
    }

    public void setIdCupo(int idCupo) {
        this.idCupo = idCupo;
    }

    public int getIdParqueadero() {
        return idParqueadero;
    }

    public void setIdParqueadero(int idParqueadero) {
        this.idParqueadero = idParqueadero;
    }

    public int getBicicleta_idBicicleta() {
        return Bicicleta_idBicicleta;
    }

    public void setBicicleta_idBicicleta(int bicicleta_idBicicleta) {
        Bicicleta_idBicicleta = bicicleta_idBicicleta;
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

    public String getSeccion() {
        return seccion;
    }

    public void setSeccion(String seccion) {
        this.seccion = seccion;
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

    public String getEstudiante_id() {
        return Estudiante_id;
    }

    public void setEstudiante_id(String estudiante_id) {
        Estudiante_id = estudiante_id;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getArrived_time() {
        return arrived_time;
    }

    public void setArrived_time(String arrived_time) {
        this.arrived_time = arrived_time;
    }

    public String getDeparture_time() {
        return departure_time;
    }

    public void setDeparture_time(String departure_time) {
        this.departure_time = departure_time;
    }
}
