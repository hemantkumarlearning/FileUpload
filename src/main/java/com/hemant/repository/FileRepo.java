package com.hemant.repository;

import com.hemant.entity.FileData;
import com.hemant.entity.imageData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FileRepo extends JpaRepository<FileData, Long> {
    Optional<FileData> findByName(String fileName);
}
