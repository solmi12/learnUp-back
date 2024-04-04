package pfe.LearnUp.Controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pfe.LearnUp.Dto.ChapitreDto;
import pfe.LearnUp.Services.ChapitreService;

import java.util.List;

@RestController
    @RequestMapping("/chapitres")
public class ChapitreController {

    @Autowired
    private ChapitreService chapitreService;

    @PostMapping("/add")
    public ResponseEntity<ChapitreDto> addChapitre(@RequestBody ChapitreDto chapitreDto) {
        ChapitreDto addedChapitre = chapitreService.addChapitre(chapitreDto);
        return new ResponseEntity<>(addedChapitre, HttpStatus.CREATED);
    }

    @GetMapping("/byCourId/{courId}")
    public ResponseEntity<List<ChapitreDto>> getChapitresByCourId(@PathVariable Long courId) {
        List<ChapitreDto> chapitres = chapitreService.getChapitresByCourId(courId);
        return new ResponseEntity<>(chapitres, HttpStatus.OK);
    }

}
