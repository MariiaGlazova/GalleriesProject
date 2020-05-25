package repository;

import dao.AbstractDAO;
import entity.Gallery;
import entity.Painting;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.RollbackException;
import java.util.List;

public class GalleryRepository implements AbstractDAO<Gallery> {

    private final EntityManager entityManager;

    public GalleryRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void add(Gallery gallery) {
        entityManager.getTransaction().begin();
        entityManager.persist(gallery);
        try {
            entityManager.getTransaction().commit();
        } catch (RollbackException e) {
            entityManager.getTransaction().rollback();
            e.printStackTrace();
        }
    }

    @Override
    public List<Gallery> getAll() {
        return entityManager.createNamedQuery("Gallery.All", Gallery.class).getResultList();
    }

    @Override
    public Gallery getById(Long id) {
        return entityManager.createNamedQuery("Gallery.getById", Gallery.class)
                .setParameter("galleryId", id)
                .getSingleResult();
    }

    @Override
    public void update(Gallery gallery) {
        entityManager.getTransaction().begin();
        entityManager.merge(gallery);
        try {
            entityManager.getTransaction().commit();
        } catch (RollbackException e) {
            entityManager.getTransaction().rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Gallery gallery) {
        entityManager.getTransaction().begin();
        entityManager.remove(gallery);
        if (gallery.getPaintings() != null) {
            for (Painting painting : gallery.getPaintings()) {
                painting.setGallery(null);
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
    public void addOrUpdate(Gallery gallery) {
        entityManager.getTransaction().begin();
        try {
            entityManager.merge(gallery);
        } catch (EntityNotFoundException e) {
            entityManager.persist(gallery);
        }
        try {
            entityManager.getTransaction().commit();
        } catch (RollbackException e) {
            entityManager.getTransaction().rollback();
            e.printStackTrace();
        }
    }
}

