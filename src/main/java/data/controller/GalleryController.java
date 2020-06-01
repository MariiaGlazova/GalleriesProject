package data.controller;

import data.entity.Gallery;
import data.repository.GalleryRepository;
import javassist.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping(path = "/galleries")
public class GalleryController {
    private GalleryRepository galleryRepository;

    public GalleryController(GalleryRepository galleryRepository) {
        this.galleryRepository = galleryRepository;
    }

    @GetMapping
    public ResponseEntity<Iterable<Gallery>> getGalleries() {
        return new ResponseEntity<>(galleryRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping(path = "/{name}")
    public ResponseEntity<Gallery> getStudioByName(@PathVariable(value = "name") String name) {
        return new ResponseEntity<>(galleryRepository.findByName(name), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Gallery> getById(@PathVariable(name = "id") Long id) throws NotFoundException {
        Optional<Gallery> repositoryResult = galleryRepository.findById(id);
        if (repositoryResult.isPresent()) {
            return new ResponseEntity<>(repositoryResult.get(), HttpStatus.OK);
        } else {
            throw new NotFoundException("a gallery with id = " + id + " not exist");
        }
    }

}
