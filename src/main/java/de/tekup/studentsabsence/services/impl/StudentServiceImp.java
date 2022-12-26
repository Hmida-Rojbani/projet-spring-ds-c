package de.tekup.studentsabsence.services.impl;

import de.tekup.studentsabsence.entities.Student;
import de.tekup.studentsabsence.repositories.StudentRepository;
import de.tekup.studentsabsence.services.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class StudentServiceImp implements StudentService {
    private final StudentRepository studentRepository;

    @Override
    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        studentRepository.findAll().forEach(students::add);
        return students;
    }

    @Override
    public Student getStudentBySid(Long sid) {
        return studentRepository.findById(sid).
                orElseThrow(() -> new NoSuchElementException("No Student With SID: " + sid));
    }

    @Override
    public Student addStudent(Student student) {
        return studentRepository.save(student);

    }

    //TODO Complete this method
    @Override
    public Student updateStudent(Student student) {
        Student updatedStudent = studentRepository.findById(student.getSid()).get();
        updatedStudent.setSid(student.getSid());
        updatedStudent.setImage(student.getImage());
        updatedStudent.setDob(student.getDob());
        updatedStudent.setEmail(student.getEmail());
        updatedStudent.setPhone(student.getPhone());
        updatedStudent.setFirstName(student.getFirstName());
        updatedStudent.setLastName(student.getLastName());
        return studentRepository.save(updatedStudent);
    }

    //TODO Complete this method
    @Override
    public Student deleteStudent(Long sid) {
        return null;
    }
}
