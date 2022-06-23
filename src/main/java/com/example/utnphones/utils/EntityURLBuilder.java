package com.example.utnphones.utils;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

public class EntityURLBuilder {
    public static String buildURL(final String entity, final String id) {

        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/{entity}/{id}")
                .buildAndExpand(entity, id)
                .toUriString();
    }
}
