package pfe.LearnUp.Entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Souhaits {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long souhaitId;

    @ManyToOne
    @JoinColumn(name = "apprenant_id")
    private Apprenant apprenant;

    @ManyToOne
    @JoinColumn(name = "cour_id")
    private Cour cour;

}

