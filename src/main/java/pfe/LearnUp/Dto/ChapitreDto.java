package pfe.LearnUp.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChapitreDto {

    private Long chapitreId;

    @NonNull
    private String titre;

    private String description;
    private Integer dureeEstimee;
    private boolean progressionComplete;
    private Integer ordreDansLeCours;
    private String pdfAttachment;

    private Long courId;

    private String imageData;
    private String youtubeVideoLink;
}