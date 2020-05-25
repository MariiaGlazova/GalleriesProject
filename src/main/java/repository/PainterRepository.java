package repository;

import dao.AbstractDAO;
import entity.Gallery;
import entity.Painter;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.RollbackException;
import java.util.List;

public class PainterRepository implements AbstractDAO<Painter> {

    private final EntityManager entityManager;

    public PainterRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void add(Painter painter) {
        entityManager.getTransaction().begin();
        entityManager.persist(painter);
        try {
            entityManager.getTransaction().commit();
        } catch (RollbackException e) {
            entityManager.getTransaction().rollback();
            e.printStackTrace();
        }
    }

    @Override
    public List<Painter> getAll() {
        return entityManager.createNamedQuery("Painter.All", Painter.class).getResultList();
    }

    @Override
    public Painter getById(Long id) {
        return entityManager.createNamedQuery("Painter.getById", Painter.class)
                .setParameter("painterId", id)
                .getSingleResult();
    }

    @Override
    public void update(Painter painter) {
        entityManager.getTransaction().begin();
        entityManager.merge(painter);
        try {
            entityManager.getTransaction().commit();
        } catch (RollbackException e) {
            entityManager.getTransaction().rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Painter painter) {
        entityManager.getTransaction().begin();
        entityManager.remove(painter);
        if (painter.getGalleries() != null) {
            for (Gallery gallery : painter.getGalleries()) {
                gallery.getPainters().remove(painter);
            }
        }
        try {
            entityManager.getTransaction().commit();
        } catch (RollbackException e) {
            entityManager.getTransaction().rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void addOrUpdate(Painter painter) {
        entityManager.getTransaction().begin();
        try {
            entityManager.merge(painter);
        } catch (EntityNotFoundException e) {
            entityManager.persist(painter);
        }
        try {
            entityManager.getTransaction().commit();
        } catch (RollbackException e) {
            entityManager.getTransaction().rollback();
            e.printStackTrace();
        }
    }
}
