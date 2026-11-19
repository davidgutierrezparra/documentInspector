package com.unad.documentinspector.repositorio;

import com.unad.documentinspector.JpaUtil;
import com.unad.documentinspector.entidad.Documento;
import com.unad.documentinspector.entidad.TipoDocumento;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

public class TipoDocumentoRepository {


    public List<TipoDocumento> findAll() {

        EntityManager em = JpaUtil.createEntityManager();
        try {
            return em.createQuery("SELECT u FROM TipoDocumento u", TipoDocumento.class).getResultList();
        } finally {
            em.close();
        }
    }

    public Optional<TipoDocumento> findById(Long id) {

        EntityManager em = JpaUtil.createEntityManager();
        try {
            return Optional.ofNullable(em.find(TipoDocumento.class, id));
        } finally {
            em.close();
        }
    }

    public List<TipoDocumento> findByLstNombre(String nombre) {

        EntityManager em = JpaUtil.createEntityManager();
        try {
            return em.createQuery("SELECT u FROM TipoDocumento u " +
                            "WHERE u.nombre = :nombre", TipoDocumento.class)
                    .setParameter("nombre", nombre)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public TipoDocumento findByNombre(String nombre) {
        EntityManager em = JpaUtil.createEntityManager();
        try {
            return em.createQuery("SELECT u FROM TipoDocumento u WHERE u.nombre = :nombre", TipoDocumento.class)
                    .setParameter("nombre", nombre)
                    .getSingleResult();
        } finally {
            em.close();
        }
    }

    public TipoDocumento save(TipoDocumento documento) {

        EntityManager em = JpaUtil.createEntityManager();

        try {
            em.getTransaction().begin();
            em.persist(documento);
            em.getTransaction().commit();
            return documento;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    public TipoDocumento update(TipoDocumento documento) {

        EntityManager em = JpaUtil.createEntityManager();
        try {
            em.getTransaction().begin();
            TipoDocumento actualizado = em.merge(documento);
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
