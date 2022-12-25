package de.tekup.studentsabsence.services.impl;

import de.tekup.studentsabsence.entities.Absence;
import de.tekup.studentsabsence.entities.Group;
import de.tekup.studentsabsence.entities.GroupSubject;
import de.tekup.studentsabsence.entities.Subject;
import de.tekup.studentsabsence.repositories.GroupSubjectRepository;
import de.tekup.studentsabsence.repositories.StudentRepository;
import de.tekup.studentsabsence.repositories.SubjectRepository;
import de.tekup.studentsabsence.services.AbsenceService;
import de.tekup.studentsabsence.services.SubjectService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class SubjectServiceImp implements SubjectService {
    private final SubjectRepository subjectRepository;
    private final AbsenceService absenceService ;

    private final GroupSubjectRepository groupSubjectRepository ;

    //TODO Complete this method
    @Override
    public List<Subject> getAllSubjects() {
        List<Subject> subjects = new ArrayList<>();
        subjectRepository.findAll().forEach(subjects::add);
        return subjects;
    }

    @Override
    public Subject getSubjectById(Long id) {
        return subjectRepository.findById(id).
                orElseThrow(() -> new NoSuchElementException("No Subject with ID: " + id));

    }
    @Override
    public Subject addSubject(Subject subject) {
        return subjectRepository.save(subject);
    }

    @Override
    public Subject updateSubject(Subject subject) {
        if (!subjectRepository.existsById(subject.getId())) {
            throw new NoSuchElementException("No Subject with ID : " + subject.getId());
        }
        return subjectRepository.save(subject);
    }

    @Override
    public Subject deleteSubject(Long id) {
        Subject subject = getSubjectById(id);
        subjectRepository.delete(subject);
        return subject;
    }

    @Override
    public String getMostAbsence(Long gid) {
        try {
            float max = 0 ;
            Long  idSubject = null;

            List<GroupSubject> groupSubjects = groupSubjectRepository.findGroupSubjectByGroupId(gid);

            for (GroupSubject GS : groupSubjects){
                List<Absence> absences = absenceService.getAllAbsencesByGroupIdAndSubjectId(gid , GS.getSubject().getId() );
                if (absenceService.countHours( absences)>max )
                {
                    max = absenceService.countHours( absences);
                    idSubject= GS.getSubject().getId(); ;
                };

            }

            return subjectRepository.getSubjectsById(idSubject).getName();

        }catch (Exception ex){
            return "NO THINGS";
        }

    }
    @Override
    public String getLessAbsence(Long gid) {
        try{
        float min =99999999 ;
        Long idSubject = null;

        List<GroupSubject> groupSubjects = groupSubjectRepository.findGroupSubjectByGroupId(gid);

        for (GroupSubject GS : groupSubjects){
            List<Absence> absences = absenceService.getAllAbsencesByGroupIdAndSubjectId(gid , GS.getSubject().getId() );

            if (absenceService.countHours( absences)<min )
            {
                min = absenceService.countHours( absences);
                idSubject= GS.getSubject().getId(); ;
            };

        }
        return subjectRepository.getSubjectsById(idSubject).getName();

        }catch (Exception ex){
            return "NO THINGS";
        }

    }

 //   @Override
    //public String getStudentsEliminate(Long gid) {
           // return null;
          //  }
        }
