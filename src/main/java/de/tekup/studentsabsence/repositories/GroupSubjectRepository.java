package de.tekup.studentsabsence.repositories;

import de.tekup.studentsabsence.entities.Group;
import de.tekup.studentsabsence.entities.GroupSubject;
import de.tekup.studentsabsence.entities.GroupSubjectKey;
import de.tekup.studentsabsence.entities.Subject;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface GroupSubjectRepository extends CrudRepository<GroupSubject, GroupSubjectKey> {
    List<GroupSubject> findAllByGroup(Group id);
    ///TODO create a methode to find a groupSubject by Group Id and Subject Id
    GroupSubject findGroupSubjectByGroup_Id(Long group_id);

    GroupSubject findGroupSubjectBySubject_Id(Long subject_id);

   // GroupSubject findGroupSubjectByGroup_IdAAndSubject_Id(Long gid , Long id);
    GroupSubject findByGroup_idAndSubject_Id(Long gid , Long id);

    List<GroupSubject> findAllBySubject(Subject subject);
}
