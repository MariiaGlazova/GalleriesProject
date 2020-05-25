package jpa;

import entity.Painter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import repository.PainterRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class PainterRepositoryTest {

    private final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("entityManager");
    private final EntityManager entityManager = entityManagerFactory.createEntityManager();
    private final PainterRepository painterRepository = new PainterRepository(entityManager);

    @Test
    public void addPainter_andGetById() {
        Painter painter = new Painter(1L, "Тестовый");
        painterRepository.add(painter);
        assertEquals(painter, painterRepository.getById(1L));
    }

    @Test
    public void getAllPainters() {
        Painter painter = new Painter(1L, "Тестовый");
        Painter painter2 = new Painter(2L, "Тестовый2");
        painterRepository.add(painter);
        painterRepository.add(painter2);
        assertEquals(2, painterRepository.getAll().size());
    }

    @Test
    public void deletePainter() {
        Painter painter = new Painter(1L, "Тестовый");
        Painter painter2 = new Painter(2L, "Тестовый2");
        painterRepository.add(painter);
        painterRepository.add(painter2);
        painterRepository.delete(painter);
        assertEquals(1, painterRepository.getAll().size());
        painterRepository.delete(painter2);
        assertEquals(0, painterRepository.getAll().size());
    }

    @Test
    public void updatePainter(){
        Painter painter = new Painter(1L, "Тестовая");
        painterRepository.add(painter);
        painter.setPainterName("Тестовая2");
        painterRepository.update(painter);
        assertEquals(1, painterRepository.getAll().size());
        assertEquals(painter, painterRepository.getById(1L));
    }

    @AfterEach
    public void clean() {
        List<Painter> painters = painterRepository.getAll();
        if (painters.size() > 0) {
            for (Painter painter : painters) {
                painterRepository.delete(painter);
            }
        }
    }
}