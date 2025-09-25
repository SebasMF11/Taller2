package com.entregable1.proyectoentregable.Models.Entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Productos")
public class Producto implements Serializable{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long Id;
    
    private String Nombre;
    @Column(name = "Precio")
    private float ValorUnitario;
    private String descripcion;
    private int stock;

    public Producto(String nombre, long id, float valorunitario, String descripcion, int stock) {
        Nombre = nombre;
        Id = id;
        ValorUnitario = valorunitario;
        this.descripcion = descripcion;
        this.stock = stock;
    }

    public Producto (){
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public float getValorUnitario() {
        return ValorUnitario;
    }

    public void setValorUnitario(float valorunitario) {
        ValorUnitario = valorunitario;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

}