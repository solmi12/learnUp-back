package pfe.LearnUp.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApprenantDTO {
    private Long apprenantId;
    private UserDTO userDTO;
    private String fullName;
    private String interests;
    private String educationLevel;
}
