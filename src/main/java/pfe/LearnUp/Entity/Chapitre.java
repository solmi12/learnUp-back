package pfe.LearnUp.Entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Chapitre {

    @Id

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chapitreId;

    @NonNull
    private String titre;
    @Column(columnDefinition = "TEXT")
    private String description;
    private Integer dureeEstimee;
    private boolean progressionComplete;
    private Integer ordreDansLeCours;
    @Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private byte[] pdfAttachment;

    @Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private byte[] image;
    @ManyToOne
    @JoinColumn(name = "cour_id")
    private Cour cour;
    private String youtubeVideoLink;
}
