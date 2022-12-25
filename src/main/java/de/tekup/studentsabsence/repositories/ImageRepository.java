package de.tekup.studentsabsence.repositories;

import de.tekup.studentsabsence.entities.Image;
import org.apache.logging.log4j.message.StringFormattedMessage;
import org.springframework.data.repository.CrudRepository;

public interface ImageRepository extends CrudRepository<Image, String> {
    Image findImageById (String id );
}
