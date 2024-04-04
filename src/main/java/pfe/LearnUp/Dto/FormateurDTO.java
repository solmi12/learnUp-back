package pfe.LearnUp.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FormateurDTO {
    private Long formateurId;
    private UserDTO userDTO;
    private String fullName;
    private String expertise;
    private String experience;
    private String phoneNumber;
    private List<CourDto> courDtos;
}