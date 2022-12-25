package de.tekup.studentsabsence.services.impl;

import de.tekup.studentsabsence.entities.Student;
import de.tekup.studentsabsence.entities.Subject;
import de.tekup.studentsabsence.services.StudentService;
import de.tekup.studentsabsence.services.SubjectService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
@Service
@AllArgsConstructor
public class MailService {
    JavaMailSender javaMailSender;
    StudentService studentService;
    SubjectService subjectService;



    public void sendMail(Long idStu,Long idsub) throws MailException, MessagingException {
        String mailText = "you are eliminated";
        String mailSubject = "Elimination";
        Student student = studentService.getStudentBySid(idStu);
        Subject subject = subjectService.getSubjectById(idsub);
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(student.getEmail());
        simpleMailMessage.setSubject(mailSubject);
        simpleMailMessage.setText(mailText+' '+ "in"+' '+subject.getName());
        javaMailSender.send(simpleMailMessage);
    }
}
