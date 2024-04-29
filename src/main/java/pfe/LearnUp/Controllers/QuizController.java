package pfe.LearnUp.Controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pfe.LearnUp.Dto.PaymentDto;
import pfe.LearnUp.Dto.QuizDto;
import pfe.LearnUp.Services.PaymentService;
import pfe.LearnUp.Services.QuizService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/quiz")
public class QuizController {


    private final QuizService quizService;

    @Autowired
    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }



    @PostMapping("/save")
    public ResponseEntity<QuizDto> saveQuiz(@RequestBody QuizDto quizDto) {
        QuizDto savedQuiz = quizService.saveQuiz(quizDto);
        return new ResponseEntity<>(savedQuiz, HttpStatus.CREATED);
    }

    @GetMapping("/byCourId/{courId}")
    public ResponseEntity<QuizDto> getQuizByCourId(@PathVariable Long courId) {
        Optional<QuizDto> quizOptional = quizService.getQuizByCourId(courId);
        return quizOptional.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping("/all")
    public ResponseEntity<List<QuizDto>> getAllQuizzes() {
        List<QuizDto> quizzes = quizService.getAllQuizzes();
        return new ResponseEntity<>(quizzes, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{quizId}")
    public ResponseEntity<String> deleteQuiz(@PathVariable Long quizId) {
        boolean deleted = quizService.deleteQuiz(quizId);
        if (deleted) {
            return new ResponseEntity<>("Quiz deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Quiz not found", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/check-response")
    public ResponseEntity<Boolean> checkUserResponse(@RequestBody QuizDto quizResponse) {
        boolean isSuccessful = quizService.checkUserResponse(quizResponse);
        if (isSuccessful) {
            return new ResponseEntity<>(true, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(false, HttpStatus.OK);
        }
    }
}
