package com.example.jpa.controller;

import com.example.jpa.exception.ResourceNotFoundException;
import com.example.jpa.model.Planet;
import com.example.jpa.repository.PlanetRepository;
import com.example.jpa.repository.LordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class PlanetController {

    @Autowired
    private PlanetRepository planetRepository;

    @Autowired
    private LordRepository lordRepository;

    @GetMapping("/lords/{lordId}/planets")
    public Page<Planet> getAllPlanetsByLordId(@PathVariable (value = "lordId") Long lordId,
                                              Pageable pageable) {
        return planetRepository.findByLordId(lordId, pageable);
    }

    @PostMapping("/lords/{lordId}/planets")
    public Planet createPlanet(@PathVariable (value = "lordId") Long lordId,
                                @Valid @RequestBody Planet planet) {
        return lordRepository.findById(lordId).map(lord -> {
            planet.setLord(lord);
            return planetRepository.save(planet);
        }).orElseThrow(() -> new ResourceNotFoundException("PostId " + lordId + " not found"));
    }

    @PutMapping("/lords/{lordId}/planets/{planetId}")
    public Planet updatePlanet(@PathVariable (value = "lordId") Long lordId,
                                @PathVariable (value = "planetId") Long planetId,
                                @Valid @RequestBody Planet planetRequest) {
        if(!lordRepository.existsById(lordId)) {
            throw new ResourceNotFoundException("PostId " + lordId + " not found");
        }

        return planetRepository.findById(planetId).map(planet -> {
            planet.setName(planetRequest.getName());
            return planetRepository.save(planet);
        }).orElseThrow(() -> new ResourceNotFoundException("CommentId " + planetId + "not found"));
    }


    @DeleteMapping("/lords/{lordId}/planets/{planetId}")
    public ResponseEntity<?> deletePlanet(@PathVariable (value = "lordId") Long lordId,
                              @PathVariable (value = "planetId") Long planetId) {
        return planetRepository.findByIdAndLordId(planetId, lordId).map(planet -> {
            planetRepository.delete(planet);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("Planet not found with id " + planetId + " and postId " + lordId));
    }
}
