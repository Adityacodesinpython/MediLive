package com.pharma_manager.MediLive.repository;

import com.pharma_manager.MediLive.entity.AdminEntity;
import com.pharma_manager.MediLive.entity.DoctorEntity;
import com.pharma_manager.MediLive.entity.NurseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NurseRepository extends JpaRepository<NurseEntity, Long> {
    NurseEntity findByUserName(String userName);

    void deleteByUserName(String userName);

}
