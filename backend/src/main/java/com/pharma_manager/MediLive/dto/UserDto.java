package com.pharma_manager.MediLive.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long userId;

    private String userName;

    private String passWord;

    private String firstName;

    private String lastName;

    private String[] roles;

    private Long doctorAssigned;

    private Long dayShiftNurseAssigned;

    private Long nightShiftNurseAssigned;

}
