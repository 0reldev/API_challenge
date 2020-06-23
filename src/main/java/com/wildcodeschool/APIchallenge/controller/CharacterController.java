package com.wildcodeschool.APIchallenge.controller;

import com.wildcodeschool.APIchallenge.model.Character;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Controller
public class CharacterController {

    private static final String RICKANDMORTY_URL = "https://rickandmortyapi.com/api/";

    @GetMapping("/")
    public String showIndex() {

        return "index";
    }

    @GetMapping("/character")
    public String showCharacter(Model model, @RequestParam Integer id) {

        WebClient webClient = WebClient.create(RICKANDMORTY_URL);

        Mono<Character> call = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/character/{id}/")
                        .build(id))
                .retrieve()
                .bodyToMono(Character.class);
        Character response = call.block();

        model.addAttribute("characterInfos", response);

        return "character";
    }
}
