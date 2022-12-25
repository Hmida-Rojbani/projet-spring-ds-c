package de.tekup.studentsabsence.services;

import de.tekup.studentsabsence.entities.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;

@Service
public class MailService {
    @Autowired
    private JavaMailSender mailSender;

    public void sendVerificationEmail(Student user, String siteURL)
            throws MessagingException, UnsupportedEncodingException {
        String toAddress = user.getEmail();
        String fromAddress = "ahmedformed@gmail.com";
        String senderName = "TEK-UP";
        String subject = "Vous etes eliminé !";
        String content = "L'etudiant [[name]],<br>"
                + "Vous etes eliminé veuillez consulter le site de tek-up. <br>"
                + "<h3><a href=\"https://www.tek-up.de\" target=\"_self\">Consulter</a></h3>"
                + "Thank you,<br>"
                + "TEK-UP Group.";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(fromAddress, senderName);
        helper.setTo(toAddress);
        helper.setSubject(subject);

        content = content.replace("[[name]]", user.getFirstName());


        helper.setText(content, true);

        mailSender.send(message);
    }
}
