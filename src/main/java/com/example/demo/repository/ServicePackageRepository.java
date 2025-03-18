package com.example.demo.repository;

import com.example.demo.model.ServicePackage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ServicePackageRepository extends JpaRepository<ServicePackage, Long> {
    Optional<ServicePackage> findByPackageId (Long id);
    Boolean existsByPackageId (Long id);
    Optional<ServicePackage> findByName (String name);
}
