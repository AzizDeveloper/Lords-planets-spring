package com.example.jpa.repository;

import com.example.jpa.model.Planet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlanetRepository extends JpaRepository<Planet, Long> {
    Page<Planet> findByLordId(Long lordId, Pageable pageable);
    Optional<Planet> findByIdAndLordId(Long id, Long lordId);
}
