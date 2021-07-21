package com.example.api_oxford_dic;

public class Definicion {

    String titulo;
    String definicion;

    public Definicion(String titulo, String definicion) {
        this.titulo = titulo;
        this.definicion = definicion;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDefinicion() {
        return definicion;
    }

    public void setDefinicion(String definicion) {
        this.definicion = definicion;
    }
}
