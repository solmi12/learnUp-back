package pfe.LearnUp.Controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pfe.LearnUp.Dto.AdminDTO;
import pfe.LearnUp.Dto.ApprenantDTO;
import pfe.LearnUp.Dto.FormateurAndUserDTO;
import pfe.LearnUp.Dto.FormateurDTO;
import pfe.LearnUp.Services.FormateurService;

import java.text.Normalizer;
import java.util.List;

@RestController
@RequestMapping("/formateur")
public class FormateurController {


    private final FormateurService formateurService;

    @Autowired
    public FormateurController(FormateurService formateurService){this.formateurService = formateurService;}

    @PostMapping("/add")
    public ResponseEntity<FormateurDTO> addStudent(@RequestBody FormateurAndUserDTO dto) {
        FormateurDTO addedFormateur = formateurService.addFormateur(dto.getFormateurDTO(), dto.getUserDTO());
        return new ResponseEntity<>(addedFormateur, HttpStatus.CREATED);
    }


    @GetMapping("/{formateurId}")
    public ResponseEntity<FormateurDTO> getAFormateurById(@PathVariable Long formateurId) {
        FormateurDTO formateurDTO = formateurService.getFormateurById(formateurId);
        if (formateurDTO != null) {
            return new ResponseEntity<>(formateurDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/all")
    public ResponseEntity<List<FormateurDTO>> getAllFormateurs() {
        List<FormateurDTO> formateurDTOS = formateurService.getAllFormateurs();
        return new ResponseEntity<>(formateurDTOS, HttpStatus.OK);
    }

    @DeleteMapping("/{formateurId}")
    public ResponseEntity<Void> deleteFormateurById(@PathVariable Long formateurId) {
        formateurService.deleteFormateurById(formateurId);
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/{formateurId}")
    public ResponseEntity<FormateurDTO> updateFormateur(@PathVariable Long formateurId, @RequestBody FormateurDTO updatedFormateurDto) {
        FormateurDTO updatedFormateur = formateurService.updateFormateur(formateurId, updatedFormateurDto);
        if (updatedFormateur != null) {
            return ResponseEntity.ok(updatedFormateur);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

