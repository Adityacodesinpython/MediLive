package com.pharma_manager.MediLive.dto;

import com.pharma_manager.MediLive.entity.UserEntity;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class DoctorDto {
    private Long doctorId;

    private String userName;

    private String passWord;

    private String department;

    private String isAvailable;

    private String inAnEmergency;

    private UserEntity userEntity;

    private String[] roles;
}
