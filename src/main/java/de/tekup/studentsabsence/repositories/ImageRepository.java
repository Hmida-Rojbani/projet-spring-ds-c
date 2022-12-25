package de.tekup.studentsabsence.repositories;

import de.tekup.studentsabsence.entities.Image;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends CrudRepository<Image, String> {

}
