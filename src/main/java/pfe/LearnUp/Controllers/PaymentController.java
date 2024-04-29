package pfe.LearnUp.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pfe.LearnUp.Dto.PaymentDto;
import pfe.LearnUp.Services.PaymentService;

import java.util.List;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<PaymentDto>> getAllPayments() {
        List<PaymentDto> payments = paymentService.getAllPayments();
        return new ResponseEntity<>(payments, HttpStatus.OK);
    }

    @GetMapping("/payments/byCourName")
    public List<PaymentDto> getPaymentsByCourName(@RequestParam String courName) {
        return paymentService.getPaymentsByCourName(courName);
    }

    @GetMapping("/byApprenant/{apprenantId}")
    public ResponseEntity<List<PaymentDto>> getPaymentsByApprenantId(@PathVariable Long apprenantId) {
        List<PaymentDto> payments = paymentService.getPaymentsByApprenantId(apprenantId);
        return new ResponseEntity<>(payments, HttpStatus.OK);
    }

    @GetMapping("/byCour/{courId}")
    public ResponseEntity<List<PaymentDto>> getPaymentsByCourId(@PathVariable Long courId) {
        List<PaymentDto> payments = paymentService.getPaymentsByCourId(courId);
        return new ResponseEntity<>(payments, HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<PaymentDto> savePayment(@RequestBody PaymentDto paymentDto) {
        PaymentDto savedPayment = paymentService.savePayment(paymentDto);
        return new ResponseEntity<>(savedPayment, HttpStatus.CREATED);
    }
}
