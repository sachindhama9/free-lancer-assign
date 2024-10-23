package com.tadaah.freelancer.model;


import com.tadaah.freelancer.enums.FreeLancerStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FreeLancer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String gender;

    @Enumerated(EnumType.STRING)
    private FreeLancerStatus status = FreeLancerStatus.NEW_FREELANCER;

    private LocalDateTime deletedDate;

}
