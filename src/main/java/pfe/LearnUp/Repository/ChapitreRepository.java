package pfe.LearnUp.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pfe.LearnUp.Entity.Chapitre;

import java.util.List;

@Repository
public interface ChapitreRepository extends JpaRepository<Chapitre, Long> {
    List<Chapitre> findByCour_CourId(Long courId);
}