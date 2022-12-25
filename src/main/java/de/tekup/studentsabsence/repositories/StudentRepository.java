package de.tekup.studentsabsence.repositories;

import de.tekup.studentsabsence.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
