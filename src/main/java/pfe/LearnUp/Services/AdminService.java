package pfe.LearnUp.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pfe.LearnUp.Dto.AdminDTO;
import pfe.LearnUp.Dto.UserDTO;
import pfe.LearnUp.Entity.Admin;
import pfe.LearnUp.Entity.User;
import pfe.LearnUp.Repository.AdminRepository;
import pfe.LearnUp.Repository.UserRepository;

import java.util.Optional;


@Service
public class AdminService {


    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    private final AdminRepository adminRepository;
    private final UserRepository userRepository;

    @Autowired
    public AdminService(AdminRepository adminRepository, UserRepository userRepository) {
        this.adminRepository = adminRepository;
        this.userRepository = userRepository;
    }

    public Admin getAdminByUserId(Long userId) {
        return adminRepository.findByUserUserId(userId);
    }

    public AdminDTO getAdminById(Long adminId) {
        Optional<Admin> optionalAdmin = adminRepository.findById(adminId);
        return optionalAdmin.map(this::convertToAdminDTO).orElse(null);
    }

    public AdminDTO updateAdmin(Long adminId, AdminDTO updatedAdminDTO) {
        Optional<Admin> optionalAdmin = adminRepository.findById(adminId);
        if (optionalAdmin.isPresent()) {
            Admin admin = optionalAdmin.get();

            admin.setFirstName(updatedAdminDTO.getFirstName());
            admin.setLastName(updatedAdminDTO.getLastName());

            UserDTO updatedUserDTO = updatedAdminDTO.getUserDTO();
            if (updatedUserDTO != null) {
                User user = admin.getUser();
                if (user != null) {
                    user.setEmail(updatedUserDTO.getEmail());

                    String newPassword = updatedUserDTO.getPassword();
                    if (newPassword != null && !newPassword.isEmpty()) {
                        String encryptedPassword = passwordEncoder.encode(newPassword);
                        user.setPassword(encryptedPassword);
                    }
                }
            }

            admin = adminRepository.save(admin);

            return convertToAdminDTO(admin);
        } else {
            return null;
        }
    }

    private AdminDTO convertToAdminDTO(Admin admin) {
        AdminDTO adminDTO = new AdminDTO();
        adminDTO.setAdminId(admin.getAdminId());
        adminDTO.setFirstName(admin.getFirstName());
        adminDTO.setLastName((admin.getLastName()));

        User user = admin.getUser();
        if (user != null) {
            UserDTO userDTO = new UserDTO();
            userDTO.setUserId(user.getUserId());
            userDTO.setEmail(user.getEmail());
            userDTO.setPassword(user.getPassword());
            userDTO.setRole(user.getRole());
            adminDTO.setUserDTO(userDTO);
        }

        return adminDTO;
    }
}
