package com.pharma_manager.MediLive.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminDto {
    private Long adminId;

    private String userName;

    private String passWord;

    private String[] roles;
}
