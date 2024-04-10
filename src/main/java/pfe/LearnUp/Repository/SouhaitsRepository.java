package pfe.LearnUp.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pfe.LearnUp.Entity.ApprenantCour;
import pfe.LearnUp.Entity.Souhaits;

import java.util.List;

@Repository
public interface SouhaitsRepository extends JpaRepository<Souhaits,Long> {

    List<Souhaits> findByApprenant_ApprenantId(Long apprenantId);

    boolean existsByApprenant_ApprenantIdAndCour_CourId(Long apprenantId, Long courId);
}
