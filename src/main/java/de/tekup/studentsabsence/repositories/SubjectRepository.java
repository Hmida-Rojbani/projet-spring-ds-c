package de.tekup.studentsabsence.repositories;

import de.tekup.studentsabsence.entities.Subject;
import org.springframework.data.repository.CrudRepository;

public interface SubjectRepository extends CrudRepository<Subject, Long> {
	
	// @Query("SELECT MIN(hours),Max(hours) from absences groupBy subjectId")
	 //public float getMinAndMaxAbsences(@Param("heure")heure )

}
