package pfe.LearnUp.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pfe.LearnUp.Entity.Apprenant;

public interface StudentRepository extends JpaRepository<Apprenant,Long> {
    Apprenant findByUserUserId(Long userId);
}
