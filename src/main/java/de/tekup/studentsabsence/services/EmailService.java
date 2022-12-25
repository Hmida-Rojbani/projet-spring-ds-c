package de.tekup.studentsabsence.services;

import de.tekup.studentsabsence.entities.EmailDetails;


public interface EmailService {


    String sendSimpleMail(EmailDetails details);

    String sendMailWithAttachment(EmailDetails details);
}