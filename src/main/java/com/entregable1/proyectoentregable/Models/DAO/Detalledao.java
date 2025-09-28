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
    @Override
    @Transactional(readOnly = true)
    public List<Detalle> findAll() {
        return em.createQuery("from Detalle").getResultList();
    }

    @Override
    @Transactional(readOnly = true)
    public Detalle findById(Long id) {
        return em.find(Detalle.class, id);
    }

    @Override
    @Transactional
    public void save(Detalle detalle) {
        if (detalle.getId() != 0) { 
            em.merge(detalle);
        } else {
            em.persist(detalle);
        }
    }

    @Override
    @Transactional
    public void update(Detalle detalle) {
        em.merge(detalle);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Detalle detalle = findById(id);
        if (detalle != null) {
            em.remove(detalle);
        }
    }
}
