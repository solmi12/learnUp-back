    package pfe.LearnUp.Services;

    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.stereotype.Service;
    import pfe.LearnUp.Dto.ApprenantCourDto;
    import pfe.LearnUp.Dto.SouhaitsDto;
    import pfe.LearnUp.Entity.Apprenant;
    import pfe.LearnUp.Entity.ApprenantCour;
    import pfe.LearnUp.Entity.Cour;
    import pfe.LearnUp.Entity.Souhaits;
    import pfe.LearnUp.Repository.SouhaitsRepository;

    import java.util.Date;
    import java.util.List;
    import java.util.stream.Collectors;

    @Service
    public class SouhaitsService {

        @Autowired
        private SouhaitsRepository souhaitsRepository;




        public SouhaitsDto addSouhait(SouhaitsDto dto) {
            if (souhaitsRepository.existsByApprenant_ApprenantIdAndCour_CourId(dto.getApprenantId(), dto.getCourId())) {

                throw new IllegalArgumentException("Course is already in the list."); // You can customize the error message as needed
            } else {
                Souhaits souhaits = convertToEntity(dto);
                souhaits = souhaitsRepository.save(souhaits);
                return convertToDto(souhaits



                );
            }
        }


        public List<Cour> getCoursByApprenantId(Long apprenantId) {
            List<Souhaits> souhaitsList = souhaitsRepository.findByApprenant_ApprenantId(apprenantId);
            return souhaitsList.stream()
                    .map(Souhaits::getCour)
                    .collect(Collectors.toList());
        }

        public void deleteSouhaits(Long souhaitId) {
            souhaitsRepository.deleteById(souhaitId);
        }

        public List<SouhaitsDto> getApprenantCourDtosByApprenantId(Long apprenantId) {
            List<Souhaits> souhaitsList = souhaitsRepository.findByApprenant_ApprenantId(apprenantId);
            return souhaitsList.stream()
                    .map(this::convertToDto)
                    .collect(Collectors.toList());
        }


        public SouhaitsDto convertToDto(Souhaits souhaits) {
            SouhaitsDto dto = new SouhaitsDto();
            dto.setSouhaitId(souhaits.getSouhaitId());
            dto.setApprenantId(souhaits.getApprenant().getApprenantId());
            dto.setCourId(souhaits.getCour().getCourId());
            return dto;
        }

        public Souhaits convertToEntity(SouhaitsDto dto) {
            Souhaits souhaits = new Souhaits();
            souhaits.setSouhaitId(dto.getSouhaitId());
            Apprenant apprenant = new Apprenant();
            apprenant.setApprenantId(dto.getApprenantId());
            souhaits.setApprenant(apprenant);

            Cour cour = new Cour();
            cour.setCourId(dto.getCourId());
            souhaits.setCour(cour);

            return souhaits;
        }


    }
