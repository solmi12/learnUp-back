package pfe.LearnUp.Dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDto {

    private Long paymentId;
    private Long apprenantId;
    private Long courId;
    private Date paymentDate;
    private Double paymentPrice;
    private String courName;
    private String fullName;
    private Double totalPrice;
}
