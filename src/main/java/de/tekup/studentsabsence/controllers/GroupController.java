package de.tekup.studentsabsence.controllers;


import de.tekup.studentsabsence.entities.*;
import de.tekup.studentsabsence.enums.LevelEnum;
import de.tekup.studentsabsence.enums.SpecialityEnum;
import de.tekup.studentsabsence.holders.GroupSubjectHolder;
import de.tekup.studentsabsence.services.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.*;

@Controller
@RequestMapping("/groups")
@AllArgsConstructor
public class GroupController {
    private final GroupService groupService;
    private final SubjectService subjectService;
    private final GroupSubjectService groupSubjectService;
    private final AbsenceService absenceService;
    private final StudentService studentService;
    private static final DecimalFormat df = new DecimalFormat("0.00");


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

        model.addAttribute("group", group);
        model.addAttribute("groupSubjects",groupSubjectService.getSubjectsByGroupId(id));
        model.addAttribute("students",group.getStudents());
        model.addAttribute("absenceService", absenceService);

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
        Group group = groupService.getGroupById(id);

        if(bindingResult.hasErrors()) {
            model.addAttribute("group", group);
            model.addAttribute("groupSubjects", groupSubjectService.getSubjectsByGroupId(id));
            model.addAttribute("students", students);
            return "groups/add-absences";
        }
        for (Student student : students) {
            absence.setStudent(student);
            absenceService.addAbsence(absence);
        }


        //TODO Complete the body of this method

        return "redirect:/groups/"+id+"/add-absences";
    }
    @GetMapping("/group-stats")
    public String absencesStat(Model model) {
        List<Group> groupList = new ArrayList<>(groupService.getAllGroups());
        Map<String, Map<String, Float>> map = new HashMap<>();
        for (Group group: groupList) {
            Map<String,Float> subjectAbsperGrp = new HashMap<>();
            for (GroupSubject groupSubject : groupSubjectService.getSubjectsByGroupId(group.getId())) {
                Subject subject = subjectService.getSubjectById(groupSubject.getSubject().getId());
                float taux = (       absenceService.hoursCountByGroupAndSubject(group.getId(),subject.getId())      /   groupSubject.getHours()         ) * 100 ;
                BigDecimal bd = new BigDecimal(taux).setScale(2, RoundingMode.HALF_UP);
                subjectAbsperGrp.put(subject.getName(), bd.floatValue());
            }

            Map.Entry<String, Float> min = null;
            Map.Entry<String, Float> max = null;
            for (Map.Entry<String, Float> entry : subjectAbsperGrp.entrySet()) {
                if (min == null || min.getValue() > entry.getValue() ) {
                    min = entry;
                }
                if (max == null || max.getValue() < entry.getValue()){
                    max = entry;
                }
            }
            Map<String, Float> subjectAbsFinal = new LinkedHashMap<>();
            assert min != null;
            subjectAbsFinal.put(min.getKey(), min.getValue());
            subjectAbsFinal.put(max.getKey(), max.getValue());
            map.put(group.getName() , subjectAbsFinal);
        }

        model.addAttribute("data", map);

        return "groups/stats";
    }




}
