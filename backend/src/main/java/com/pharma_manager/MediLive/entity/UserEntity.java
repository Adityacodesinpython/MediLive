package com.pharma_manager.MediLive.entity;

import jakarta.persistence.*;
import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @SequenceGenerator(name="my_seq", sequenceName="user_seq", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "my_seq")
    private Long userId;

    @NonNull
    @Column(name = "username")
    private String userName;

    @NonNull
    @Column(name = "password")
    private String passWord;

    @NonNull
    @Column(name = "first_name")
    private String firstName;

    @NonNull
    @Column(name = "last_name")
    private String lastName;

    @Column(name = "roles", columnDefinition = "TEXT[]")
    private String[] roles;

    @Column(name = "doctor_id")
    private Long doctorAssigned;

    @Column(name = "day_nurse_id")
    private Long dayShiftNurseAssigned;

    @Column(name = "night_nurse_id")
    private Long nightShiftNurseAssigned;
}
