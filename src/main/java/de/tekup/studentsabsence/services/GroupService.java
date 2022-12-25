package de.tekup.studentsabsence.services;

import de.tekup.studentsabsence.entities.Group;
import de.tekup.studentsabsence.entities.Student;
import de.tekup.studentsabsence.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public interface GroupService {
    List<Group> getAllGroups();



    Group getGroupById(Long id);

    Group addGroup(Group group);

    Group updateGroup(Group group);

    Group deleteGroup(Long id);

    List<Student> getStudentsForGroup(long id);
}
