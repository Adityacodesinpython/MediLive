package com.pharma_manager.MediLive.entity;

import jakarta.persistence.*;
import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "admins")
public class AdminEntity {

    @Id
    @SequenceGenerator(name="my_seq", sequenceName="admin_seq", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "my_seq")
    private Long adminId;

    @NonNull
    @Column(name = "username")
    private String userName;

    @NonNull
    @Column(name = "password")
    private String passWord;

    @Column(name = "roles", columnDefinition = "TEXT[]")
    private String[] roles;
}
