package pfe.LearnUp.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pfe.LearnUp.Dto.QuizDto;
import pfe.LearnUp.Entity.Cour;
import pfe.LearnUp.Entity.Quiz;
import pfe.LearnUp.Repository.CourRepository;
import pfe.LearnUp.Repository.QuizRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class QuizService {

    private final QuizRepository quizRepository;

    @Autowired
    private CourRepository courRepository;
    @Autowired
    public QuizService(QuizRepository quizRepository) {
        this.quizRepository = quizRepository;
    }



    public QuizDto saveQuiz(QuizDto quizDto) {
        Quiz quiz = convertDtoToEntity(quizDto);
        Quiz savedQuiz = quizRepository.save(quiz);
        return convertEntityToDto(savedQuiz);
    }

    public Optional<QuizDto> getQuizByCourId(Long courId) {
        Optional<Quiz> quizOptional = quizRepository.findByCour_CourId(courId);
        return quizOptional.map(this::convertEntityToDto);
    }

    public List<QuizDto> getAllQuizzes() {
        List<Quiz> quizzes = quizRepository.findAll();
        List<QuizDto> quizDtos = new ArrayList<>();

        for (Quiz quiz : quizzes) {
            quizDtos.add(convertEntityToDto(quiz));
        }

        return quizDtos;
    }

    public boolean deleteQuiz(Long quizId) {
        Optional<Quiz> existingQuiz = quizRepository.findById(quizId);
        if (existingQuiz.isPresent()) {
            quizRepository.deleteById(quizId);
            return true;
        } else {
            return false;
        }
    }
    public boolean checkUserResponse(QuizDto quizResponse) {
        String[] questions = quizResponse.getQuestions();
        String[] userResponses = quizResponse.getUserResponses();
        String[][] correctResponses = quizResponse.getCorrectResponses();

        int totalQuestions = questions.length;
        int correctAnswers = 0;

        // Compare user responses with correct responses
        for (int i = 0; i < totalQuestions; i++) {
            String userResponse = userResponses[i];
            String[] correctOptions = correctResponses[i];


            for (String correctOption : correctOptions) {
                if (userResponse.equalsIgnoreCase(correctOption)) {
                    correctAnswers++;
                    break;
                }
            }
        }

        double percentageScore = ((double) correctAnswers / totalQuestions) * 100;

        return percentageScore >= 50;
    }
    public Quiz convertDtoToEntity(QuizDto quizDto) {
        Quiz quiz = new Quiz();
        quiz.setQuizId(quizDto.getQuizId());
        quiz.setQuestion1(quizDto.getQuestions()[0]);
        quiz.setCorrectReponseQuestion1(quizDto.getCorrectResponses()[0][0]);
        quiz.setFalseReponse1Question1(quizDto.getFalseResponses()[0][0]);
        quiz.setFalseReponse2Question1(quizDto.getFalseResponses()[0][1]);
        quiz.setFalseReponse3Question1(quizDto.getFalseResponses()[0][2]);
        quiz.setQuestion2(quizDto.getQuestions()[1]);
        quiz.setCorrectReponseQuestion2(quizDto.getCorrectResponses()[1][0]);
        quiz.setFalseReponse1Question2(quizDto.getFalseResponses()[1][0]);
        quiz.setFalseReponse2Question2(quizDto.getFalseResponses()[1][1]);
        quiz.setFalseReponse3Question2(quizDto.getFalseResponses()[1][2]);
        quiz.setQuestion3(quizDto.getQuestions()[2]);
        quiz.setCorrectReponseQuestion3(quizDto.getCorrectResponses()[2][0]);
        quiz.setFalseReponse1Question3(quizDto.getFalseResponses()[2][0]);
        quiz.setFalseReponse2Question3(quizDto.getFalseResponses()[2][1]);
        quiz.setFalseReponse3Question3(quizDto.getFalseResponses()[2][2]);
        quiz.setQuestion4(quizDto.getQuestions()[3]);
        quiz.setCorrectReponseQuestion4(quizDto.getCorrectResponses()[3][0]);
        quiz.setFalseReponse1Question4(quizDto.getFalseResponses()[3][0]);
        quiz.setFalseReponse2Question4(quizDto.getFalseResponses()[3][1]);
        quiz.setFalseReponse3Question4(quizDto.getFalseResponses()[3][2]);
        quiz.setQuestion5(quizDto.getQuestions()[4]);
        quiz.setCorrectReponseQuestion5(quizDto.getCorrectResponses()[4][0]);
        quiz.setFalseReponse1Question5(quizDto.getFalseResponses()[4][0]);
        quiz.setFalseReponse2Question5(quizDto.getFalseResponses()[4][1]);
        quiz.setFalseReponse3Question5(quizDto.getFalseResponses()[4][2]);
        Long courId = quizDto.getCourId();
        Cour cour = courRepository.findById(courId).orElseThrow(() -> new IllegalArgumentException("Invalid courId: " + courId));
        quiz.setCour(cour);

        return quiz;
    }


    public QuizDto convertEntityToDto(Quiz quiz) {
        QuizDto quizDto = new QuizDto();
        quizDto.setQuizId(quiz.getQuizId());
        quizDto.setCourId(quiz.getCour().getCourId());
        quizDto.setQuestions(new String[]{
                quiz.getQuestion1(),
                quiz.getQuestion2(),
                quiz.getQuestion3(),
                quiz.getQuestion4(),
                quiz.getQuestion5()
        });
        quizDto.setCorrectResponses(new String[][]{
                {quiz.getCorrectReponseQuestion1()},
                {quiz.getCorrectReponseQuestion2()},
                {quiz.getCorrectReponseQuestion3()},
                {quiz.getCorrectReponseQuestion4()},
                {quiz.getCorrectReponseQuestion5()}
        });
        // Set false responses
        quizDto.setFalseResponses(new String[][]{
                {quiz.getFalseReponse1Question1(), quiz.getFalseReponse2Question1(), quiz.getFalseReponse3Question1()},
                {quiz.getFalseReponse1Question2(), quiz.getFalseReponse2Question2(), quiz.getFalseReponse3Question2()},
                {quiz.getFalseReponse1Question3(), quiz.getFalseReponse2Question3(), quiz.getFalseReponse3Question3()},
                {quiz.getFalseReponse1Question4(), quiz.getFalseReponse2Question4(), quiz.getFalseReponse3Question4()},
                {quiz.getFalseReponse1Question5(), quiz.getFalseReponse2Question5(), quiz.getFalseReponse3Question5()}
        });

        return quizDto;
    }

}
