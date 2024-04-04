package pfe.LearnUp.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pfe.LearnUp.Entity.Cour;

import java.util.List;

public interface CourRepository extends JpaRepository<Cour,Long> {

    List<Cour> findByCategoryCategoryId(Long categoryId);


    // New method to find Cours by Formateur ID
    List<Cour> findByFormateurFormateurId(Long formateurId);
    @Modifying
    @Query("UPDATE Cour t SET t.discountedPrice = :discountedPrice WHERE t.courId = :courId")
    void updateDiscountedPrice(@Param("courId") Long courId, @Param("discountedPrice") Double discountedPrice);
    List<Cour> findByCourNameContaining(String courName);

    List<Cour> findByNeedsReviewTrue();
    List<Cour> findByNeedsReviewFalse();
    List<Cour> findByStatus(String status);

    List<Cour> findByDiscountedPriceIsNotNull();
    List<Cour> findByCategoryCategoryName(String categoryName);
}
