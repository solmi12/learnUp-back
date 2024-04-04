package pfe.LearnUp.Dto;

import lombok.Data;

import java.util.List;

@Data
public class AdminDTO {
    private Long adminId;
    private UserDTO userDTO;
    private String firstName;
    private String lastName;

}

