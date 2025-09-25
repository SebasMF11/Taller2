package com.entregable1.proyectoentregable.Models.DAO;

import java.util.List;

import com.entregable1.proyectoentregable.Models.Entity.Detalle;

public interface IDetalledao {
    public List<Detalle> findAll();
    public void update(Detalle Detalle);
    public void delete(Long id);
    public void save(Detalle Detalle);
    public Detalle findById(Long id);
    
}
