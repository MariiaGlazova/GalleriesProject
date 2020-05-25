package repository;

import dao.AbstractDAO;
import entity.Studio;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.RollbackException;
import java.util.List;

public class StudioRepository implements AbstractDAO<Studio> {

    private final EntityManager entityManager;

    public StudioRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void add(Studio studio) {
        entityManager.getTransaction().begin();
        entityManager.persist(studio);
        try {
            entityManager.getTransaction().commit();
        } catch (RollbackException e) {
            entityManager.getTransaction().rollback();
            e.printStackTrace();
        }
    }

    @Override
    public List<Studio> getAll() {
        return entityManager.createNamedQuery("Studio.All", Studio.class).getResultList();
    }

    @Override
    public Studio getById(Long id) {
        return entityManager.createNamedQuery("Studio.getById", Studio.class)
                .setParameter("studioId", id)
                .getSingleResult();
    }

    @Override
    public void update(Studio studio) {
        entityManager.getTransaction().begin();
        entityManager.merge(studio);
        try {
            entityManager.getTransaction().commit();
        } catch (RollbackException e) {
            entityManager.getTransaction().rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Studio studio) {
        entityManager.getTransaction().begin();
        entityManager.remove(studio);
        if (studio.getPainter() != null) {
            studio.getPainter().setStudio(null);
        }
        try {
            entityManager.getTransaction().commit();
        } catch (RollbackException e) {
            entityManager.getTransaction().rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void addOrUpdate(Studio studio) {
        entityManager.getTransaction().begin();
        try {
            entityManager.merge(studio);
        } catch (EntityNotFoundException e) {
            entityManager.persist(studio);
        }
        try {
            entityManager.getTransaction().commit();
        } catch (RollbackException e) {
            entityManager.getTransaction().rollback();
            e.printStackTrace();
        }
    }
}
