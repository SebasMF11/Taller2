package com.entregable1.proyectoentregable.Models.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Detalles")
public class Detalle {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    private Long idEncabezado;
    private Long idProducto;
    private int cantidad;
    private float valor;
    private float subtotal;
    private float total;
    private float descuento;

    public Detalle(Long idEncabezado, Long idProducto, int cantidad, float valor, float descuento) {
        this.idEncabezado = idEncabezado;
        this.idProducto = idProducto;
        this.cantidad = cantidad;
        this.valor = valor;
        this.descuento = descuento;
        this.subtotal = valor*cantidad;
        this.total = subtotal-descuento;
    }

    public Detalle (){

    }

    public float getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(float subtotal) {
        this.subtotal = subtotal;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdEncabezado() {
        return idEncabezado;
    }

    public void setIdEncabezado(Long idEncabezado) {
        this.idEncabezado = idEncabezado;
    }

    public Long getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Long idProducto) {
        this.idProducto = idProducto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    public float getDescuento() {
        return descuento;
    }

    public void setDescuento(float descuento) {
        this.descuento = descuento;
    }

    
}
