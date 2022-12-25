package de.tekup.studentsabsence.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"image","group","absences"})
public class Student implements Serializable {
    //TODO Complete Validations of fields


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sid;

    @NotBlank(message = "Name is required")
    @Size(min = 5, max = 25)
    private String firstName;
    @NotBlank(message = "lastName is required")
    @Size(min = 5, max = 25)
    private String lastName;
    @Email
    private String email;
    @NotBlank
    private String phone;
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate dob;

    //TODO Complete Relations with other entities
    @OneToOne
    Image image;

    @ManyToOne
    Group group;

    @OneToMany(mappedBy = "student")
    List<Absence> absences ;



}
