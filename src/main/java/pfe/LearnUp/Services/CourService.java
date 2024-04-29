    package pfe.LearnUp.Services;


    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.stereotype.Service;
    import pfe.LearnUp.Dto.CategoryDto;
    import pfe.LearnUp.Dto.CourDto;
    import pfe.LearnUp.Entity.Category;
    import pfe.LearnUp.Entity.Cour;
    import pfe.LearnUp.Entity.Formateur;
    import pfe.LearnUp.Repository.CategoryRepository;
    import pfe.LearnUp.Repository.CourRepository;
    import pfe.LearnUp.Repository.FormateurRepository;

    import java.io.IOException;
    import java.util.Base64;
    import java.util.List;
    import java.util.Optional;
    import java.util.stream.Collectors;

    @Service
    public class CourService {

        @Autowired
        private CourRepository courRepository;

        @Autowired
        private CategoryRepository categoryRepository;

        @Autowired
        private FormateurRepository formateurRepository;

        @Autowired
        private FormateurService formateurService;
        public CourDto addCour(CourDto courDto) throws IOException {
            Cour newCour = new Cour();
            newCour.setCourName(courDto.getCourName());
            newCour.setDescription(courDto.getDescription());
            newCour.setPrice(courDto.getPrice());

            newCour.setNeedsReview(true);
            newCour.setStatus("pending");
            if (courDto.getImageData() != null && !courDto.getImageData().isEmpty()) {
                newCour.setImage(Base64.getDecoder().decode(courDto.getImageData()));
            }

            if (courDto.getCategory() != null) {
                Category category = getCategoryFromDTO(courDto.getCategory());
                newCour.setCategory(category);
            }

            if (courDto.getFormateurId() != null) {
                Formateur formateur = formateurRepository.findById(courDto.getFormateurId()).orElse(null);
                if (formateur != null) {
                    newCour.setFormateur(formateur);
                } else {
                }
            }

            Cour savedCour = courRepository.save(newCour);

            return convertToDTO(savedCour);
        }

        public CourDto acceptCour(Long courId) {
            Optional<Cour> optionalCour = courRepository.findById(courId);
            if (optionalCour.isPresent()) {
                Cour cour = optionalCour.get();
                cour.setStatus("accepted");

                cour.setNeedsReview(false);
                Cour updatedCour = courRepository.save(cour);
                return convertToDTO(updatedCour);
            } else {
                return null;
            }
        }




        public CourDto refuseCour(Long courId) {
            Optional<Cour> optionalCour = courRepository.findById(courId);
            if (optionalCour.isPresent()) {
                Cour cour = optionalCour.get();
                cour.setStatus("refused");
                cour.setNeedsReview(false);
                Cour updatedCour = courRepository.save(cour);
                return convertToDTO(updatedCour);
            } else {
                return null;
            }
        }



        public List<CourDto> getCoursByStatus(String status) {
            List<Cour> cours = courRepository.findByStatus(status);
            return cours.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        }

        public List<CourDto> getCoursByFormateurId(Long formateurId) {
                List<Cour> coursList = courRepository.findByFormateurFormateurId(formateurId);
                return coursList.stream()
                        .map(this::convertToDTO)
                        .collect(Collectors.toList());
            }


        private final Object notificationLock = new Object();

        public List<CourDto> getNewCoursForFormateurNotification() {
            List<CourDto> newCours = fetchNewCours();
            updateCoursNeedsReview(newCours);

            return newCours;
        }
        private List<CourDto> fetchNewCours() {
            List<Cour> fetchedCours = courRepository.findByNeedsReviewFalse();

            return fetchedCours.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        }
        private void updateCoursNeedsReview(List<CourDto> newCours) {
            newCours.forEach(courDto -> {
                Optional<Cour> optionalCour = courRepository.findById(courDto.getCourId());
                optionalCour.ifPresent(cour -> {
                    synchronized (notificationLock) {
                        if (cour.getNeedsReview() != null) {
                            cour.setNeedsReview(null);
                            courRepository.save(cour);
                        }
                    }
                });
            });
        }


        public List<CourDto> getAllCours() {
            List<Cour> cours = courRepository.findAll();
            return cours.stream().map(this::convertToDTO).collect(Collectors.toList());
        }
        private Category getCategoryFromDTO(CategoryDto categoryDTO) {
            if (categoryDTO.getCategoryId() != null) {
                return categoryRepository.findById(categoryDTO.getCategoryId()).orElse(null);
            } else {
                Category newCategory = new Category();
                newCategory.setCategoryName(categoryDTO.getCategoryName());
                return categoryRepository.save(newCategory);
            }
        }


        public List<CourDto> searchCourByName(String courName) {
            List<Cour> cours;

            if (courName != null && !courName.isEmpty()) {
                cours = courRepository.findByCourNameContaining(courName);
            } else {
                cours = courRepository.findAll();
            }

            return cours.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        }



        public List<CourDto> getCoursByCategory(Long categoryId) {
            List<Cour> cours = courRepository.findByCategoryCategoryId(categoryId);
            return cours.stream().map(this::convertToDTO).collect(Collectors.toList());
        }

        public List<CourDto> searchCoursByCategory(String categoryName) {
            List<Cour> cours;

            if (categoryName != null && !categoryName.isEmpty()) {
                cours = courRepository.findByCategoryCategoryName(categoryName);
            } else {
                cours =courRepository.findAll();
            }

            return cours.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        }

        public List<CourDto> getNewCoursForAdminNotification() {
            return courRepository.findByNeedsReviewTrue().stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        }


        private CourDto convertToDTO(Cour cour) {
            CourDto courDTO = new CourDto();
            courDTO.setCourId(cour.getCourId());
            courDTO.setCourName(cour.getCourName());
            courDTO.setDescription(cour.getDescription());
            courDTO.setStatus(cour.getStatus());
            courDTO.setNeedsReview(cour.getNeedsReview());
            if (cour.getCategory() != null) {
                CategoryDto categoryDto= convertToCategoryDTO(cour.getCategory());
                courDTO.setCategory(categoryDto);
            }



            if (cour.getImage() != null) {
                courDTO.setImageData(Base64.getEncoder().encodeToString(cour.getImage()));
            }

            courDTO.setPrice(cour.getPrice());
            courDTO.setDiscountedPrice(cour.getDiscountedPrice());
            courDTO.setTotalPrice(cour.getTotalPrice());
            Formateur formateur = cour.getFormateur();
            if (formateur != null) {
                courDTO.setFormateurId(formateur.getFormateurId());
            }
            return courDTO;
        }

        private CategoryDto convertToCategoryDTO(Category category) {
            CategoryDto categoryDTO = new CategoryDto();
            categoryDTO.setCategoryId(category.getCategoryId());
            categoryDTO.setCategoryName(category.getCategoryName());


            return categoryDTO;
        }


        public boolean deleteCour(Long courId) {
            Optional<Cour> existingCour = courRepository.findById(courId);
            if (existingCour.isPresent()) {
                courRepository.deleteById(courId);
                return true;
            } else {
                return false;
            }
        }


        public CourDto getCourById(Long courId) {
            Optional<Cour> optionalCour = courRepository.findById(courId);
            if (optionalCour.isPresent()) {
                Cour cour = optionalCour.get();
                return convertToDTO(cour);
            } else {
                return null;
            }
        }

    }
