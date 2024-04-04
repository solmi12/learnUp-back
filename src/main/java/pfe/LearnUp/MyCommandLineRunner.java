package pfe.LearnUp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import pfe.LearnUp.Entity.Admin;
import pfe.LearnUp.Entity.Role;
import pfe.LearnUp.Entity.User;
import pfe.LearnUp.Repository.AdminRepository;
import pfe.LearnUp.Repository.UserRepository;

@Component
public class MyCommandLineRunner implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Application started.");
        User existingUser = userRepository.findByEmail("admin@admin.com");

        if (existingUser == null) {
            User adminUser = new User();
            adminUser.setEmail("admin@admin.com");
            adminUser.setPassword(passwordEncoder.encode("123"));
            adminUser.setRole(Role.ADMIN);
            userRepository.save(adminUser);

            Admin newAdmin = new Admin();
            newAdmin.setUser(adminUser);
            newAdmin.setFirstName("admin");
            newAdmin.setLastName("admin");

            adminRepository.save(newAdmin);

            System.out.println("New admin created.");
        } else {
            System.out.println("Admin already exists in the database.");
        }

        System.out.println("Command executed.");
    }
}
