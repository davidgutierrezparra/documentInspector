package com.unad.documentinspector.repositorio;

import com.unad.documentinspector.JpaUtil;
import com.unad.documentinspector.entidad.Area;
import com.unad.documentinspector.entidad.Cargo;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

public class AreaRepository {


    public List<Area> findAll() {

        EntityManager em = JpaUtil.createEntityManager();
        try {
            return em.createQuery("SELECT u FROM Area u", Area.class).getResultList();
        } finally {
            em.close();
        }
    }

    public Optional<Area> findById(Long id) {

        EntityManager em = JpaUtil.createEntityManager();
        try {
            return Optional.ofNullable(em.find(Area.class, id));
        } finally {
            em.close();
        }
    }

    public List<Area> findByLstNombre(String nombre) {

        EntityManager em = JpaUtil.createEntityManager();
        try {
            return em.createQuery("SELECT u FROM Area u " +
                            "WHERE u.nombre = :nombre", Area.class)
                    .setParameter("nombre", nombre)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public Area findByNombre(String nombre) {
        EntityManager em = JpaUtil.createEntityManager();
        try {
            return em.createQuery("SELECT u FROM Area u WHERE u.nombre = :nombre", Area.class)
                    .setParameter("nombre", nombre)
                    .getSingleResult();
        } finally {
            em.close();
        }
    }

    public Area save(Area area) {

        EntityManager em = JpaUtil.createEntityManager();

        try {
            em.getTransaction().begin();
            em.persist(area);
            em.getTransaction().commit();
            return area;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    public Area update(Area area) {

        EntityManager em = JpaUtil.createEntityManager();
        try {
            em.getTransaction().begin();
            Area actualizado = em.merge(area);
            em.getTransaction().commit();
            return actualizado;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }


}
