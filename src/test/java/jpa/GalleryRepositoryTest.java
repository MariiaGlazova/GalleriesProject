package jpa;

import entity.Gallery;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import repository.GalleryRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class GalleryRepositoryTest {


    private final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("entityManager");
    private final EntityManager entityManager = entityManagerFactory.createEntityManager();
    private final GalleryRepository galleryRepository = new GalleryRepository(entityManager);


    @Test
    public void addGallery_andGetById() {
        Gallery gallery = new Gallery(1L, "Тестовая");
        galleryRepository.add(gallery);
        assertEquals(gallery, galleryRepository.getById(1L));
    }

    @Test
    public void getAllGalleries() {
        Gallery gallery = new Gallery(1L, "Тестовая");
        Gallery gallery2 = new Gallery(2L, "Тестовая2");
        galleryRepository.add(gallery);
        galleryRepository.add(gallery2);
        assertEquals(2, galleryRepository.getAll().size());
    }

    @Test
    public void deleteGallery() {
        Gallery gallery = new Gallery(1L, "Тестовая");
        Gallery gallery2 = new Gallery(2L, "Тестовая2");
        galleryRepository.add(gallery);
        galleryRepository.add(gallery2);
        galleryRepository.delete(gallery);
        assertEquals(1, galleryRepository.getAll().size());
        galleryRepository.delete(gallery2);
        assertEquals(0, galleryRepository.getAll().size());
    }

    @Test
    public void updateGallery(){
        Gallery gallery = new Gallery(1L, "Тестовая");
        galleryRepository.add(gallery);
        gallery.setGalleryCity("Город-тест");
        galleryRepository.update(gallery);
        assertEquals(1, galleryRepository.getAll().size());
        assertEquals(gallery, galleryRepository.getById(1L));
    }

    @AfterEach
    public void clean() {
        List<Gallery> galleries = galleryRepository.getAll();
        if (galleries.size() > 0) {
            for (Gallery gallery : galleries) {
                galleryRepository.delete(gallery);
            }
        }
    }
}
