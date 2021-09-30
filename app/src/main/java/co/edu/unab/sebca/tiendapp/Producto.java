package co.edu.unab.sebca.tiendapp;

import java.io.Serializable;

public class Producto implements Serializable {
    private int id;
    private String nombre;
    private double precio;
    private String descripcion;
    private String urlImagen;

    public Producto() {
    }

    @Override
    public String toString() {
        return this.nombre+" ($"+precio+")";
    }

    public Producto(String nombre, double precio, String urlImagen) {
        this.nombre = nombre;
        this.precio = precio;
        this.urlImagen = urlImagen;
        this.descripcion="Sin descripción";
    }

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

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUrlImagen() {
        return urlImagen;
    }

    public void setUrlImagen(String urlImagen) {
        this.urlImagen = urlImagen;
    }
}
