package pfe.LearnUp.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pfe.LearnUp.Dto.ApprenantCourDto;
import pfe.LearnUp.Dto.CourDto;
import pfe.LearnUp.Dto.QuestionReponseDto;
import pfe.LearnUp.Entity.*;
import pfe.LearnUp.Repository.FormateurRepository;
import pfe.LearnUp.Repository.QuestionReponseRepository;
import pfe.LearnUp.Repository.StudentRepository;

import java.io.IOException;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class QuestionCourService {

    @Autowired
    private  QuestionReponseRepository questionReponseRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private FormateurRepository formateurRepository;

    public QuestionReponseDto addQuestion(QuestionReponseDto questionReponseDto) throws IOException {
        QuestionReponse newQuestion = new QuestionReponse();

        newQuestion.setAddedDate(questionReponseDto.getAddedDate());
        newQuestion.setMessage(questionReponseDto.getMessage());
        newQuestion.setNeedsReview(true);


        if (questionReponseDto.getFormateurId() != null) {
            Formateur formateur = formateurRepository.findById(questionReponseDto.getFormateurId()).orElse(null);
            if (formateur != null) {
                newQuestion.setFormateur(formateur);
            } else {
            }
        }

        if (questionReponseDto.getApprenantId() != null) {
            Apprenant apprenant = studentRepository.findById(questionReponseDto.getApprenantId()).orElse(null);
            if (apprenant != null) {
                newQuestion.setApprenant(apprenant);
            } else {
            }
        }

        QuestionReponse savedQuestion = questionReponseRepository.save(newQuestion);

        return convertToDto(savedQuestion);
    }

    private List<QuestionReponseDto> fetchNewReponse() {
        List<QuestionReponse> fetchedReponse = questionReponseRepository.findByNeedsReviewFalse();

        return fetchedReponse.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    public List<QuestionReponseDto> getNewReponseForApprenantNotification(Long apprenantId) {
        List<QuestionReponseDto> newReponse = fetchNewReponse();
        updateQuestionNeedsReview(newReponse);

        return newReponse.stream()
                .filter(response -> response.getApprenantId().equals(apprenantId))
                .collect(Collectors.toList());
    }
    private final Object notificationLock = new Object();

    public List<QuestionReponseDto> getReponseByApprenantId(Long apprenantId) {
        List<QuestionReponse> questionReponsesList = questionReponseRepository.findByApprenant_ApprenantId(apprenantId);
        return questionReponsesList.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public QuestionReponseDto updateQuestionReponse(Long qaId, QuestionReponseDto updatedDto) {
        Optional<QuestionReponse> optionalQuestion = questionReponseRepository.findById(qaId);
        if (optionalQuestion.isPresent()) {
            QuestionReponse questionReponse = optionalQuestion.get();
            questionReponse.setMessage(updatedDto.getMessage());
            questionReponse.setNeedsReview(false);
            QuestionReponse updatedQuestion = questionReponseRepository.save(questionReponse);
            return convertToDto(updatedQuestion);
        } else {
            throw new IllegalArgumentException("QuestionReponse with ID " + qaId + " not found");
        }
    }
    public List<QuestionReponseDto> getReponseByFormateurId(Long formateurId) {
        List<QuestionReponse> questionReponsesList = questionReponseRepository.findByFormateur_FormateurId(formateurId);
        return questionReponsesList.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    private void updateQuestionNeedsReview(List<QuestionReponseDto> newQuetion) {
        newQuetion.forEach(questionReponseDto -> {
            Optional<QuestionReponse> optionalQuestion = questionReponseRepository.findById(questionReponseDto.getQaId());
            optionalQuestion.ifPresent(questionReponse -> {
                synchronized (notificationLock) {
                    if (questionReponse.getNeedsReview() != null) {
                        questionReponse.setNeedsReview(null);
                        questionReponseRepository.save(questionReponse);
                    }
                }
            });
        });
    }

    public List<QuestionReponseDto> getNewQuestionForFormateurNotification() {
        return questionReponseRepository.findByNeedsReviewTrue().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    public void deleteQuestionReponse(Long qaId) {
        questionReponseRepository.deleteById(qaId);
    }

    public QuestionReponseDto convertToDto(QuestionReponse questionReponse) {
        QuestionReponseDto dto = new QuestionReponseDto();
        dto.setQaId(questionReponse.getQaId());
        dto.setMessage(questionReponse.getMessage());
        dto.setApprenantId(questionReponse.getApprenant().getApprenantId());
        dto.setFormateurId(questionReponse.getFormateur().getFormateurId());
        dto.setAddedDate(questionReponse.getAddedDate());
        dto.setNeedsReview(questionReponse.getNeedsReview());
        return dto;
    }

    public QuestionReponse convertToEntity(QuestionReponseDto dto) {
        QuestionReponse questionReponse = new QuestionReponse();
        questionReponse.setQaId(dto.getQaId());
        Apprenant apprenant = new Apprenant();
        apprenant.setApprenantId(dto.getApprenantId());
        questionReponse.setApprenant(apprenant);

        Formateur formateur = new Formateur();
        formateur.setFormateurId(dto.getFormateurId());
        questionReponse.setFormateur(formateur);

        questionReponse.setMessage(dto.getMessage());
        questionReponse.setNeedsReview(dto.getNeedsReview());
        questionReponse.setAddedDate(dto.getAddedDate());
        return questionReponse;
    }
}
