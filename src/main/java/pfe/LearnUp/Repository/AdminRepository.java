package pfe.LearnUp.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pfe.LearnUp.Entity.Admin;

@Repository
public interface AdminRepository extends JpaRepository<Admin,Long> {

    Admin findByUserUserId(Long userId);
}
