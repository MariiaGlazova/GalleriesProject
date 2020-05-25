package json;

import entity.Gallery;
import entity.Painter;
import entity.Painting;
import entity.Studio;
import org.junit.jupiter.api.Test;
import repository.GalleryRepository;
import repository.PainterRepository;
import repository.PaintingRepository;
import repository.StudioRepository;
import utility.DatabaseUtility;
import utility.JsonParser;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import static org.junit.Assert.assertEquals;

public class JsonParsingTest {

    private final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("entityManager");
    private final EntityManager entityManager = entityManagerFactory.createEntityManager();
    GalleryRepository galleryRepository = new GalleryRepository(entityManager);
    PainterRepository painterRepository = new PainterRepository(entityManager);
    PaintingRepository paintingRepository = new PaintingRepository(entityManager);
    StudioRepository studioRepository = new StudioRepository(entityManager);
    JsonParser jsonParser = new JsonParser();

    @Test
    public void parsingFromJsonToObjectTest(){
        DatabaseUtility.insertIntoDatabase();
        assertEquals(galleryRepository.getById(1L).toString(),
                jsonParser.fromJSONToObject(".\\src\\main\\resources\\json\\GalleryData1.json", Gallery.class).toString());
        assertEquals(painterRepository.getById(1L).toString(),
                jsonParser.fromJSONToObject(".\\src\\main\\resources\\json\\PainterData1.json", Painter.class).toString());
        assertEquals(paintingRepository.getById(1L).toString(),
                jsonParser.fromJSONToObject(".\\src\\main\\resources\\json\\PaintingData1.json", Painting.class).toString());
        assertEquals(studioRepository.getById(1L).toString(),
                jsonParser.fromJSONToObject(".\\src\\main\\resources\\json\\StudioData1.json", Studio.class).toString());
    }
}
