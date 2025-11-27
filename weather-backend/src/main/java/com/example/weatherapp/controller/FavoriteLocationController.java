package com.example.weatherapp.controller;

import com.example.weatherapp.entity.FavoriteLocation;
import com.example.weatherapp.repository.FavoriteLocationRepository;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/favorites")   // ✅ FIXED HERE
@CrossOrigin(origins = "http://localhost:5173")
public class FavoriteLocationController {

    private final FavoriteLocationRepository repository;

    public FavoriteLocationController(FavoriteLocationRepository repository) {
        this.repository = repository;
    }

    // CREATE
    @PostMapping
    public FavoriteLocation addFavorite(@RequestBody FavoriteLocation location) {
        return repository.save(location);
    }

    // READ ALL
    @GetMapping
    public List<FavoriteLocation> getAllFavorites() {
        return repository.findAll();
    }

    // READ BY ID
    @GetMapping("/{id}")
    public ResponseEntity<FavoriteLocation> getFavoriteById(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<FavoriteLocation> updateFavorite(
            @PathVariable Long id, @RequestBody FavoriteLocation updatedLocation) {

        return repository.findById(id)
                .map(fav -> {
                    fav.setName(updatedLocation.getName());
                    fav.setCity(updatedLocation.getCity());
                    return ResponseEntity.ok(repository.save(fav));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFavorite(@PathVariable Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
