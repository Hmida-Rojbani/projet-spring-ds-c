package de.tekup.studentsabsence.controllers;

import de.tekup.studentsabsence.entities.Group;
import de.tekup.studentsabsence.services.GroupService;
import de.tekup.studentsabsence.services.SubjectService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@AllArgsConstructor
public class FrontController {


    @GetMapping({"", "/"})
    public String index() {


        return "index";
    }
}
