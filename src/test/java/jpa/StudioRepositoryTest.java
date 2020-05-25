package jpa;

import entity.Painting;
import entity.Studio;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import repository.StudioRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class StudioRepositoryTest {

    private final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("entityManager");
    private final EntityManager entityManager = entityManagerFactory.createEntityManager();
    private final StudioRepository studioRepository = new StudioRepository(entityManager);

    @Test
    public void addStudio_andGetById() {
        Studio studio = new Studio(1L, "Тестовая");
        studioRepository.add(studio);
        assertEquals(studio, studioRepository.getById(1L));
    }

    @Test
    public void getAllStudios() {
        Studio studio = new Studio(1L, "Тестовая");
        Studio studio2 = new Studio(2L, "Тестовая2");
        studioRepository.add(studio);
        studioRepository.add(studio2);
        assertEquals(2, studioRepository.getAll().size());
    }

    @Test
    public void deleteStudio() {
        Studio studio = new Studio(1L, "Тестовая");
        Studio studio2 = new Studio(2L, "Тестовая2");
        studioRepository.add(studio);
        studioRepository.add(studio2);
        studioRepository.delete(studio);
        assertEquals(1, studioRepository.getAll().size());
        studioRepository.delete(studio2);
        assertEquals(0, studioRepository.getAll().size());
    }

    @Test
    public void updateStudio(){
        Studio studio = new Studio(1L, "Тестовая");
        studioRepository.add(studio);
        studio.setStudioName("Тестовая2");
        studioRepository.update(studio);
        assertEquals(1, studioRepository.getAll().size());
        assertEquals(studio, studioRepository.getById(1L));
    }

    @AfterEach
    public void clean() {
        List<Studio> studios = studioRepository.getAll();
        if (studios.size() > 0) {
            for (Studio studio : studios) {
                studioRepository.delete(studio);
            }
        }
    }
}
