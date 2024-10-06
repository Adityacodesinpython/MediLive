package com.pharma_manager.MediLive.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "nurse")
public class NurseEntity {
    @Id
    @SequenceGenerator(name="my_seq",sequenceName="nurse_seq", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "my_seq")
    private Long nurseId;

    @NonNull
    @Column(name = "username")
    private String userName;

    @NonNull
    @Column(name = "password")
    private String passWord;

    @Column(name = "day_shift", columnDefinition = "TEXT[]")
    private String[] dayShifts;

    @Column(name = "night_shift", columnDefinition = "TEXT[]")
    private String[] nightShifts;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    @Column(name = "roles", columnDefinition = "TEXT[]")
    private String[] roles;

}
