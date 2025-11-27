package com.example.weatherapp.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import com.example.weatherapp.dto.WeatherDTO;
import com.example.weatherapp.service.WeatherService;
import com.example.weatherapp.repository.FavoriteLocationRepository;
import com.example.weatherapp.entity.FavoriteLocation; // ✅ FIXED import

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class WeatherController {
    private final WeatherService weatherService;
    private final FavoriteLocationRepository repo;

    public WeatherController(WeatherService weatherService, FavoriteLocationRepository repo) {
        this.weatherService = weatherService;
        this.repo = repo;
    }


    @PostMapping("/locations")
    public ResponseEntity<FavoriteLocation> saveLocation(@RequestBody FavoriteLocation loc) {
        return ResponseEntity.ok(repo.save(loc));
    }

    @GetMapping("/locations")
    public ResponseEntity<List<FavoriteLocation>> getLocations() {
        return ResponseEntity.ok(repo.findAll());
    }
}
