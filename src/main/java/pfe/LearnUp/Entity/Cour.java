package pfe.LearnUp.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cour {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long courId;
    @NonNull
    private String courName;
        @Column(name = "description", columnDefinition = "TEXT")
        @Lob
        private String description;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    private Double price;
    @Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private byte[] image;

    @Column(name = "needs_review")
    private Boolean needsReview;
    @Column(name = "discounted_price")
    private Double discountedPrice;
    private Double totalPrice;



    @ManyToOne
    @JoinColumn(name = "formateur_id")
    private Formateur formateur;

    // New field to track course status (pending, accepted, rejected)
    private String status;
    @ManyToOne
    @JoinColumn(name = "admin_id")
    private Admin admin;
    @JsonIgnore
    @OneToMany(mappedBy = "cour", cascade = CascadeType.ALL)
    private List<Chapitre> chapitres;
}
