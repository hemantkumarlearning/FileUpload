package com.hemant.repository;

import com.hemant.entity.imageData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ImageRepo extends JpaRepository<imageData, Long> {

   Optional<imageData> findByName(String name);
}
