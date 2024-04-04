package pfe.LearnUp.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pfe.LearnUp.Entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    // You can add custom query methods if needed
}