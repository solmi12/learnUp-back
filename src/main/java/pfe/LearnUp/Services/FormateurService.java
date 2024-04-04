package pfe.LearnUp.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pfe.LearnUp.Dto.ApprenantDTO;
import pfe.LearnUp.Dto.FormateurDTO;
import pfe.LearnUp.Dto.UserDTO;
import pfe.LearnUp.Entity.Apprenant;
import pfe.LearnUp.Entity.Formateur;
import pfe.LearnUp.Entity.User;
import pfe.LearnUp.Repository.FormateurRepository;
import pfe.LearnUp.Repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class FormateurService {


    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    private final FormateurRepository formateurRepository;
    private final UserRepository userRepository;

    @Autowired
    public FormateurService(FormateurRepository formateurRepository, UserRepository userRepository) {
        this.formateurRepository =  formateurRepository;
        this.userRepository = userRepository;
    }



    public FormateurDTO updateFormateur(Long formateurId, FormateurDTO updatedFormateurDTO) {
        Optional<Formateur> optionalFormateur = formateurRepository.findById(formateurId);
        if (optionalFormateur.isPresent()) {
            Formateur formateur = optionalFormateur.get();

            formateur.setFullName(updatedFormateurDTO.getFullName());
            formateur.setPhoneNumber(updatedFormateurDTO.getPhoneNumber());

            formateur.setExperience(updatedFormateurDTO.getExperience());
            formateur.setExpertise(updatedFormateurDTO.getExpertise());

            UserDTO updatedUserDTO = updatedFormateurDTO.getUserDTO();
            if (updatedUserDTO != null) {
                User user = formateur.getUser();
                if (user != null) {
                    user.setEmail(updatedUserDTO.getEmail());

                    String newPassword = updatedUserDTO.getPassword();
                    if (newPassword != null && !newPassword.isEmpty()) {
                        String encryptedPassword = passwordEncoder.encode(newPassword);
                        user.setPassword(encryptedPassword);
                    }
                }
            }

            formateur = formateurRepository.save(formateur);

            return convertToFormateurDTO(formateur);
        } else {
            return null;
        }
    }


    public FormateurDTO addFormateur(FormateurDTO formateurDTO, UserDTO userDTO) {

        Formateur formateur = convertToFormateurEntity(formateurDTO, userDTO);
        User user = formateur.getUser();

        User savedUser = userRepository.save(user);

        formateur.setUser(savedUser);

        Formateur savedFormateur = formateurRepository.save(formateur);

        return convertToFormateurDTO(savedFormateur);
    }


    public Formateur getFormateurByUserId(Long userId) {
        return formateurRepository.findByUserUserId(userId);
    }
    public FormateurDTO getFormateurById(Long formateurId) {
        Optional<Formateur> optionalFormateur = formateurRepository.findById(formateurId);
        return optionalFormateur.map(this::convertToFormateurDTO).orElse(null);
    }
    public void deleteFormateurById(Long formateurId) {
        Optional<Formateur> optionalFormateur = formateurRepository.findById(formateurId);
        optionalFormateur.ifPresent(formateur -> {
            formateurRepository.delete(formateur);
        });
    }


    public List<FormateurDTO> getAllFormateurs() {
        List<Formateur> formateurs = formateurRepository.findAll();
        List<FormateurDTO> formateurDTOs = new ArrayList<>();
        for (Formateur formateur : formateurs) {
            formateurDTOs.add(convertToFormateurDTO(formateur));
        }
        return formateurDTOs;
    }

    private Formateur convertToFormateurEntity(FormateurDTO formateurDTO, UserDTO userDTO) {
        Formateur formateur = new Formateur();
        formateur.setFullName(formateurDTO.getFullName());
        formateur.setExpertise(formateurDTO.getExpertise());
        formateur.setExperience(formateurDTO.getExperience());
        formateur.setPhoneNumber(formateurDTO.getPhoneNumber());
        User user = new User();
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setRole(userDTO.getRole());

        formateur.setUser(user);

        return formateur;
    }

    private FormateurDTO convertToFormateurDTO(Formateur formateur) {
        FormateurDTO formateurDTO = new FormateurDTO();
        formateurDTO.setFormateurId(formateur.getFormateurId());
        formateurDTO.setFullName(formateur.getFullName());
        formateurDTO.setExperience(formateur.getExperience());
        formateurDTO.setExpertise(formateur.getExpertise());
        formateurDTO.setPhoneNumber(formateur.getPhoneNumber());

        User user = formateur.getUser();
        if (user != null) {
            UserDTO userDTO = new UserDTO();
            userDTO.setUserId(user.getUserId());
            userDTO.setEmail(user.getEmail());
            userDTO.setPassword(user.getPassword());
            userDTO.setRole(user.getRole());
            formateurDTO.setUserDTO(userDTO);
        }

        return formateurDTO;
    }

}
