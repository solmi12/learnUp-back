package pfe.LearnUp.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pfe.LearnUp.Dto.ApprenantCourDto;
import pfe.LearnUp.Dto.SouhaitsDto;
import pfe.LearnUp.Entity.Apprenant;
import pfe.LearnUp.Entity.Cour;
import pfe.LearnUp.Services.ApprenantCourService;

import java.util.List;

@RestController
@RequestMapping("/apprenantCour")
public class ApprenantCourController {

    @Autowired
    private ApprenantCourService apprenantCourService;


    @PostMapping("/add")
    public ResponseEntity<ApprenantCourDto> addApprenantCour(@RequestBody ApprenantCourDto dto) {
        ApprenantCourDto addedApprenantCour = apprenantCourService.addApprenantCour(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedApprenantCour);
    }

    @DeleteMapping("/delete/{apprenantCourid}")
    public ResponseEntity<Void> deleteApprenantCour(@PathVariable("apprenantCourid") Long apprenantCourId) {
        apprenantCourService.deleteApprenantCour(apprenantCourId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @GetMapping("/cours/{apprenantId}")
    public ResponseEntity<List<Cour>> getCoursByApprenantId(@PathVariable Long apprenantId) {
        List<Cour> courList = apprenantCourService.getCoursByApprenantId(apprenantId);
        return ResponseEntity.ok(courList);
    }


    @GetMapping("/coursApprenant/{apprenantId}")
    public ResponseEntity<?> getCoursApprenantByApprenantId(@PathVariable Long apprenantId) {
        List<ApprenantCourDto> apprenantCourDtoList = apprenantCourService.getApprenantCourDtosByApprenantId(apprenantId);

        if (apprenantCourDtoList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No apprenantCour found for the given apprenantId.");
        } else {
            return ResponseEntity.ok(apprenantCourDtoList);
        }
    }

    @GetMapping("/apprenants/{courId}")
    public ResponseEntity<List<Apprenant>> getApprenantsByCourId(@PathVariable Long courId) {
        List<Apprenant> apprenantList = apprenantCourService.getApprenantsByCourId(courId);
        return ResponseEntity.ok(apprenantList);
    }
}
