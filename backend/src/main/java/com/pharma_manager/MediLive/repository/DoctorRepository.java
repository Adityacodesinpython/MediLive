package com.pharma_manager.MediLive.repository;

import com.pharma_manager.MediLive.dto.DoctorDto;
import com.pharma_manager.MediLive.dto.NurseDto;
import com.pharma_manager.MediLive.entity.DoctorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepository extends JpaRepository<DoctorEntity, Long> {

    DoctorEntity findByUserName(String userName);

    void deleteByUserName(String userName);
}
