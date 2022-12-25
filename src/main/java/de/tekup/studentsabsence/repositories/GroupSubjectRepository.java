package de.tekup.studentsabsence.repositories;

import de.tekup.studentsabsence.entities.Group;
import de.tekup.studentsabsence.entities.GroupSubject;
import de.tekup.studentsabsence.entities.GroupSubjectKey;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface GroupSubjectRepository extends CrudRepository<GroupSubject, GroupSubjectKey> {
    List<GroupSubject> findAllByGroup(Group group);

    List<GroupSubject> findAllBySubjectId(Long id);
    GroupSubject findGroupSubjectByGroupIdAndSubjectId(Long group_id,Long subject_id);
    ///TODO create a methode to find a groupSubject by Group Id and Subject Id
}
