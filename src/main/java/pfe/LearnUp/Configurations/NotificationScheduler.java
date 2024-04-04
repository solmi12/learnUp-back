    package pfe.LearnUp.Configurations;


    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.scheduling.annotation.Scheduled;
    import org.springframework.stereotype.Component;
    import pfe.LearnUp.Dto.CourDto;
    import pfe.LearnUp.Dto.QuestionReponseDto;
    import pfe.LearnUp.Entity.Formateur;
    import pfe.LearnUp.Services.CourService;
    import pfe.LearnUp.Services.QuestionCourService;

    import java.util.List;

    @Component
    public class NotificationScheduler {

        @Autowired
        private CourService courService;

        @Autowired
        private QuestionCourService questionCourService;


        @Scheduled(fixedRate = 30000)
        public void checkAndNotifyAdminForNewCours() {
            List<CourDto> newCours = courService.getNewCoursForAdminNotification();
            if (!newCours.isEmpty()) {
                System.out.println("Notification: New Cours added, needs admin review");
            }
        }


        @Scheduled(fixedRate = 30000)
        public void checkAndNotifyFormateurForNewResponse() {
            List<QuestionReponseDto> newQuestions = questionCourService.getNewQuestionForFormateurNotification();
            for (QuestionReponseDto question : newQuestions) {
                Long formateurId = question.getFormateurId();
                List<QuestionReponseDto> questionReponses = questionCourService.getReponseByFormateurId(formateurId);
                if (!questionReponses.isEmpty()) {
                    System.out.println("Notification: New responses for Formateur " + formateurId);
                    // Implement your notification logic here, such as sending an email or using a notification service
                }
            }
        }

        @Scheduled(fixedRate = 30000)
        public void checkAndNotifyFormateur() {
            List<CourDto> newCours = courService.getNewCoursForFormateurNotification();
            newCours.forEach(cour -> {
                Long formateurId = cour.getFormateurId();
                List<CourDto> formateurCours = courService.getCoursByFormateurId(formateurId);

                if (!formateurCours.isEmpty() && formateurCours.stream().anyMatch(c -> c.getNeedsReview())) {
                    System.out.println("Notification: New Cours added, needs admin review");

                }
            });
        }


        @Scheduled(fixedRate = 30000)
        public void checkAndNotifyApprenant(Long apprenantId) {
            List<QuestionReponseDto> newReponse = questionCourService.getNewReponseForApprenantNotification(apprenantId);
            newReponse.forEach(reponse -> {
                List<QuestionReponseDto> questionReponse = questionCourService.getReponseByApprenantId(apprenantId);

                if (!questionReponse.isEmpty() && questionReponse.stream().anyMatch(c -> c.getNeedsReview())) {
                    System.out.println("Notification: New Cours added, needs admin review");
                }
            });
        }









    }
