package pfe.LearnUp.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pfe.LearnUp.Entity.Payment;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List    <Payment> findByApprenant_ApprenantId(Long apprenantId);
    List<Payment> findByCour_CourId(Long courId);
    List<Payment> findByCour_CourName(String courName);
}