package pfe.LearnUp.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pfe.LearnUp.Dto.ApprenantCourDto;
import pfe.LearnUp.Entity.Apprenant;
import pfe.LearnUp.Entity.ApprenantCour;
import pfe.LearnUp.Entity.Cour;
import pfe.LearnUp.Repository.ApprenantCourRepository;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ApprenantCourService {

    @Autowired
    private ApprenantCourRepository apprenantCourRepository;

    public ApprenantCourDto addApprenantCour(ApprenantCourDto dto) {
        ApprenantCour apprenantCour = convertToEntity(dto);
        apprenantCour.setAddedDate(new Date());
        apprenantCour = apprenantCourRepository.save(apprenantCour);
        return convertToDto(apprenantCour);
    }

    public void deleteApprenantCour(Long apprenantCourId) {
        apprenantCourRepository.deleteById(apprenantCourId);
    }

    public ApprenantCourDto convertToDto(ApprenantCour apprenantCour) {
        ApprenantCourDto dto = new ApprenantCourDto();
        dto.setApprenantCourid(apprenantCour.getApprenantCourid());
        dto.setApprenantId(apprenantCour.getApprenant().getApprenantId());
        dto.setCourId(apprenantCour.getCour().getCourId());
        dto.setAddedDate(apprenantCour.getAddedDate());
        return dto;
    }

    public ApprenantCour convertToEntity(ApprenantCourDto dto) {
        ApprenantCour apprenantCour = new ApprenantCour();
        apprenantCour.setApprenantCourid(dto.getApprenantCourid());
        Apprenant apprenant = new Apprenant();
        apprenant.setApprenantId(dto.getApprenantId());
        apprenantCour.setApprenant(apprenant);

        Cour cour = new Cour();
        cour.setCourId(dto.getCourId());
        apprenantCour.setCour(cour);

        apprenantCour.setAddedDate(dto.getAddedDate());
        return apprenantCour;
    }

    public List<Cour> getCoursByApprenantId(Long apprenantId) {
        List<ApprenantCour> apprenantCourList = apprenantCourRepository.findByApprenant_ApprenantId(apprenantId);
        return apprenantCourList.stream()
                .map(ApprenantCour::getCour)
                .collect(Collectors.toList());
    }

    public List<Apprenant> getApprenantsByCourId(Long courId) {
        List<ApprenantCour> apprenantCourList = apprenantCourRepository.findByCour_CourId(courId);
        return apprenantCourList.stream()
                .map(ApprenantCour::getApprenant)
                .collect(Collectors.toList());
    }
}
