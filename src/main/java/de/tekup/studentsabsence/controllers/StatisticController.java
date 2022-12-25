package de.tekup.studentsabsence.controllers;

import de.tekup.studentsabsence.entities.Group;
import de.tekup.studentsabsence.services.GroupService;
import de.tekup.studentsabsence.services.SubjectService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@AllArgsConstructor
public class StatisticController {
    private final GroupService groupService;
    private final  SubjectService subjectService;
    @GetMapping({"", "/statistics"})
    public String index(Model model) {
        List<Group> groups = groupService.getAllGroups() ;
        model.addAttribute("subjectService", subjectService) ;
        model.addAttribute("groups", groups) ;
        return "/statistics/index";
    }
}
