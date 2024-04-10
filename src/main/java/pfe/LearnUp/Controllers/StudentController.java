package pfe.LearnUp.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import pfe.LearnUp.Dto.AdminDTO;
import pfe.LearnUp.Dto.StudentAndUserDTO;
import pfe.LearnUp.Dto.ApprenantDTO;

import pfe.LearnUp.Entity.Apprenant;
import pfe.LearnUp.Services.StudentService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping("/add") //
    public ResponseEntity<ApprenantDTO> addStudent(@RequestBody StudentAndUserDTO dto) {
        ApprenantDTO addedStudent = studentService.addStudent(dto.getApprenantDTO(), dto.getUserDTO());
        return new ResponseEntity<>(addedStudent, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ApprenantDTO>> getAllStudents() {
        List<ApprenantDTO> students = studentService.getAllStudents();
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    @GetMapping("/{apprenantId}")
    public ResponseEntity<ApprenantDTO> getStudentById(@PathVariable Long apprenantId) {
        ApprenantDTO student = studentService.getStudentById(apprenantId);
        if (student != null) {
            return new ResponseEntity<>(student, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{apprenantId}")
    public ResponseEntity<Void> deleteStudentById(@PathVariable Long apprenantId) {
        studentService.deleteStudentById(apprenantId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/byClasse/{classe}")
    public ResponseEntity<List<ApprenantDTO>> getApprenantsByClasse(@PathVariable String classe) {
        List<ApprenantDTO> apprenants = studentService.getApprenantsByClasse(classe);
        return ResponseEntity.ok(apprenants);
    }
    @PutMapping("/{apprenantId}")
    public ResponseEntity<ApprenantDTO> updateFormateur(@PathVariable Long apprenantId, @RequestBody ApprenantDTO updatedApprenantDto) {
        ApprenantDTO updatedApprenant = studentService.updateApprenant(apprenantId, updatedApprenantDto);
        if (updatedApprenant != null) {
            return ResponseEntity.ok(updatedApprenant);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
