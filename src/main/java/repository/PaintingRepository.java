package repository;

import dao.AbstractDAO;
import entity.Painter;
import entity.Painting;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.RollbackException;
import java.util.List;

public class PaintingRepository implements AbstractDAO<Painting> {

    private final EntityManager entityManager;

    public PaintingRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void add(Painting painting) {
        entityManager.getTransaction().begin();
        entityManager.persist(painting);
        try {
            entityManager.getTransaction().commit();
        } catch (RollbackException e) {
            entityManager.getTransaction().rollback();
            e.printStackTrace();
        }
    }

    @Override
    public List<Painting> getAll() {
        return entityManager.createNamedQuery("Painting.All", Painting.class).getResultList();
    }

    @Override
    public Painting getById(Long id) {
        return entityManager.createNamedQuery("Painting.getById", Painting.class)
                .setParameter("paintingId", id)
                .getSingleResult();
    }

    @Override
    public void update(Painting painting) {
        entityManager.getTransaction().begin();
        entityManager.merge(painting);
        try {
            entityManager.getTransaction().commit();
        } catch (RollbackException e) {
            entityManager.getTransaction().rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Painting painting) {
        entityManager.getTransaction().begin();
        entityManager.remove(painting);
        if (painting.getGallery() != null && painting.getGallery().getPaintings() != null) {
            painting.getGallery().getPaintings().remove(painting);
        }
        if (painting.getPainters() != null) {
            for (Painter painter : painting.getPainters()) {
                painter.getPaintings().remove(painting);
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
    public void addOrUpdate(Painting painting) {
        entityManager.getTransaction().begin();
        try {
            entityManager.merge(painting);
        } catch (EntityNotFoundException e) {
            entityManager.persist(painting);
        }
        try {
            entityManager.getTransaction().commit();
        } catch (RollbackException e) {
            entityManager.getTransaction().rollback();
            e.printStackTrace();
        }
    }
}
