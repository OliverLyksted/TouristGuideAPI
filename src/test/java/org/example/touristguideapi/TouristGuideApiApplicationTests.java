package org.example.touristguideapi;

import org.example.touristguideapi.controller.TouristController;
import org.example.touristguideapi.repository.TouristRepository;
import org.example.touristguideapi.service.TouristService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class TouristGuideApiApplicationTests {

    @Autowired
    private TouristController controller;

    @Autowired
    private TouristService service;
    private TouristRepository repository;


    @Test
    void contextLoads() {
        assertThat(controller).isNotNull();
        assertThat(service).isNotNull();
        assertThat(repository).isNull();
    }

}
