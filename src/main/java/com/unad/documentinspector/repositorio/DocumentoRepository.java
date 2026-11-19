package com.unad.documentinspector.repositorio;

import com.unad.documentinspector.JpaUtil;
import com.unad.documentinspector.entidad.Documento;
import com.unad.documentinspector.entidad.Usuario;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

public class DocumentoRepository {

    public List<Documento> findAll() {

        EntityManager em = JpaUtil.createEntityManager();
        try {
            return em.createQuery("SELECT u FROM Documento u", Documento.class).getResultList();
        } finally {
            em.close();
        }
    }

    public Optional<Documento> findById(Long id) {

        EntityManager em = JpaUtil.createEntityManager();
        try {
            return Optional.ofNullable(em.find(Documento.class, id));
        } finally {
            em.close();
        }
    }

    public List<Documento> findByNombre(String nombres) {

        EntityManager em = JpaUtil.createEntityManager();
        try {
            return em.createQuery("SELECT u FROM Documento u " +
                            "WHERE u.nombres = :nombres", Documento.class)
                    .setParameter("nombres", nombres)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public List<Documento> findByUsuario(Usuario usuario) {

        EntityManager em = JpaUtil.createEntityManager();
        try {
            return em.createQuery("SELECT u FROM Documento u " +
                            "WHERE u.usuarioFk = :usuario", Documento.class)
                    .setParameter("usuario", usuario)
                    .getResultList();
        } catch (jakarta.persistence.NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }

    public Documento save(Documento documento) {

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

    public Documento update(Documento documento) {

        EntityManager em = JpaUtil.createEntityManager();
        try {
            em.getTransaction().begin();
            Documento actualizado = em.merge(documento);
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
