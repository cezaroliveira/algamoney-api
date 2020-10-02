package com.example.algamoney.api.config;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.algamoney.api.config.property.AlgamoneyApiProperty;

@Component
public class StarterConfig {

    @Autowired
    private AlgamoneyApiProperty algamoneyApiProperty;

    @PostConstruct
    private void startUp() {
        System.out.println("\n ====== Ambiente: " + algamoneyApiProperty.getEnvironment() + " =========\n");
    }

}
