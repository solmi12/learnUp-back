package pfe.LearnUp.Controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pfe.LearnUp.Dto.CourDto;
import pfe.LearnUp.Services.CourService;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/cour")
public class CourController {

    @Autowired
    private CourService courService;


    @PostMapping("/add")
    public ResponseEntity<CourDto> addTool(@RequestBody CourDto courDto){

        try {
            CourDto addedCour = courService.addCour(courDto);
            return new ResponseEntity<>(addedCour, HttpStatus.CREATED);
        } catch (IOException e) {
            // Handle IO exception
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/formateur/{formateurId}")
    public List<CourDto> getCoursByFormateurId(@PathVariable Long formateurId) {
        return courService.getCoursByFormateurId(formateurId);
    }

    @GetMapping("/pending-courses")
    public ResponseEntity<List<CourDto>> getPendingCourses() {
            List<CourDto> pendingCourses = courService.getCoursByStatus("pending");
        return ResponseEntity.ok(pendingCourses);
    }

    @GetMapping("/accepted-courses")
    public ResponseEntity<List<CourDto>> getAcceptedCourses() {
        List<CourDto> acceptedCourses = courService.getCoursByStatus("accepted");
        return ResponseEntity.ok(acceptedCourses);
    }

    @GetMapping("/{courId}")
    public ResponseEntity<CourDto> getCourById(@PathVariable Long courId) {
        CourDto courDto = courService.getCourById(courId);
        if (courDto != null) {
            return new ResponseEntity<>(courDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/admin/notifications")
    public ResponseEntity<List<CourDto>> getAdminNotifications() {
        List<CourDto> notifications = courService.getNewCoursForAdminNotification();
        if (!notifications.isEmpty()) {
            return ResponseEntity.ok(notifications);
        } else {
            return ResponseEntity.noContent().build();
        }

    }
    @PutMapping("/accept-cour/{courId}")
    public ResponseEntity<CourDto> acceptCour(@PathVariable Long courId) {
        CourDto acceptedCour = courService.acceptCour(courId);
        if (acceptedCour != null) {
            return ResponseEntity.ok(acceptedCour);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/refuse-cour/{courId}")
    public ResponseEntity<CourDto> refuseCour(@PathVariable Long courId) {
        CourDto refusedCour = courService.refuseCour(courId);
        if (refusedCour != null) {
            return ResponseEntity.ok(refusedCour);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping("/formateur/notifications")
    public ResponseEntity<List<CourDto>> getFormateurNotifications() {
        List<CourDto> notifications = courService.getNewCoursForFormateurNotification();
        if (!notifications.isEmpty()) {
            return ResponseEntity.ok(notifications);
        } else {
            return ResponseEntity.noContent().build();
        }
    }


    @GetMapping("/byCategory")
    public List<CourDto> getCourByCategory(@RequestParam(required = false) String categoryName) {
        return courService.searchCoursByCategory(categoryName);
    }

    @GetMapping("/search")
    public ResponseEntity<List<CourDto>> searchToolsByName(@RequestParam(name = "courName", required = false) String courName) {
        List<CourDto> result = courService.searchCourByName(courName);
        return ResponseEntity.ok(result);
    }


    @GetMapping("/byCategory/{categoryId}")
    public List<CourDto> getCourByCategory(@PathVariable Long categoryId) {
        return courService.getCoursByCategory(categoryId);
    }
    @DeleteMapping("/{courId}")
    public ResponseEntity<Void> deleteCour(@PathVariable Long courId) {
        if (courService.deleteCour(courId)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            // Handle not found case
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @GetMapping
    public List<CourDto> getAllCours() {
        return courService.getAllCours();
    }
}
