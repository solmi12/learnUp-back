package pfe.LearnUp.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApprenantCour {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long apprenantCourid;

    @ManyToOne
    @JoinColumn(name = "apprenant_id")
    private Apprenant apprenant;

    @ManyToOne
    @JoinColumn(name = "cour_id")
    private Cour cour;

    private Date addedDate;
}
