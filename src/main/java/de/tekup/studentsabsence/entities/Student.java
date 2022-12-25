package de.tekup.studentsabsence.entities;

import lombok.*;
import org.hibernate.Hibernate;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class Student implements Serializable {
    //TODO Complete Validations of fields


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sid;

    @NotBlank(message = "FirstName is required")
    private String firstName;

    @NotBlank(message = "LastName is required")
    private String lastName;

    @Email(message = "Enter a valid email address")
    @NotBlank(message = "Email is required")
    private String email;

    @Size(min = 8, max = 8)
    @NotBlank
    @NumberFormat(style = NumberFormat.Style.NUMBER)
    private String phone;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @Past(message = "Enter valid date.")
    private LocalDate dob;

    //TODO Complete Relations with other entities

    @OneToMany(mappedBy = "student", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Absence> absences;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "gid", nullable = false)
    @ToString.Exclude
    private Group group;


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id")
    @ToString.Exclude
    private Image image;

}
