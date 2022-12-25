package de.tekup.studentsabsence.controllers;


import de.tekup.studentsabsence.entities.*;
import de.tekup.studentsabsence.enums.LevelEnum;
import de.tekup.studentsabsence.enums.SpecialityEnum;
import de.tekup.studentsabsence.holders.GroupSubjectHolder;
import de.tekup.studentsabsence.services.AbsenceService;
import de.tekup.studentsabsence.services.GroupService;
import de.tekup.studentsabsence.services.GroupSubjectService;
import de.tekup.studentsabsence.services.SubjectService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@Controller
@RequestMapping("/groups")
@AllArgsConstructor
public class GroupController {
    private final GroupService groupService;
    private final SubjectService subjectService;
    private final GroupSubjectService groupSubjectService;
    private final AbsenceService absenceService;

    @GetMapping({"", "/"})
    public String index(Model model) {
        List<Group> groups = groupService.getAllGroups();
        model.addAttribute("groups", groups);
        return "groups/index";
    }

    @GetMapping("/add")
    public String addView(Model model) {
        model.addAttribute("levels", LevelEnum.values());
        model.addAttribute("specialities", SpecialityEnum.values());
        model.addAttribute("group", new Group());
        return "groups/add";
    }

    @PostMapping("/add")
    public String add(@Valid Group group, BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()) {
            model.addAttribute("levels", LevelEnum.values());
            model.addAttribute("specialities", SpecialityEnum.values());
            return "groups/add";
        }

        groupService.addGroup(group);
        return "redirect:/groups";
    }

    @GetMapping("/{id}/update")
    public String updateView(@PathVariable long id,  Model model) {
        model.addAttribute("levels", LevelEnum.values());
        model.addAttribute("specialities", SpecialityEnum.values());
        model.addAttribute("group", groupService.getGroupById(id));
        return "groups/update";
    }

    @PostMapping("/{id}/update")
    public String update(@PathVariable long id, @Valid Group group, BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()) {
            model.addAttribute("levels", LevelEnum.values());
            model.addAttribute("specialities", SpecialityEnum.values());
            return "groups/update";
        }
        groupService.updateGroup(group);
        return "redirect:/groups";
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable long id) {
        groupService.deleteGroup(id);
        return "redirect:/groups";
    }

    @GetMapping("/{id}/show")
    public String show(@PathVariable long id, Model model) {
        Group group = groupService.getGroupById(id);

        List<GroupSubject> groupSubjectList = groupSubjectService.getSubjectsByGroupId(id);
        Map<Subject,Float> mapSubTauxDabscence = new HashMap<>();
        for (GroupSubject groupSubject :groupSubjectList){
            float res = absenceService.hoursCountByGroupAndSubject(groupSubject.getGroup().getId(), groupSubject.getSubject().getId());
            mapSubTauxDabscence.put(groupSubject.getSubject(), res);
        }
        List<Map.Entry<Subject, Float>> nlist;
        nlist = new ArrayList<>(mapSubTauxDabscence.entrySet());
        nlist.sort(Map.Entry.comparingByValue());
         Subject highstSubjectAbscence = nlist.get(nlist.size()-1).getKey();
         Subject lowestSubjectAbscence = nlist.get(0).getKey();

        model.addAttribute("group", group);
        model.addAttribute("groupSubjects",groupSubjectService.getSubjectsByGroupId(id));
        model.addAttribute("students",group.getStudents());
        model.addAttribute("absenceService", absenceService);

        model.addAttribute("highstSubjectAbscence",highstSubjectAbscence);
        model.addAttribute("lowestSubjectAbscence", lowestSubjectAbscence);

        group.getStudents().forEach(student -> {

        });

        return "groups/show";
    }

    @GetMapping("/{id}/add-subject")
    public String addSubjectView(Model model , @PathVariable Long id){
        model.addAttribute("groupSubjectHolder", new GroupSubjectHolder());
        model.addAttribute("group",groupService.getGroupById(id));
        model.addAttribute("subjects",subjectService.getAllSubjects());
        return "groups/add-subject";

    }

    @PostMapping("/{id}/add-subject")
    public String addSubject(@PathVariable Long id, @Valid GroupSubjectHolder groupSubjectHolder, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()) {
            model.addAttribute("group",groupService.getGroupById(id));
            model.addAttribute("subjects",subjectService.getAllSubjects());
            return "groups/add-subject";
        }

        Group group = groupService.getGroupById(id);
        groupSubjectService.addSubjectToGroup(group, groupSubjectHolder.getSubject(), groupSubjectHolder.getHours());
        return "redirect:/groups/"+id+"/add-subject";
    }

    @GetMapping("/{gid}/subject/{sid}/delete")
    public String deleteSubject(@PathVariable Long gid, @PathVariable Long sid){
        groupSubjectService.deleteSubjectFromGroup(gid, sid);
        return "redirect:/groups/"+gid+"/show";
    }

    @GetMapping("/{id}/add-absences")
    public String addAbsenceView(@PathVariable long id, Model model) {
        Group group = groupService.getGroupById(id);

        model.addAttribute("group", group);
        model.addAttribute("absence", new Absence());
        model.addAttribute("groupSubjects", groupSubjectService.getSubjectsByGroupId(id));
        model.addAttribute("students", group.getStudents());

        return "groups/add-absences";
    }

    @PostMapping("/{id}/add-absences")
    public String addAbsence(@PathVariable long id, @Valid Absence absence, BindingResult bindingResult, @RequestParam(value = "students", required = false) List<Student> students, Model model) {
        //TODO Complete the body of this method
        if(bindingResult.hasErrors()) {
            model.addAttribute("group",groupService.getGroupById(id));
            model.addAttribute("absence",absenceService.getAllAbsencesByGroupId(id));
            return "groups/add-absences";
        }
        Group group = groupService.getGroupById(id);

        for (Student student : students){
            List<Absence> absenceList = new ArrayList<>();

            Absence abs = new Absence();
                    abs.setStudent(student);
                    abs.setHours(absence.getHours());
                    abs.setSubject(absence.getSubject());
                    abs.setStartDate(absence.getStartDate());
                    absenceService.addAbsence(abs);
            absenceList = student.getAbsences();
            absenceList.add(abs);
            student.setAbsences(absenceList);
        }
        group.setStudents(students);
        return "redirect:/groups/"+id+"/add-absences";
    }
    @GetMapping("/{gid}/getTheHighestSubjectAbs")
    @ResponseBody
    public Subject getTheHighestSubjectAbs(@PathVariable Long gid ){
        List<GroupSubject> groupSubjectList = groupSubjectService.getSubjectsByGroupId(gid);
        Map<Subject,Float> mapSubTauxDabscence = new HashMap<>();
        for (GroupSubject groupSubject :groupSubjectList){
           float res = absenceService.hoursCountByGroupAndSubject(groupSubject.getGroup().getId(), groupSubject.getSubject().getId());
            mapSubTauxDabscence.put(groupSubject.getSubject(), res);
        }
        List<Map.Entry<Subject, Float>> nlist;
        nlist = new ArrayList<>(mapSubTauxDabscence.entrySet());
        nlist.sort(Map.Entry.comparingByValue());
        return nlist.get(nlist.size()-1).getKey();
    }

    @GetMapping("/{gid}/getTheLowestSubjectAbs")
    @ResponseBody
    public Subject getTheLowestSubjectAbs(@PathVariable Long gid){
        List<GroupSubject> groupSubjectList = groupSubjectService.getSubjectsByGroupId(gid);

        Map<Subject,Float> mapSubTauxDabscence = new HashMap<>();
        for (GroupSubject groupSubject :groupSubjectList){
            float res = absenceService.hoursCountByGroupAndSubject(groupSubject.getGroup().getId(), groupSubject.getSubject().getId());
            mapSubTauxDabscence.put(groupSubject.getSubject(), res);
        }
        List<Map.Entry<Subject, Float>> nlist;
        nlist = new ArrayList<>(mapSubTauxDabscence.entrySet());
        nlist.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));
        return nlist.get(nlist.size()-1).getKey();
    }

}
