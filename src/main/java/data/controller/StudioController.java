package data.controller;

import data.entity.Studio;
import data.repository.StudioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/studios")
public class StudioController {
    private StudioRepository studioRepository;

    public StudioController(StudioRepository studioRepository) {
        this.studioRepository = studioRepository;
    }
    @GetMapping
    public ResponseEntity<Iterable<Studio>> getStudios() {
        return new ResponseEntity<>(studioRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping(path = "/{name}")
    public ResponseEntity<Studio> getStudioByName(@PathVariable(value = "name") String name) {
        return new ResponseEntity<>(studioRepository.findByName(name), HttpStatus.OK);
    }
}
