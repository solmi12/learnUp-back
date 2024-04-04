package pfe.LearnUp.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pfe.LearnUp.Dto.ChapitreDto;
import pfe.LearnUp.Entity.Chapitre;
import pfe.LearnUp.Entity.Cour;
import pfe.LearnUp.Repository.ChapitreRepository;
import pfe.LearnUp.Repository.CourRepository;

import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChapitreService {

    @Autowired
    private ChapitreRepository chapitreRepository;

    @Autowired
    private CourRepository courRepository;

    public ChapitreDto addChapitre(ChapitreDto chapitreDto) {


        Chapitre chapitre = new Chapitre();
        mapChapitreDtoToEntity(chapitreDto, chapitre);
        chapitre = chapitreRepository.save(chapitre);
        return mapChapitreToChapitreDto(chapitre);
    }

    public List<ChapitreDto> getAllChapitres() {
        List<Chapitre> chapitres = chapitreRepository.findAll();
        return chapitres.stream()
                .map(this::mapChapitreToChapitreDto)
                .collect(Collectors.toList());
    }

    private void mapChapitreDtoToEntity(ChapitreDto chapitreDto, Chapitre chapitre) {
        chapitre.setTitre(chapitreDto.getTitre());
        chapitre.setDescription(chapitreDto.getDescription());
        chapitre.setDureeEstimee(chapitreDto.getDureeEstimee());
        chapitre.setProgressionComplete(chapitreDto.isProgressionComplete());
        chapitre.setOrdreDansLeCours(chapitreDto.getOrdreDansLeCours());
      if (chapitreDto.getPdfAttachment() !=null && !chapitreDto.getPdfAttachment().isEmpty()){
          chapitre.setPdfAttachment(Base64.getDecoder().decode(chapitreDto.getPdfAttachment()));
      }
        chapitre.setYoutubeVideoLink(chapitreDto.getYoutubeVideoLink());
        if (chapitreDto.getImageData() != null && !chapitreDto.getImageData().isEmpty()) {
            chapitre.setImage(Base64.getDecoder().decode(chapitreDto.getImageData()));
        }
        chapitre.setYoutubeVideoLink(chapitreDto.getYoutubeVideoLink());
        if (chapitreDto.getImageData() != null && !chapitreDto.getImageData().isEmpty()) {
            chapitre.setImage(Base64.getDecoder().decode(chapitreDto.getImageData()));
        }
        if (chapitreDto.getCourId() != null) {
            Cour cour = courRepository.findById(chapitreDto.getCourId()).orElse(null);
            chapitre.setCour(cour);
        }
    }

    public List<ChapitreDto> getChapitresByCourId(Long courId) {
        List<Chapitre> chapitres = chapitreRepository.findByCour_CourId(courId);
        return chapitres.stream()
                .map(this::mapChapitreToChapitreDto)
                .collect(Collectors.toList());
    }

    private ChapitreDto mapChapitreToChapitreDto(Chapitre chapitre) {
        ChapitreDto chapitreDto = new ChapitreDto();
        chapitreDto.setChapitreId(chapitre.getChapitreId());
        chapitreDto.setTitre(chapitre.getTitre());
        chapitreDto.setDescription(chapitre.getDescription());
        chapitreDto.setDureeEstimee(chapitre.getDureeEstimee());
        chapitreDto.setProgressionComplete(chapitre.isProgressionComplete());
        chapitreDto.setOrdreDansLeCours(chapitre.getOrdreDansLeCours());
      if (chapitre.getPdfAttachment() != null){
          chapitreDto.setPdfAttachment(Base64.getEncoder().encodeToString(chapitre.getPdfAttachment()));
      }
        chapitreDto.setYoutubeVideoLink(chapitre.getYoutubeVideoLink());
        if (chapitre.getImage() != null) {
            chapitreDto.setImageData(Base64.getEncoder().encodeToString(chapitre.getImage()));
        }
        Cour cour = chapitre.getCour();
        if (cour != null) {
            chapitreDto.setCourId(cour.getCourId());
        }
        return chapitreDto;
    }

}
