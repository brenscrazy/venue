package com.gleb.vinnikov.social_network.posting.configs;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.file.Path;

@Configuration
public class PostingConfig {

    @Value("${application.image.saving.directory}")
    private String imageSavingDirectory;

    @Bean
    public Path imageSavingDirectory() {
        return Path.of(imageSavingDirectory);
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

}
