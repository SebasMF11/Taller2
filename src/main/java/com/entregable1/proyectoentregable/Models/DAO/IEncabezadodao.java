package com.entregable1.proyectoentregable.Models.DAO;

import java.util.List;

import com.entregable1.proyectoentregable.Models.Entity.Encabezado;

public interface IEncabezadodao {
    public List<Encabezado> findAll();
    public void update(Encabezado Encabezado);
    public void delete(Long id);
    public void save(Encabezado Encabezado);
    public Encabezado findById(Long id);
}
