package pfe.LearnUp.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pfe.LearnUp.Dto.SouhaitsDto;
import pfe.LearnUp.Entity.Cour;
import pfe.LearnUp.Services.SouhaitsService;

import java.util.List;

@RestController
@RequestMapping("/souhaits")
public class SouhaitsController {

    @Autowired
    private SouhaitsService souhaitsService;

    @PostMapping("/add")
    public ResponseEntity<SouhaitsDto> addSouhait(@RequestBody SouhaitsDto dto) {
        SouhaitsDto addedSouhait = souhaitsService.addSouhait(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedSouhait);
    }

    @GetMapping("/cours/{apprenantId}")
    public ResponseEntity<List<Cour>> getCoursByApprenantId(@PathVariable Long apprenantId) {
        List<Cour> courList = souhaitsService.getCoursByApprenantId(apprenantId);
        return ResponseEntity.ok(courList);
    }


    @DeleteMapping("/delete/{souhaitId}")
    public ResponseEntity<Void> deleteSouhaits(@PathVariable("souhaitId") Long souhaitId) {
        souhaitsService.deleteSouhaits(souhaitId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @GetMapping("/souhaits/{apprenantId}")
    public ResponseEntity<?> getSouhaitsByApprenantId(@PathVariable Long apprenantId) {
        List<SouhaitsDto> souhaitsDtoList = souhaitsService.getApprenantCourDtosByApprenantId(apprenantId);

        if (souhaitsDtoList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No souhaits found for the given apprenantId.");
        } else {
            return ResponseEntity.ok(souhaitsDtoList);
        }
    }


}