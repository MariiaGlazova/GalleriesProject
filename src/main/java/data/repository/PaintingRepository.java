package data.repository;

import data.entity.Painting;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaintingRepository extends CrudRepository<Painting, Long> {
    Painting findByName(String name);
}
