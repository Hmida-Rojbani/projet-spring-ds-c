package de.tekup.studentsabsence.repositories;

import de.tekup.studentsabsence.entities.GroupSubject;
import de.tekup.studentsabsence.entities.Student;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface StudentRepository extends CrudRepository<Student, Long> {
  public List<Student> getStudentsByGroupId(long gid);

}
