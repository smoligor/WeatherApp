package com.example.weatherapp.service.impl;

import com.example.weatherapp.dto.GeoDto;
import com.example.weatherapp.exception.LocationNotFoundException;
import com.example.weatherapp.service.GeoService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class GeoServiceImpl implements GeoService {

    private final WebClient webClient;

    @Value("${weather.api.nominatim-url}")
    private String nominatimUrl;

    @Value("${weather.api.user-agent}")
    private String userAgent;

    public GeoServiceImpl(WebClient webClient) {
        this.webClient = webClient;
    }

    @Override
    public Mono<GeoDto> getGeo(String location) {
        return webClient.get()
                .uri(nominatimUrl, uriBuilder -> uriBuilder
                        .queryParam("q", location)
                        .queryParam("format", "json")
                        .queryParam("limit", 1)
                        .build())
                .header("User-Agent", userAgent)
                .retrieve()
                .bodyToMono(GeoDto[].class)
                .flatMap(geoArray -> {
                    if (geoArray == null || geoArray.length == 0) {
                        return Mono.error(new LocationNotFoundException("City not found: " + location));
                    }
                    return Mono.just(geoArray[0]);
                });
    }
}

