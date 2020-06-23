package com.wildcodeschool.APIchallenge.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
public class CharacterController {


    private static final String RICKANDMORTY_URL = "https://rickandmortyapi.com/api/";


    @GetMapping("/")
        public String showIndex() {

        return "/";
    }

    @GetMapping("character")
    public String showCharacter(Model model, @RequestParam Integer id) {

        WebClient webClient = WebClient.create(RICKANDMORTY_URL);

        Mono<String> call = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/character/{id}/")
                        .build(id))
                .retrieve()
                .bodyToMono(String.class);
        String response = call.block();
        ObjectMapper objectMapper = new ObjectMapper();
        Character characterObject = null;

        try {

            characterObject = objectMapper.readValue(response, Character.class);
        } catch (JsonProcessingException e) {

            e.printStackTrace();
        } model.addAttribute("characterInfos", characterObject);
        return "character";
    }
}
