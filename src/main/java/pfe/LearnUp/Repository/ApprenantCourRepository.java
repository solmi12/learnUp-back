package pfe.LearnUp.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pfe.LearnUp.Entity.ApprenantCour;

import java.util.List;

public interface ApprenantCourRepository extends JpaRepository<ApprenantCour, Long> {
    List<ApprenantCour> findByApprenant_ApprenantId(Long apprenantId);

    List<ApprenantCour> findByCour_CourId(Long courId);
}