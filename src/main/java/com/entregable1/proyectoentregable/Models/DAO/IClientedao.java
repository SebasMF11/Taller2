package com.entregable1.proyectoentregable.Models.DAO;

import java.util.List;

import com.entregable1.proyectoentregable.Models.Entity.Cliente;


public interface IClientedao {
    public List<Cliente> findAll();
    public Cliente findById(Long id);
    public void update(Cliente cliente);
    public void delete(Long id);
    public void save(Cliente cliente);
}