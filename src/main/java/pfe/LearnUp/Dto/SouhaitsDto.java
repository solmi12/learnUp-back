package pfe.LearnUp.Dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SouhaitsDto {

    private Long souhaitId;
    private Long apprenantId;
    private Long courId;
}
