package de.tekup.studentsabsence.services;

import de.tekup.studentsabsence.entities.Group;
import de.tekup.studentsabsence.entities.Student;

import java.util.List;

public interface GroupService {
    List<Group> getAllGroups();
    List<Student> getStudentsForGroup(long id);

    Group getGroupById(Long id);

    Group addGroup(Group group);

    Group updateGroup(Group group);

    Group deleteGroup(Long id);
}
