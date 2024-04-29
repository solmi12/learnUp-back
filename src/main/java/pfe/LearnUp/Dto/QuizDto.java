        package pfe.LearnUp.Dto;

        import jdk.jfr.Name;
        import lombok.AllArgsConstructor;
        import lombok.Data;
        import lombok.NoArgsConstructor;

        @Data
        @AllArgsConstructor
        @NoArgsConstructor
        public class QuizDto {
                private Long quizId;
                private Long courId;
                private String[] questions;
                private String[][] correctResponses;
                private String[][] falseResponses;
                private String[] userResponses;
                private boolean[] isCorrect;

                // Constructor to initialize arrays based on the number of questions
                public QuizDto(Long quizId, Long courId, int questionCount) {
                        this.quizId = quizId;
                        this.courId = courId;
                        this.questions = new String[questionCount];
                        this.correctResponses = new String[questionCount][1]; // Assuming one correct response for each question
                        this.falseResponses = new String[questionCount][3]; // Assuming 3 false responses for each question
                        this.userResponses = new String[questionCount]; // Initialize user responses array
                        this.isCorrect = new boolean[questionCount]; // Initialize correctness status array
                }

                // Getter and setter methods for questions array
                public String[] getQuestions() {
                        return questions;
                }

                public void setQuestions(String[] questions) {
                        this.questions = questions;
                }

                // Other getter and setter methods for other fields...
        }
