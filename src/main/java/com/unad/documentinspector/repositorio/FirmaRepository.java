package com.unad.documentinspector.repositorio;

import com.unad.documentinspector.JpaUtil;
import com.unad.documentinspector.entidad.Documento;
import com.unad.documentinspector.entidad.Firma;
import com.unad.documentinspector.entidad.Usuario;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

public class FirmaRepository {


    public List<Firma> findAll() {

        EntityManager em = JpaUtil.createEntityManager();
        try {
            return em.createQuery("SELECT u FROM Firma u", Firma.class).getResultList();
        } finally {
            em.close();
        }
    }

    public Optional<Firma> findById(Long id) {

        EntityManager em = JpaUtil.createEntityManager();
        try {
            return Optional.ofNullable(em.find(Firma.class, id));
        } finally {
            em.close();
        }
    }

    public Firma findByUsuario(Usuario usuario) {

        EntityManager em = JpaUtil.createEntityManager();
        try {
            return em.createQuery("SELECT u FROM Firma u " +
                            "WHERE u.usuario = :usuario", Firma.class)
                    .setParameter("usuario", usuario)
                    .getSingleResult();
        } catch (jakarta.persistence.NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }

    public Firma save(Firma firma) {

        EntityManager em = JpaUtil.createEntityManager();

        try {
            em.getTransaction().begin();
            em.persist(firma);
            em.getTransaction().commit();
            return firma;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    public Firma update(Firma firma) {

        EntityManager em = JpaUtil.createEntityManager();
        try {
            em.getTransaction().begin();
            Firma actualizado = em.merge(firma);
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
