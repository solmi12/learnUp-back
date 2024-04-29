package pfe.LearnUp.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pfe.LearnUp.Dto.PaymentDto;
import pfe.LearnUp.Entity.Apprenant;
import pfe.LearnUp.Entity.Cour;
import pfe.LearnUp.Entity.Payment;
import pfe.LearnUp.Repository.CourRepository;
import pfe.LearnUp.Repository.PaymentRepository;
import pfe.LearnUp.Repository.StudentRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final StudentRepository apprenantRepository;
    private final CourRepository courRepository;

    @Autowired
    public PaymentService(PaymentRepository paymentRepository, StudentRepository apprenantRepository,
                          CourRepository courRepository) {
        this.paymentRepository = paymentRepository;
        this.apprenantRepository = apprenantRepository;
        this.courRepository = courRepository;
    }

    public List<PaymentDto> getAllPayments() {
        List<Payment> payments = paymentRepository.findAll();
        return payments.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public List<PaymentDto> getPaymentsByApprenantId(Long apprenantId) {
        List<Payment> payments = paymentRepository.findByApprenant_ApprenantId(apprenantId);
        return payments.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public List<PaymentDto> getPaymentsByCourId(Long courId) {
        List<Payment> payments = paymentRepository.findByCour_CourId(courId);
        return payments.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public List<PaymentDto> getPaymentsByCourName(String courName) {
        List<Payment> payments = paymentRepository.findByCour_CourName(courName);
        return payments.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public PaymentDto savePayment(PaymentDto paymentDto) {
        Payment payment = convertToEntity(paymentDto);
        Payment savedPayment = paymentRepository.save(payment);
        return convertToDto(savedPayment);
    }

    private PaymentDto convertToDto(Payment payment) {
        return new PaymentDto(
                payment.getPaymentId(),
                payment.getApprenant().getApprenantId(),
                payment.getCour().getCourId(),
                payment.getPaymentDate(),
                payment.getPaymentPrice(),
                payment.getCourName(),
                payment.getFullName(),
                payment.getTotalPrice()
        );
    }

    private Payment convertToEntity(PaymentDto paymentDto) {
        Optional<Apprenant> apprenantOptional = apprenantRepository.findById(paymentDto.getApprenantId());
        Optional<Cour> courOptional = courRepository.findById(paymentDto.getCourId());
        if (apprenantOptional.isPresent() && courOptional.isPresent()) {
            Apprenant apprenant = apprenantOptional.get();
            Cour cour = courOptional.get();
            return new Payment(null, apprenant, cour,
                    paymentDto.getPaymentDate(), paymentDto.getFullName(),
                    paymentDto.getCourName(), paymentDto.getPaymentPrice(), paymentDto.getTotalPrice());
        } else {
            throw new IllegalArgumentException("Apprenant or Cour not found with provided IDs");
        }
    }
}
