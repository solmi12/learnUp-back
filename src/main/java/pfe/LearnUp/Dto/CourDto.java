package pfe.LearnUp.Dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import pfe.LearnUp.Entity.Category;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourDto {

    private Long courId;
    @NonNull
    private String courName;
    private String description;

    private Long formateurId;

    private CategoryDto category;

    private Double price;
    private String imageData;
        private Boolean needsReview;
    @Column(name = "discounted_price")
    private Double discountedPrice;
    private Double totalPrice;
        private String status;
    private List<ChapitreDto> chapitres;

}
