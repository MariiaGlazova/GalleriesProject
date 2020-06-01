package data.controller;

import data.entity.Painting;
import data.repository.PaintingRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/paintings")
public class PaintingController {
    private PaintingRepository paintingRepository;

    public PaintingController(PaintingRepository paintingRepository) {
        this.paintingRepository = paintingRepository;
    }
    @GetMapping
    public ResponseEntity<Iterable<Painting>> getPaintings() {
        return new ResponseEntity<>(paintingRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping(path = "/{name}")
    public ResponseEntity<Painting> getStudioByName(@PathVariable(value = "name") String name) {
        return new ResponseEntity<>(paintingRepository.findByName(name), HttpStatus.OK);
    }
}
