package com.unad.documentinspector.repositorio;

import com.unad.documentinspector.JpaUtil;
import com.unad.documentinspector.entidad.Rol;
import com.unad.documentinspector.entidad.TipoDocumento;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

public class RolRepository {


    public List<Rol> findAll() {

        EntityManager em = JpaUtil.createEntityManager();
        try {
            return em.createQuery("SELECT u FROM Rol u", Rol.class).getResultList();
        } finally {
            em.close();
        }
    }

    public Optional<Rol> findById(Long id) {

        EntityManager em = JpaUtil.createEntityManager();
        try {
            return Optional.ofNullable(em.find(Rol.class, id));
        } finally {
            em.close();
        }
    }

    public List<Rol> findByLstNombre(String nombre) {

        EntityManager em = JpaUtil.createEntityManager();
        try {
            return em.createQuery("SELECT u FROM Rol u " +
                            "WHERE u.nombre = :nombre", Rol.class)
                    .setParameter("nombre", nombre)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public Rol findByNombre(String nombre) {
        EntityManager em = JpaUtil.createEntityManager();
        try {
            return em.createQuery("SELECT u FROM Rol u WHERE u.nombre = :nombre", Rol.class)
                    .setParameter("nombre", nombre)
                    .getSingleResult();
        } finally {
            em.close();
        }
    }

    public Rol save(Rol rol) {

        EntityManager em = JpaUtil.createEntityManager();

        try {
            em.getTransaction().begin();
            em.persist(rol);
            em.getTransaction().commit();
            return rol;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    public Rol update(Rol rol) {

        EntityManager em = JpaUtil.createEntityManager();
        try {
            em.getTransaction().begin();
            Rol actualizado = em.merge(rol);
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
