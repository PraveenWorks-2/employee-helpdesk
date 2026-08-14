package com.example.employee_helpdesk.repository;

import com.example.employee_helpdesk.entity.Asset;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


public interface AssetRepository extends JpaRepository<Asset,Long> {
	
	List <Asset> findByIsDeletedFalse();
	
	Optional<Asset> findByIdAndIsDeletedFalse(Long id);
}
