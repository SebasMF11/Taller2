package com.entregable1.proyectoentregable.Models.DAO;

import java.util.List;

import com.entregable1.proyectoentregable.Models.Entity.Producto;

public interface IProductodao {
    public List<Producto> findAll();
    public void update(Producto producto);
    public void delete(Long id);
    public void save(Producto producto);
    public Producto findById(Long id);
}