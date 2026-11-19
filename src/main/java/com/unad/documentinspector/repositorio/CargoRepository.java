package com.unad.documentinspector.repositorio;

import com.unad.documentinspector.JpaUtil;
import com.unad.documentinspector.entidad.Area;
import com.unad.documentinspector.entidad.Cargo;
import com.unad.documentinspector.entidad.Rol;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

public class CargoRepository {


    public List<Cargo> findAll() {

        EntityManager em = JpaUtil.createEntityManager();
        try {
            return em.createQuery("SELECT u FROM Cargo u", Cargo.class).getResultList();
        } finally {
            em.close();
        }
    }

    public Optional<Cargo> findById(Long id) {

        EntityManager em = JpaUtil.createEntityManager();
        try {
            return Optional.ofNullable(em.find(Cargo.class, id));
        } finally {
            em.close();
        }
    }

    public List<Cargo> findByLstNombre(String nombre) {

        EntityManager em = JpaUtil.createEntityManager();
        try {
            return em.createQuery("SELECT u FROM Cargo u " +
                            "WHERE u.nombre = :nombre", Cargo.class)
                    .setParameter("nombre", nombre)
                    .getResultList();
        } finally {
            em.close();
        }
    }


    public List<Cargo> findByLstArea(Area area) {

        EntityManager em = JpaUtil.createEntityManager();
        try {
            return em.createQuery("SELECT u FROM Cargo u " +
                            "WHERE u.area = :area", Cargo.class)
                    .setParameter("area", area)
                    .getResultList();
        } finally {
            em.close();
        }
    }


    public Cargo findByNombre(String nombre) {
        EntityManager em = JpaUtil.createEntityManager();
        try {
            return em.createQuery("SELECT u FROM Cargo u WHERE u.nombre = :nombre", Cargo.class)
                    .setParameter("nombre", nombre)
                    .getSingleResult();
        } finally {
            em.close();
        }
    }

    public Cargo save(Cargo cargo) {

        EntityManager em = JpaUtil.createEntityManager();

        try {
            em.getTransaction().begin();
            em.persist(cargo);
            em.getTransaction().commit();
            return cargo;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    public Cargo update(Cargo cargo) {

        EntityManager em = JpaUtil.createEntityManager();
        try {
            em.getTransaction().begin();
            Cargo actualizado = em.merge(cargo);
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
