package com.example.jpa.controller;

import com.example.jpa.exception.ResourceNotFoundException;
import com.example.jpa.model.Lord;
import com.example.jpa.repository.LordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class LordController {

    @Autowired
    private LordRepository lordRepository;

    @GetMapping("/lords")
    public Page<Lord> getAllLords(Pageable pageable) {
        return lordRepository.findAll(pageable);
    }

    @PostMapping("/lords")
    public Lord createLord(@Valid @RequestBody Lord lord) {
        return lordRepository.save(lord);
    }

    @PutMapping("/lords/{lordId}")
    public Lord updateLord(@PathVariable Long lordId, @Valid @RequestBody Lord lordRequest) {
        return lordRepository.findById(lordId).map(lord -> {
            lord.setName(lordRequest.getName());
            lord.setAge(lordRequest.getAge());
            return lordRepository.save(lord);
        }).orElseThrow(() -> new ResourceNotFoundException("PostId " + lordId + " not found"));
    }

    @DeleteMapping("/lords/{lordId}")
    public ResponseEntity<?> deleteLord(@PathVariable Long lordId) {
        return lordRepository.findById(lordId).map(lord -> {
            lordRepository.delete(lord);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("PostId " + lordId + " not found"));
    }
}
