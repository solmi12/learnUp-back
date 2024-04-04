    package pfe.LearnUp.Entity;

    import com.fasterxml.jackson.annotation.JsonIgnore;
    import jakarta.persistence.*;
    import lombok.AllArgsConstructor;
    import lombok.Data;
    import lombok.NoArgsConstructor;

    import java.util.List;

    @Entity
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class Formateur {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long formateurId;

        @OneToOne
        @JoinColumn(name = "user_id")
        private User user;

        @Column(nullable = false)
        private String fullName;

        @Column(nullable = false)
        private String expertise;

        @Column(nullable = false)
        private String experience;

        @Column(nullable = false)
        private String phoneNumber;

        @OneToMany(mappedBy = "formateur", cascade = CascadeType.ALL)

        @JsonIgnore
        private List<Cour> courses;

    }
