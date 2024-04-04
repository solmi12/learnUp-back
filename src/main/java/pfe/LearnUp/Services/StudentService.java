package pfe.LearnUp.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pfe.LearnUp.Configurations.PasswordEncoder;
import pfe.LearnUp.Dto.AdminDTO;
import pfe.LearnUp.Dto.ApprenantDTO;
import pfe.LearnUp.Dto.UserDTO;
import pfe.LearnUp.Entity.Admin;
import pfe.LearnUp.Entity.Apprenant;
import pfe.LearnUp.Entity.User;
import pfe.LearnUp.Repository.StudentRepository;
import pfe.LearnUp.Repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public StudentService(StudentRepository studentRepository, UserRepository userRepository) {
        this.studentRepository = studentRepository;
        this.userRepository = userRepository;
    }

    public ApprenantDTO addStudent(ApprenantDTO apprenantDTO, UserDTO userDTO) {

        Apprenant apprenant = convertToStudentEntity(apprenantDTO, userDTO);
        User user = apprenant.getUser();

        User savedUser = userRepository.save(user);

        apprenant.setUser(savedUser);

        Apprenant savedApprenant = studentRepository.save(apprenant);

        return convertToStudentDTO(savedApprenant);
    }


    public List<ApprenantDTO> getAllStudents() {
        List<Apprenant> apprenants = studentRepository.findAll();
        List<ApprenantDTO> apprenantDTOS = new ArrayList<>();
        for (Apprenant apprenant : apprenants) {
            apprenantDTOS.add(convertToStudentDTO(apprenant));
        }
        return apprenantDTOS;
    }

    public ApprenantDTO getStudentById(Long apprenantId) {
        Optional<Apprenant> optionalStudent = studentRepository.findById(apprenantId);
        return optionalStudent.map(this::convertToStudentDTO).orElse(null);
    }

    public Apprenant getApprenantByUserId(Long userId) {
        return studentRepository.findByUserUserId(userId);
    }

    public void deleteStudentById(Long apprenantId) {
        Optional<Apprenant> optionalApprenant = studentRepository.findById(apprenantId);
        if (optionalApprenant.isPresent()) {
            Apprenant apprenant = optionalApprenant.get();
            User user = apprenant.getUser();
            if (user != null) {
                studentRepository.deleteById(apprenantId);

                userRepository.deleteById(user.getUserId());
            }
        }
    }


    public ApprenantDTO updateApprenant(Long apprenantId, ApprenantDTO updatedApprenantDTO) {
        Optional<Apprenant> optionalApprenant = studentRepository.findById(apprenantId);
        if (optionalApprenant.isPresent()) {
            Apprenant apprenant = optionalApprenant.get();

            apprenant.setFullName(updatedApprenantDTO.getFullName());
            apprenant.setInterests(updatedApprenantDTO.getInterests());

            apprenant.setEducationLevel(updatedApprenantDTO.getEducationLevel());

            UserDTO updatedUserDTO = updatedApprenantDTO.getUserDTO();
            if (updatedUserDTO != null) {
                User user = apprenant.getUser();
                if (user != null) {
                    user.setEmail(updatedUserDTO.getEmail());

                    String newPassword = updatedUserDTO.getPassword();
                    if (newPassword != null && !newPassword.isEmpty()) {
                        String encryptedPassword = passwordEncoder.encode(newPassword);
                        user.setPassword(encryptedPassword);
                    }
                }
            }

            apprenant = studentRepository.save(apprenant);

            return convertToStudentDTO(apprenant);
        } else {
            return null;
        }
    }



    private Apprenant convertToStudentEntity(ApprenantDTO apprenantDTO, UserDTO userDTO) {
        Apprenant apprenant = new Apprenant();
        apprenant.setFullName(apprenantDTO.getFullName());
        apprenant.setInterests(apprenantDTO.getInterests());
        apprenant.setEducationLevel(apprenantDTO.getEducationLevel());
        User user = new User();
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setRole(userDTO.getRole());

        apprenant.setUser(user);

        return apprenant;
    }
    private ApprenantDTO convertToStudentDTO(Apprenant apprenant) {
        ApprenantDTO apprenantDTO = new ApprenantDTO();
        apprenantDTO.setApprenantId(apprenant.getApprenantId());
        apprenantDTO.setFullName(apprenant.getFullName());
        apprenantDTO.setInterests(apprenant.getInterests());
        apprenantDTO.setEducationLevel(apprenant.getEducationLevel());

        User user = apprenant.getUser();
        if (user != null) {
            UserDTO userDTO = new UserDTO();
            userDTO.setUserId(user.getUserId());
            userDTO.setEmail(user.getEmail());
            userDTO.setPassword(user.getPassword());
            userDTO.setRole(user.getRole());
            apprenantDTO.setUserDTO(userDTO);
        }

        return apprenantDTO;
    }
}
