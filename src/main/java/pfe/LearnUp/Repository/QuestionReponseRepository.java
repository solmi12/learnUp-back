package pfe.LearnUp.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pfe.LearnUp.Entity.ApprenantCour;
import pfe.LearnUp.Entity.Cour;
import pfe.LearnUp.Entity.QuestionReponse;

import java.util.List;

public interface QuestionReponseRepository extends JpaRepository<QuestionReponse,Long> {
    List<QuestionReponse> findByApprenant_ApprenantId(Long apprenantId);

    List<QuestionReponse> findByNeedsReviewTrue();
    List<QuestionReponse> findByNeedsReviewFalse();

    List<QuestionReponse> findByFormateur_FormateurId(Long formateurid);
}
