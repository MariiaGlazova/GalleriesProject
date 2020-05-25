package utility;

import entity.Gallery;
import entity.Painter;
import entity.Painting;
import entity.Studio;
import repository.GalleryRepository;
import repository.PainterRepository;
import repository.PaintingRepository;
import repository.StudioRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Arrays;
import java.util.Collections;

public class DatabaseUtility {

    private DatabaseUtility() {
    }

    private final static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("entityManager");

    public static void insertIntoDatabase() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        GalleryRepository galleryRepository = new GalleryRepository(entityManager);
        PaintingRepository paintingRepository = new PaintingRepository(entityManager);

        Studio shepardStudio = new Studio(1L, "Каюта капитана");
        Studio garrusStudio = new Studio(2L, "Жилая палуба");
        Studio geraltStudio = new Studio(3L, "Аэдд Гинваэль");
        Studio jaskierStudio = new Studio(4L, "вотчины де Леттенхофа");
        Studio joeStudio = new Studio(5L, "Полицейский Участок");

        Painter masEffectPainter1 = new Painter(1L, "Джейн Шепард");
        masEffectPainter1.setStudio(shepardStudio);

        Painter masEffectPainter2 = new Painter(2L, "Гаррус Вакариан");
        masEffectPainter2.setStudio(garrusStudio);

        Painter theWitcherPainter1 = new Painter(3L, "Геральт из Ривии");
        theWitcherPainter1.setStudio(geraltStudio);

        Painter theWitcherPainter2 = new Painter(4L, "Лютик (Юлиан Альфред Панкрац виконт де Леттенхоф)");
        theWitcherPainter2.setStudio(jaskierStudio);

        Painter arkhamPainter1 = new Painter(5L, "Джо Даймонд");
        arkhamPainter1.setStudio(joeStudio);

        Gallery massEffectGallery1 = new Gallery(1L, "База Коллекционеров");
        massEffectGallery1.setGalleryCity("Омега-4");
        massEffectGallery1.setPainters(Arrays.asList(masEffectPainter1, masEffectPainter2));

        Gallery massEffectGallery2 = new Gallery(2L, "Президиум");
        massEffectGallery2.setGalleryCity("Цитадель");
        massEffectGallery2.setPainters(Arrays.asList(masEffectPainter1, masEffectPainter2));

        Gallery theWitcherGallery1 = new Gallery(3L, "У Вирсинга");
        theWitcherGallery1.setGalleryCity("Ривия");
        theWitcherGallery1.setPainters(Arrays.asList(theWitcherPainter1, theWitcherPainter2));

        Gallery theWitcherGallery2 = new Gallery(4L, "Хижина Травницы");
        theWitcherGallery2.setGalleryCity("Белый Сад");
        theWitcherGallery2.setPainters(Arrays.asList(theWitcherPainter1, theWitcherPainter2));

        Gallery arkhamGallery1 = new Gallery(5L, "У Велмы");
        arkhamGallery1.setGalleryCity("Аркхэм");
        arkhamGallery1.setPainters(Collections.singletonList(arkhamPainter1));

        Painting massEffectPainting1 = new Painting(1L, "Самоубийственная Миссия");
        massEffectPainting1.setGallery(massEffectGallery1);
        massEffectPainting1.setPainters(Collections.singletonList(masEffectPainter1));

        Painting massEffectPainting2 = new Painting(2L, "Смерть Сарена");
        massEffectPainting2.setGallery(massEffectGallery2);
        massEffectPainting2.setPainters(Arrays.asList(masEffectPainter1, masEffectPainter2));

        Painting theWitcherPainting1 = new Painting(3L, "Битва за Ривию");
        theWitcherPainting1.setGallery(theWitcherGallery1);
        theWitcherPainting1.setPainters(Collections.singletonList(theWitcherPainter1));

        Painting theWitcherPainting2 = new Painting(4L, "Ведьмаку заплатите чеканной монетой");
        theWitcherPainting2.setGallery(theWitcherGallery2);
        theWitcherPainting2.setPainters(Collections.singletonList(theWitcherPainter2));

        Painting arkhamPainting1 = new Painting(5L, "Древний Ужас");
        arkhamPainting1.setGallery(arkhamGallery1);
        arkhamPainting1.setPainters(Collections.singletonList(arkhamPainter1));

        galleryRepository.add(massEffectGallery1);
        galleryRepository.add(massEffectGallery2);
        galleryRepository.add(theWitcherGallery1);
        galleryRepository.add(theWitcherGallery2);
        galleryRepository.add(arkhamGallery1);
        paintingRepository.add(massEffectPainting1);
        paintingRepository.add(massEffectPainting2);
        paintingRepository.add(theWitcherPainting1);
        paintingRepository.add(theWitcherPainting2);
        paintingRepository.add(arkhamPainting1);
    }
}
