package com.pharma_manager.MediLive.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "doctor")
public class DoctorEntity {

    @Id
    @SequenceGenerator(name="my_seq",sequenceName="doctor_seq", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "my_seq")
    private Long doctorId;

    @NonNull
    @Column(name = "username")
    private String userName;

//    @NonNull
    @Column(name = "password")
    private String passWord;

    @Column(name = "department")
    private String department;

    @Column(name = "isAvailable")
    private String isAvailable;

    @Column(name = "inAnEmergency")
    private String inAnEmergency;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    @Column(name = "roles", columnDefinition = "TEXT[]")
    private String[] roles;
}
