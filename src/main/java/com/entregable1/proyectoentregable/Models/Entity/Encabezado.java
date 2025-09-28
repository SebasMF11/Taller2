package com.entregable1.proyectoentregable.Models.Entity;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Encabezados")
public class Encabezado {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    private Long idCliente;
    private Date fecha;
    private float subTotal;
    private float total;
    private float descuentottl;

    
    public Encabezado( Long idCliente, Date fecha, float subTotal, float total, float descuentottl) {
        this.idCliente = idCliente;
        this.fecha = fecha;
        this.subTotal = subTotal;
        this.total = total;
        this.descuentottl = descuentottl;
    }

    public Encabezado(){
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getIdCliente() {
        return idCliente;
    }
    public void setIdCliente(Long idCliente) {
        this.idCliente = idCliente;
    }
    public Date getFecha() {
        return fecha;
    }
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    public float getSubTotal() {
        return subTotal;
    }
    public void setSubTotal(float subTotal) {
        this.subTotal = subTotal;
    }
    public float getTotal() {
        return total;
    }
    public void setTotal(float total) {
        this.total = total;
    }
    public float getDescuentottl() {
        return descuentottl;
    }
    public void setDescuentottl(float descuento) {
        this.descuentottl = descuento;
    }
}
