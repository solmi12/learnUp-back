package pfe.LearnUp.Dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApprenantCourDto {
    private Long apprenantCourid;
    private Long apprenantId;
    private Long courId;
    private Date addedDate;
}
