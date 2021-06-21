package com.example.jpa.repository;

import com.example.jpa.model.Lord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LordRepository extends JpaRepository<Lord, Long> {

}
