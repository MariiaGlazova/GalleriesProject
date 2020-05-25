package jpa;

import entity.Painting;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import repository.PaintingRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class PaintingRepositoryTest {

    private final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("entityManager");
    private final EntityManager entityManager = entityManagerFactory.createEntityManager();
    private final PaintingRepository paintingRepository = new PaintingRepository(entityManager);

    @Test
    public void addPainting_andGetById() {
        Painting painting = new Painting(1L, "Тестовая");
        paintingRepository.add(painting);
        assertEquals(painting, paintingRepository.getById(1L));
    }

    @Test
    public void getAllPaintings() {
        Painting painting = new Painting(1L, "Тестовая");
        Painting painting2 = new Painting(2L, "Тестовая2");
        paintingRepository.add(painting);
        paintingRepository.add(painting2);
        assertEquals(2, paintingRepository.getAll().size());
    }

    @Test
    public void deletePainting() {
        Painting painting = new Painting(1L, "Тестовая");
        Painting painting2 = new Painting(2L, "Тестовая2");
        paintingRepository.add(painting);
        paintingRepository.add(painting2);
        paintingRepository.delete(painting);
        assertEquals(1, paintingRepository.getAll().size());
        paintingRepository.delete(painting2);
        assertEquals(0, paintingRepository.getAll().size());
    }

    @Test
    public void updatePainting(){
        Painting painting = new Painting(1L, "Тестовая");
        paintingRepository.add(painting);
        painting.setPaintingName("Тестовая2");
        paintingRepository.update(painting);
        assertEquals(1, paintingRepository.getAll().size());
        assertEquals(painting, paintingRepository.getById(1L));
    }

    @AfterEach
    public void clean() {
        List<Painting> paintings = paintingRepository.getAll();
        if (paintings.size() > 0) {
            for (Painting painting : paintings) {
                paintingRepository.delete(painting);
            }
        }
    }
}
