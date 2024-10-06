package com.pharma_manager.MediLive.dto;

import com.pharma_manager.MediLive.entity.UserEntity;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NurseDto {
    private Long nurseId;

    private String userName;

    private String passWord;

    private String[] dayShifts;

    private String[] nightShifts;

    private UserEntity userEntity;

    private String[] roles;
}
