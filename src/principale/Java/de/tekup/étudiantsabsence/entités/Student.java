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


@ Entité
@ Données
@ AllArgsConstructor
@ NoArgsConstructor
 la classe  publique GroupSubject  implémente  Serializable {
    @ ID intégré
     identifiant GroupSubjectKey  privé ;

    @ ManyToOne
    @ MapsId ( "id_groupe" )
    @ JoinColumn ( nom = "group_id" )
     groupe  privé groupe ;

    @ ManyToOne
    @ MapsId ( "id_sujet" )
    @ JoinColumn ( nom = "id_sujet" )
     Sujet  privé sujet ;

     heures de flotteur  privé ;
    
    public  GroupSubjectKey  getId () {
        retourner  l'identifiant ;
    }

    public  void  setId ( identifiant GroupSubjectKey  ) {
        cela . identifiant = identifiant ;
    }
     flotteur  public getHeures () {
         heures de retour ;
    }

    public  void  setHours ( heures flottantes  ) {
        cela . heures = heures ;
    }

     groupe  public getGroup () {
         groupe de retour ;
    }

    public  void  setGroup ( groupe  groupe ) {
        cela . groupe = groupe ;
    }

     Objet  public getSubject () {
        retour  sujet ;
    }

    public  void  setSubject ( Sujet  sujet ) {
        cela . sujet = sujet ;
    }

    
}
