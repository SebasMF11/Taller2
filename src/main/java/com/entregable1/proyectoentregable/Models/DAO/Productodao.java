package com.entregable1.proyectoentregable.Models.DAO;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.entregable1.proyectoentregable.Models.Entity.Producto;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class Productodao implements IProductodao {
    @PersistenceContext
    private EntityManager em;
    
    @SuppressWarnings("unchecked")
    @Transactional(readOnly = true)
    @Override
    public List<Producto> findAll(){
        return em.createQuery("from Producto").getResultList();
    }

    @Override
    @Transactional
    public void update(Producto producto){
        em.merge(producto);
    }

    @Override
    @Transactional
    public void delete(Long id){
        em.remove(findAll().stream().filter(p -> p.getId() == id).findFirst().orElse(null));
    }
    
    @Override
    @Transactional
    public void save(Producto producto){
        em.persist(producto);
    }
    
    @Override
    public Producto findById(Long id){
        return em.find(Producto.class, id);
    }
}
