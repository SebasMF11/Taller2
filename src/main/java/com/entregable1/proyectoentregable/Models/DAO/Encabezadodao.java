package com.entregable1.proyectoentregable.Models.DAO;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.entregable1.proyectoentregable.Models.Entity.Encabezado;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class Encabezadodao implements IEncabezadodao{
    @PersistenceContext
    private EntityManager em;
    
    @SuppressWarnings("unchecked")
    @Transactional(readOnly = true)
    @Override
    public List<Encabezado> findAll(){
        return  em.createQuery("from Encabezado").getResultList();
    }

    @Override
    @Transactional
    public void update(Encabezado Encabezado){
        em.merge(Encabezado);
    }

    @Override
    @Transactional
    public void delete(Long id){
        em.remove(findAll().stream().filter(p -> p.getId() == id).findFirst().orElse(null));
    }

    @Override
    @Transactional
    public void save(Encabezado Encabezado){
        em.persist(Encabezado);
    }

    @Override
    @Transactional(readOnly = true)
    public Encabezado findById(Long id){
        return em.find(Encabezado.class, id);
    }
    
}
