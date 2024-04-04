package pfe.LearnUp.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionReponseDto {

    private Long qaId;
    private Long apprenantId;
    private String message;
    private Long formateurId;
    private Boolean needsReview;
    private Date addedDate;
}
