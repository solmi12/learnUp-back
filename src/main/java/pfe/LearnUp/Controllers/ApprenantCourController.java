package pfe.LearnUp.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pfe.LearnUp.Dto.ApprenantCourDto;
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
        return new ResponseEntity<>(addedApprenantCour, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteApprenantCour(@PathVariable("id") Long apprenantCourId) {
        apprenantCourService.deleteApprenantCour(apprenantCourId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @GetMapping("/cours/{apprenantId}")
    public List<Cour> getCoursByApprenantId(@PathVariable Long apprenantId) {
        return apprenantCourService.getCoursByApprenantId(apprenantId);
    }

    @GetMapping("/apprenants/{courId}")
    public List<Apprenant> getApprenantsByCourId(@PathVariable Long courId) {
        return apprenantCourService.getApprenantsByCourId(courId);
    }
}
