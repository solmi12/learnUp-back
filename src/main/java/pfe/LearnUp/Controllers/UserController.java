package pfe.LearnUp.Controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pfe.LearnUp.Entity.*;
import pfe.LearnUp.Services.AdminService;
import pfe.LearnUp.Services.FormateurService;
import pfe.LearnUp.Services.StudentService;
import pfe.LearnUp.Services.UserService;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    AdminService adminService;
    @Autowired
    StudentService apprenantService;
    @Autowired
    FormateurService formateurService;
    @Autowired
    UserService userService;

    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserDetails(@PathVariable Long userId) {
        User user = userService.getUserById(userId);
        if (user != null) {
            if (user.getRole() == Role.ADMIN) {
                Admin admin = adminService.getAdminByUserId(userId);
                return new ResponseEntity<>(admin, HttpStatus.OK);
            } else if (user.getRole() == Role.FORMATEUR) {
                Formateur formateur = formateurService.getFormateurByUserId(userId);
                return new ResponseEntity<>(formateur, HttpStatus.OK);
            } else if (user.getRole() == Role.APPRENANT) {
                Apprenant apprenant = apprenantService.getApprenantByUserId(userId);
                return new ResponseEntity<>(apprenant, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("User role not recognized", HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
    }

}
