package com.unad.documentinspector.repositorio;

import com.unad.documentinspector.JpaUtil;
import com.unad.documentinspector.entidad.Documento;
import com.unad.documentinspector.entidad.Firma;
import com.unad.documentinspector.entidad.HistorialFirma;
import com.unad.documentinspector.entidad.Usuario;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

public class HistorialFirmaRepository {


    public List<HistorialFirma> findAll() {

        EntityManager em = JpaUtil.createEntityManager();
        try {
            return em.createQuery("SELECT u FROM HistorialFirma u", HistorialFirma.class).getResultList();
        } finally {
            em.close();
        }
    }

    public Optional<HistorialFirma> findById(Long id) {

        EntityManager em = JpaUtil.createEntityManager();
        try {
            return Optional.ofNullable(em.find(HistorialFirma.class, id));
        } finally {
            em.close();
        }
    }

    public List<HistorialFirma> findByFirma(Firma firma) {

        EntityManager em = JpaUtil.createEntityManager();
        try {
            return em.createQuery("SELECT u FROM HistorialFirma u " +
                            "WHERE u.firma = :firma", HistorialFirma.class)
                    .setParameter("firma", firma)
                    .getResultList();
        } finally {
            em.close();
        }
    }


    public HistorialFirma findByDocumento(Documento documento) {

        EntityManager em = JpaUtil.createEntityManager();
        try {
            return em.createQuery("SELECT u FROM HistorialFirma u " +
                            "WHERE u.documento = :documento", HistorialFirma.class)
                    .setParameter("documento", documento)
                    .getSingleResult();
        } finally {
            em.close();
        }
    }


    public List<HistorialFirma> findBySinFirma(Firma firma) {
        EntityManager em = JpaUtil.createEntityManager();
        try {
            return em.createQuery("SELECT u FROM HistorialFirma u " +
                            "WHERE u.firma = :firma AND u.codigoFirma IS NULL", HistorialFirma.class)
                    .setParameter("firma", firma)
                    .getResultList();
        } finally {
            em.close();
        }
    }


    public HistorialFirma save(HistorialFirma historial) {

        EntityManager em = JpaUtil.createEntityManager();

        try {
            em.getTransaction().begin();
            em.persist(historial);
            em.getTransaction().commit();
            return historial;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    public HistorialFirma update(HistorialFirma historial) {

        EntityManager em = JpaUtil.createEntityManager();
        try {
            em.getTransaction().begin();
            HistorialFirma actualizado = em.merge(historial);
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
