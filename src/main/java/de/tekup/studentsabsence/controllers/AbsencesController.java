package de.tekup.studentsabsence.controllers;

import de.tekup.studentsabsence.entities.Absence;
import de.tekup.studentsabsence.services.AbsenceService;
import de.tekup.studentsabsence.services.GroupService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;

@Controller
@RequestMapping("/absences")
@AllArgsConstructor
public class AbsencesController {
    private final AbsenceService absenceService;
    private Absence max;
    private Absence min;
    @GetMapping("/{gid}/{id}/show")
    public String showmaxabsence(@PathVariable Long gid, @PathVariable Long id, Model model)
    {
        ArrayList <Absence> absences = new ArrayList<>(absenceService.getAllAbsencesByGroupIdAndSubjectId(gid,id));
        for (int i=0;i<=absences.size();i++) {
            if (absences.get(i).getHours()<absences.get(i+1).getHours()){
                max = absences.get(i+1);
            }
        }
        for (int i=0;i<=absences.size();i++) {
            if (absences.get(i).getHours()>absences.get(i+1).getHours()){
                min = absences.get(i+1);
            }
        }
        model.addAttribute("maxabsence",max);
        model.addAttribute("minabsence",min);
        return "subjects/show";
    }
}
