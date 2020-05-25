import entity.Gallery;
import entity.Painter;
import entity.Painting;
import entity.Studio;
import org.apache.log4j.Logger;
import repository.GalleryRepository;
import repository.PainterRepository;
import repository.PaintingRepository;
import repository.StudioRepository;
import utility.DatabaseUtility;
import utility.JsonParser;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Application {
    private static final Logger Log = Logger.getLogger(Application.class.getName());

    static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("entityManager");
    static EntityManager entityManager = entityManagerFactory.createEntityManager();

    public static void main(String[] args) {

        GalleryRepository galleryRepository = new GalleryRepository(entityManager);
        PainterRepository painterRepository = new PainterRepository(entityManager);
        PaintingRepository paintingRepository = new PaintingRepository(entityManager);
        StudioRepository studioRepository = new StudioRepository(entityManager);

        JsonParser jsonParser = new JsonParser();
        DatabaseUtility.insertIntoDatabase();

        Log.info(galleryRepository.getAll());
        Log.info(painterRepository.getAll());
        Log.info(paintingRepository.getAll());
        Log.info(studioRepository.getAll());

        Log.info("___________________________________________________");
        Log.info("Парсинг объектов в формат json");
        jsonParser.fromObjectToJSON(".\\src\\main\\resources\\json\\GalleryData1.json", galleryRepository.getById(1L));
        jsonParser.fromObjectToJSON(".\\src\\main\\resources\\json\\PainterData1.json", painterRepository.getById(1L));
        jsonParser.fromObjectToJSON(".\\src\\main\\resources\\json\\PaintingData1.json", paintingRepository.getById(1L));
        jsonParser.fromObjectToJSON(".\\src\\main\\resources\\json\\StudioData1.json", studioRepository.getById(1L));

        galleryRepository.addOrUpdate(jsonParser.fromJSONToObject(".\\src\\main\\resources\\json\\GalleryData1.json", Gallery.class));
        painterRepository.addOrUpdate(jsonParser.fromJSONToObject(".\\src\\main\\resources\\json\\PainterData1.json", Painter.class));
        paintingRepository.addOrUpdate(jsonParser.fromJSONToObject(".\\src\\main\\resources\\json\\PaintingData1.json", Painting.class));
        studioRepository.addOrUpdate(jsonParser.fromJSONToObject(".\\src\\main\\resources\\json\\StudioData1.json", Studio.class));

        Log.info(galleryRepository.getAll());
        Log.info(painterRepository.getAll());
        Log.info(paintingRepository.getAll());
        Log.info(studioRepository.getAll());
    }
}