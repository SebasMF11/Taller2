package com.entregable1.proyectoentregable.Models.DAO;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.entregable1.proyectoentregable.Models.Entity.Cliente;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class Clientedao implements IClientedao{
    
    @PersistenceContext
    private EntityManager em;
    
    @SuppressWarnings("unchecked")
    @Transactional(readOnly = true)
    @Override
    public List<Cliente> findAll(){
        return em.createQuery("from Cliente").getResultList();
    }

    @Override    
    @Transactional
    public void update(Cliente cliente){
        em.merge(cliente);
    }

    @Override
    @Transactional
    public void delete(Long id){
        em.remove(findAll().stream().filter(c -> c.getId() == id).findFirst().orElse(null));
    }

    @Override
    @Transactional
    public void save(Cliente cliente){
        em.persist(cliente);
    }
    
    @Override
    public Cliente findById(Long id){
        return em.find(Cliente.class, id);
    }
}
