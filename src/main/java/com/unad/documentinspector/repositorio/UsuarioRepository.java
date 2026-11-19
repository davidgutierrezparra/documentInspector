package com.unad.documentinspector.repositorio;

import com.unad.documentinspector.JpaUtil;
import com.unad.documentinspector.entidad.Usuario;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

public class UsuarioRepository {

    public List<Usuario> findAll() {

        EntityManager em = JpaUtil.createEntityManager();
        try {
            return em.createQuery("SELECT u FROM Usuario u", Usuario.class).getResultList();
        } finally {
            em.close();
        }
    }

    public Optional<Usuario> findById(Long id) {

        EntityManager em = JpaUtil.createEntityManager();
        try {
            return Optional.ofNullable(em.find(Usuario.class, id));
        } finally {
            em.close();
        }
    }

    public List<Usuario> findByLstNombre(String nombres) {

        EntityManager em = JpaUtil.createEntityManager();
        try {
            return em.createQuery("SELECT u FROM Usuario u " +
                            "WHERE u.nombres = :nombres", Usuario.class)
                    .setParameter("nombres", nombres)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public Usuario findByNombre(String nombres) {

        EntityManager em = JpaUtil.createEntityManager();
        try {
            return em.createQuery("SELECT u FROM Usuario u " +
                            "WHERE u.nombres = :nombres", Usuario.class)
                    .setParameter("nombres", nombres)
                    .getSingleResult();
        } finally {
            em.close();
        }
    }

    public Usuario findByIdentificacion(Long identificacion) {
        EntityManager em = JpaUtil.createEntityManager();
        try {
            return em.createQuery("SELECT u FROM Usuario u " +
                            "WHERE u.identificacion = :identificacion", Usuario.class)
                    .setParameter("identificacion", identificacion)
                    .getSingleResult();
        } catch (jakarta.persistence.NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }


    public Usuario save(Usuario usuario) {

        EntityManager em = JpaUtil.createEntityManager();

        try {
            em.getTransaction().begin();
            em.persist(usuario);
            em.getTransaction().commit();
            return usuario;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    public Usuario update(Usuario usuario) {

        EntityManager em = JpaUtil.createEntityManager();
        try {
            em.getTransaction().begin();
            Usuario actualizado = em.merge(usuario);
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
