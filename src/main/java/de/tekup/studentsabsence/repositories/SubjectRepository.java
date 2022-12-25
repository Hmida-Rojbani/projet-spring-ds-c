package de.tekup.studentsabsence.repositories;

import de.tekup.studentsabsence.entities.GroupSubject;
import de.tekup.studentsabsence.entities.Student;
import de.tekup.studentsabsence.entities.Subject;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SubjectRepository extends CrudRepository<Subject, Long> {



    Subject getSubjectsById(Long sid );
}
