package pfe.LearnUp.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pfe.LearnUp.Entity.Quiz;

import java.util.List;
import java.util.Optional;


@Repository
public interface QuizRepository extends JpaRepository<Quiz,Long> {

    Optional<Quiz> findByCour_CourId(Long courId);
}
