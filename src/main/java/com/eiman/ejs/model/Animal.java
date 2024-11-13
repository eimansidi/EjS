package com.eiman.ejs.model;

/**
 * Modelo que representa un animal en la base de datos.
 */
public class Animal {

    private int id;
    private String nombre;
    private String especie;
    private String raza;
    private String sexo;
    private int edad;
    private double peso;
    private String observaciones;
    private String fechaPrimeraConsulta;
    private byte[] foto;

    /**
     * Constructor completo para inicializar todos los campos del modelo.
     *
     * @param id Identificador unico del animal.
     * @param nombre Nombre del animal.
     * @param especie Especie del animal.
     * @param raza Raza del animal.
     * @param sexo Sexo del animal.
     * @param edad Edad del animal.
     * @param peso Peso del animal.
     * @param observaciones Observaciones relacionadas al animal.
     * @param fechaPrimeraConsulta Fecha de la primera consulta del animal.
     * @param foto Foto del animal en formato binario (BLOB).
     */
    public Animal(int id, String nombre, String especie, String raza, String sexo, int edad, double peso, String observaciones, String fechaPrimeraConsulta, byte[] foto) {
        this.id = id;
        this.nombre = nombre;
        this.especie = especie;
        this.raza = raza;
        this.sexo = sexo;
        this.edad = edad;
        this.peso = peso;
        this.observaciones = observaciones;
        this.fechaPrimeraConsulta = fechaPrimeraConsulta;
        this.foto = foto;
    }

    /**
     * Constructor vacio.
     */
    public Animal() {
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getFechaPrimeraConsulta() {
        return fechaPrimeraConsulta;
    }

    public void setFechaPrimeraConsulta(String fechaPrimeraConsulta) {
        this.fechaPrimeraConsulta = fechaPrimeraConsulta;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    @Override
    public String toString() {
        return "Animal{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", especie='" + especie + '\'' +
                ", raza='" + raza + '\'' +
                ", sexo='" + sexo + '\'' +
                ", edad=" + edad +
                ", peso=" + peso +
                ", observaciones='" + observaciones + '\'' +
                ", fechaPrimeraConsulta='" + fechaPrimeraConsulta + '\'' +
                '}';
    }
}
