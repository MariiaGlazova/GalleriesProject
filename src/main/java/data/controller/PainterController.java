package data.controller;

import data.entity.Painter;
import data.repository.PainterRepository;
import javassist.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path = "/painters")
public class PainterController {
    private PainterRepository painterRepository;

    public PainterController(PainterRepository painterRepository) {
        this.painterRepository = painterRepository;
    }

    @GetMapping
    public ResponseEntity<Iterable<Painter>> getPaintings() {
        return new ResponseEntity<>(painterRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Painter> getById(@PathVariable(name = "id") Long id) throws NotFoundException {
        Optional<Painter> repositoryResult = painterRepository.findById(id);
        if (repositoryResult.isPresent()) {
            return new ResponseEntity<>(repositoryResult.get(), HttpStatus.OK);
        } else {
            throw new NotFoundException("a painter with id = " + id + " not exist");
        }
    }

    @PostMapping
    public void Painter(@RequestBody Painter painter) {
        painterRepository.save(painter);
    }

    @PutMapping(path = "/{id}")
    @Transactional
    public void updatePainter(
            @PathVariable(name = "id") Long id,
            @RequestBody Painter painter
    ) throws NotFoundException {
        if (painterRepository.existsById(id)) {
            Painter editedPainter = painterRepository.findById(painter.getId()).get();
            editedPainter.setName(painter.getName());
            editedPainter.setStudio(painter.getStudio());
            editedPainter.setPaintings(painter.getPaintings());
            editedPainter.setGallery(painter.getGallery());
            painterRepository.save(editedPainter);
        } else {
            throw new NotFoundException("a painter with id = " + id + " not exist");
        }
    }

    @GetMapping(path = "/{name}")
    public ResponseEntity<Painter> getStudioByName(@PathVariable(value = "name") String name) {
        return new ResponseEntity<>(painterRepository.findByName(name), HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public void deletePainter(@PathVariable(name = "id") Long id) throws NotFoundException {
        if (painterRepository.existsById(id)) {
            painterRepository.deleteById(id);
        } else {
            throw new NotFoundException("a painter with id = " + id + " not exist");
        }
    }
}
