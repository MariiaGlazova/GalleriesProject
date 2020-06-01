package data.repository;

import data.entity.Painter;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PainterRepository extends CrudRepository<Painter, Long> {
    Painter findByName(String name);
}
