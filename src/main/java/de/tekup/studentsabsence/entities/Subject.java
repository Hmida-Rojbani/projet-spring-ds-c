package de.tekup.studentsabsence.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Subject implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Name is required")
    private String name;

    @OneToMany(mappedBy = "subject", fetch = FetchType.LAZY,cascade = CascadeType.MERGE)
    @Nullable
    public List<GroupSubject> groupSubjects = new ArrayList<>();

    @OneToMany(mappedBy = "subject", fetch = FetchType.LAZY,cascade = CascadeType.MERGE)
    @Nullable
    public List<Absence> absencesSubject = new ArrayList<>();

}
