package data.config;

import data.entity.Gallery;
import data.entity.Painter;
import data.entity.Painting;
import data.entity.Studio;
import data.repository.GalleryRepository;
import data.repository.PainterRepository;
import data.repository.PaintingRepository;
import data.repository.StudioRepository;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Configuration
public class InitConfig {
    private GalleryRepository galleryRepository;
    private PainterRepository painterRepository;
    private PaintingRepository paintingRepository;
    private StudioRepository studioRepository;

    public InitConfig(
            GalleryRepository galleryRepository, PainterRepository painterRepository,
            PaintingRepository paintingRepository, StudioRepository studioRepository) {
        this.galleryRepository = galleryRepository;
        this.painterRepository = painterRepository;
        this.paintingRepository = paintingRepository;
        this.studioRepository = studioRepository;
    }

    @PostConstruct
    @Transactional
    public void initIt() {
        String locationName1 = "Каюта капитана";
        String locationName2 = "Жилая палуба";
        String locationName3 = "Аэдд Гинваэль";
        String locationName4 = "Вотчины де Леттенхофа";
        String locationName5 = "Инженерный отсек";
        String locationName6 = "Магазин подержанных кораблей";
        String locationName7 = "Боклерский дворец";

        studioRepository.save(new Studio(locationName1));
        studioRepository.save(new Studio(locationName2));
        studioRepository.save(new Studio(locationName3));
        studioRepository.save(new Studio(locationName4));
        studioRepository.save(new Studio(locationName5));
        studioRepository.save(new Studio(locationName6));
        studioRepository.save(new Studio(locationName7));

        Studio studio1 = studioRepository.findByName(locationName1);
        Studio studio2 = studioRepository.findByName(locationName2);
        Studio studio3 = studioRepository.findByName(locationName3);
        Studio studio4 = studioRepository.findByName(locationName4);
        Studio studio5 = studioRepository.findByName(locationName5);
        Studio studio6 = studioRepository.findByName(locationName6);
        Studio studio7 = studioRepository.findByName(locationName7);


        String paintingName1 = "Самоубийственная Миссия";
        String paintingName2 = "Смерть Сарена";
        String paintingName3 = "Аркхангел";
        String paintingName4 = "Битва за Ривию";
        String paintingName5 = "Геральт-геральт...";
        String paintingName6 = "Ведьмаку заплатите чеканной монетой";
        String paintingName7 = "Еще какая-нибудь картинка";
        String paintingName8 = "Гвинт";

        paintingRepository.save(new Painting(paintingName1));
        paintingRepository.save(new Painting(paintingName2));
        paintingRepository.save(new Painting(paintingName3));
        paintingRepository.save(new Painting(paintingName4));
        paintingRepository.save(new Painting(paintingName5));
        paintingRepository.save(new Painting(paintingName6));
        paintingRepository.save(new Painting(paintingName7));
        paintingRepository.save(new Painting(paintingName8));

        Painting painting1 = paintingRepository.findByName(paintingName1);
        Painting painting2 = paintingRepository.findByName(paintingName2);
        Painting painting3 = paintingRepository.findByName(paintingName3);
        Painting painting4 = paintingRepository.findByName(paintingName4);
        Painting painting5 = paintingRepository.findByName(paintingName5);
        Painting painting6 = paintingRepository.findByName(paintingName6);
        Painting painting7 = paintingRepository.findByName(paintingName7);
        Painting painting8 = paintingRepository.findByName(paintingName8);

        List<Gallery> galleries = Arrays.asList(
                new Gallery("База Коллекционеров", "Омега-4"),
                new Gallery("Президиум", "Цитадель"),
                new Gallery("У Вирсинга", "Ривия"),
                new Gallery("Хижина Травницы", "Белый Сад")
        );
        galleryRepository.saveAll(galleries);

        Gallery gallery1 = galleryRepository.findByName("База Коллекционеров");
        Gallery gallery2 = galleryRepository.findByName("Президиум");
        Gallery gallery3 = galleryRepository.findByName("У Вирсинга");
        Gallery gallery4 = galleryRepository.findByName("Хижина Травницы");

        List<Painter> painters = Arrays.asList(
                new Painter("Джейн Шепард", studio1, Arrays.asList(painting1, painting2), gallery1),
                new Painter("Гаррус Вакариан", studio2, Collections.singletonList(painting3), gallery2),
                new Painter("Геральт из Ривии", studio3, Arrays.asList(painting4, painting5), gallery3),
                new Painter("Лютик (Юлиан Альфред Панкрац виконт де Леттенхоф)", studio4, Collections.singletonList(painting6), gallery4),
                new Painter("Присцилла", studio5, Collections.singletonList(painting7), gallery4)
        );
        painterRepository.saveAll(painters);


    }
}
