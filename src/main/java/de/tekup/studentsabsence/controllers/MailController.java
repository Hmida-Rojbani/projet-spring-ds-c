package de.tekup.studentsabsence.controllers;

import de.tekup.studentsabsence.services.impl.MailService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;

@Controller
@AllArgsConstructor
@RequestMapping("/email")
public class MailController {

 private final MailService mailService;

    @GetMapping("/send/{idStudent}/{idSubject}")
    public String sendmail(@PathVariable Long idStudent,@PathVariable Long idSubject) throws MessagingException {

                mailService.sendMail(idStudent,idSubject);


        return "redirect:/students";
        }



}
