package pfe.LearnUp.Entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long quizId;


    @ManyToOne
    @JoinColumn(name = "cour_id")
    private Cour cour;


    private String question1;
    private String correctReponseQuestion1;
    private String falseReponse1Question1;
    private String falseReponse2Question1;
    private String falseReponse3Question1;
    private String question2;
    private String correctReponseQuestion2;
    private String falseReponse1Question2;
    private String falseReponse2Question2;
    private String falseReponse3Question2;
    private String question3;
    private String correctReponseQuestion3;
    private String falseReponse1Question3;
    private String falseReponse2Question3;
    private String falseReponse3Question3;
    private String question4;
    private String correctReponseQuestion4;
    private String falseReponse1Question4;
    private String falseReponse2Question4;
    private String falseReponse3Question4;
    private String question5;
    private String correctReponseQuestion5;
    private String falseReponse1Question5;
    private String falseReponse2Question5;
    private String falseReponse3Question5;


}
