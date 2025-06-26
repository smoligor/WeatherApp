package com.example.weatherapp.service;

import com.example.weatherapp.dto.GeoDto;
import reactor.core.publisher.Mono;

public interface GeoService {
    Mono<GeoDto> getGeo(String location);
}

