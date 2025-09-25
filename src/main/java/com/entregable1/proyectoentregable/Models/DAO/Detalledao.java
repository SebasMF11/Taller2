package com.entregable1.proyectoentregable.Models.DAO;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.entregable1.proyectoentregable.Models.Entity.Detalle;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class Detalledao implements IDetalledao {
    @PersistenceContext
    private EntityManager em;

    @SuppressWarnings("unchecked")
    @Transactional(readOnly = true)
    @Override
    public List<Detalle> findAll(){
        return  em.createQuery("from Detalle").getResultList();
    }

    @Override
    @Transactional
    public void update(Detalle Detalle){
        em.merge(Detalle);
    }

    @Override
    @Transactional
    public void delete(Long id){
       em.remove(findAll().stream().filter(p -> p.getId() == id).findFirst().orElse(null));
    }

    @Override
    @Transactional
    public void save(Detalle Detalle){
        em.persist(Detalle);
    }

    @Transactional(readOnly = true)
    @Override
    public Detalle findById(Long id){
        return em.find(Detalle.class, id);
    }
}
