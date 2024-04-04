package pfe.LearnUp.Entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionReponse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long qaId;
    @ManyToOne
    @JoinColumn(name = "apprenant_id")
    private Apprenant apprenant;
    private String message;
    private Boolean needsReview;
    @ManyToOne
    @JoinColumn(name = "formateur_id")
    private Formateur formateur;

    private Date addedDate;
}

