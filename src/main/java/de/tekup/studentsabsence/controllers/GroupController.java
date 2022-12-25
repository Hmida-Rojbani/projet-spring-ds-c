package de.tekup.studentsabsence.controllers;


import de.tekup.studentsabsence.entities.Absence;
import de.tekup.studentsabsence.entities.Group;
import de.tekup.studentsabsence.entities.Student;
import de.tekup.studentsabsence.enums.LevelEnum;
import de.tekup.studentsabsence.enums.SpecialityEnum;
import de.tekup.studentsabsence.holders.GroupSubjectHolder;
import de.tekup.studentsabsence.repositories.StudentRepository;
import de.tekup.studentsabsence.services.AbsenceService;
import de.tekup.studentsabsence.services.GroupService;
import de.tekup.studentsabsence.services.GroupSubjectService;
import de.tekup.studentsabsence.services.SubjectService;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/groups")
@AllArgsConstructor
public class GroupController {
    private final GroupService groupService;
    private final SubjectService subjectService;
    private final GroupSubjectService groupSubjectService;
    private final AbsenceService absenceService;
    private final StudentRepository studentRepository;

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
        List<Student> students = groupService.getStudentsForGroup(id);
        model.addAttribute("group", group);
        model.addAttribute("groupSubjects",groupSubjectService.getSubjectsByGroupId(id));
        model.addAttribute("student",students);
        System.out.println(students);
        students.forEach(st->{
            System.out.println(st);
        });



        model.addAttribute("absenceService", absenceService);

        group.getStudents().forEach(student -> {
            System.out.println("List of students : " +student);
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
        List<Student> students = groupService.getStudentsForGroup(id);
        model.addAttribute("group", group);
        model.addAttribute("absence", new Absence());
        model.addAttribute("students", students);
        System.out.println(group.getStudents());
        model.addAttribute("groupSubjects", groupSubjectService.getSubjectsByGroupId(id));


        return "groups/add-absences";
    }

    @PostMapping("/{id}/add-absences")
    public String addAbsence(@PathVariable long id, @Valid Absence absence, BindingResult bindingResult, @RequestParam(value = "students", required = false) List<Student> students, Model model) {
        //TODO Complete the body of this method
        Group group=groupService.getGroupById(id);

        if(bindingResult.hasErrors()){
            model.addAttribute("group",group);
            model.addAttribute("groupSubjects",groupSubjectService.getSubjectsByGroupId(id));
            model.addAttribute("students",students);
            return "group/add-absences";
        }
        for (Student student:students){
            absence.setStudent(student);
            absenceService.addAbsence(absence);
        }
        return "redirect:/groups/"+id+"/add-absences";
    }

}
