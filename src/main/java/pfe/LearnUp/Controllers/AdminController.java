package pfe.LearnUp.Controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pfe.LearnUp.Dto.AdminDTO;
import pfe.LearnUp.Dto.ApprenantDTO;
import pfe.LearnUp.Services.AdminService;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    @Autowired
    public  AdminController(AdminService adminService){
        this.adminService = adminService;
    }


    @GetMapping("/{adminId}")
    public ResponseEntity<AdminDTO> getAdminById(@PathVariable Long adminId) {
       AdminDTO admin = adminService.getAdminById(adminId);
        if (admin != null) {
            return new ResponseEntity<>(admin, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PutMapping("/{adminId}")
    public ResponseEntity<AdminDTO> updateAdmin(@PathVariable Long adminId, @RequestBody AdminDTO updatedAdminDTO) {
        AdminDTO updatedAdmin = adminService.updateAdmin(adminId, updatedAdminDTO);
        if (updatedAdmin != null) {
            return ResponseEntity.ok(updatedAdmin);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
