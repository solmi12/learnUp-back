package pfe.LearnUp.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pfe.LearnUp.Dto.CourDto;
import pfe.LearnUp.Dto.QuestionReponseDto;
import pfe.LearnUp.Entity.QuestionReponse;
import pfe.LearnUp.Services.CourService;
import pfe.LearnUp.Services.QuestionCourService;

import java.io.IOException;
import java.util.List;



@RestController
@RequestMapping("/question")
public class QuestionReponseController {



    @Autowired
    private QuestionCourService questionCourService;

    @PutMapping("/{qaId}")
    public ResponseEntity<QuestionReponseDto> updateQuestionReponse(@PathVariable Long qaId, @RequestBody QuestionReponseDto updatedDto) {
        try {
            QuestionReponseDto updatedQuestion = questionCourService.updateQuestionReponse(qaId, updatedDto);
            return ResponseEntity.ok(updatedQuestion);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
    @GetMapping("/apprenant/notifications/{apprenantId}")
    public ResponseEntity<List<QuestionReponseDto>> getApprenantNotifications(@PathVariable Long apprenantId) {
        List<QuestionReponseDto> notifications = questionCourService.getNewReponseForApprenantNotification(apprenantId);
        if (!notifications.isEmpty()) {
            return ResponseEntity.ok(notifications);
        } else {
            return ResponseEntity.noContent().build();
        }
    }


    @GetMapping("/question-reponses")
    public List<QuestionReponseDto> getReponseByFormateurId(@RequestParam Long formateurId) {
        return questionCourService.getReponseByFormateurId(formateurId);
    }
    @GetMapping("/question")
    public List<QuestionReponseDto> getReponseByApprenantId(@RequestParam Long apprenantId) {
        return questionCourService.getReponseByApprenantId(apprenantId);
    }
    @GetMapping("/formateur/notifications")
    public ResponseEntity<List<QuestionReponseDto>> getFormateurNotifications() {
        List<QuestionReponseDto> notifications = questionCourService.getNewQuestionForFormateurNotification();
        if (!notifications.isEmpty()) {
            return ResponseEntity.ok(notifications);
        } else {
            return ResponseEntity.noContent().build();
        }

    }


    @PostMapping("/add")
    public ResponseEntity<QuestionReponseDto> addQuestion(@RequestBody QuestionReponseDto questionReponse){

        try {

            QuestionReponseDto addedQuestion = questionCourService.addQuestion(questionReponse);
            return new ResponseEntity<>(addedQuestion, HttpStatus.CREATED);
        } catch (IOException e) {
            // Handle IO exception
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
