package pfe.LearnUp.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pfe.LearnUp.Entity.Formateur;


@Repository
public interface FormateurRepository extends JpaRepository <Formateur, Long> {
    Formateur findByUserUserId(Long userId);
}
