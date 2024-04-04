package pfe.LearnUp.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pfe.LearnUp.Entity.User;


@Repository
public interface UserRepository extends JpaRepository<User,Long> {


    User findByEmail(java.lang.String email);
}
