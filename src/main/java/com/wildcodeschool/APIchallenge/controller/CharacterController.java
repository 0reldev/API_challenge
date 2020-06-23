package com.wildcodeschool.APIchallenge.controller;

import com.wildcodeschool.APIchallenge.model.Character;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

@Controller
public class CharacterController {

    private static final String RICKANDMORTY_URL = "https://rickandmortyapi.com/api/";

    @GetMapping("/")
    public String showIndex() {

        return "index";
    }

    @GetMapping("/character")
    public String showCharacterById(Model model, @RequestParam Integer id) {

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


    @GetMapping("/character/rand")
    public String showRandomCharacter(Model model) {

        Character character = new Character();
        Integer rand = character.randomId();

        WebClient webClient = WebClient.create(RICKANDMORTY_URL);

        Mono<Character> call = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/character/{id}/")
                        .build(rand))
                .retrieve()
                .bodyToMono(Character.class);
        Character response = call.block();

        model.addAttribute("characterInfos", response);

        return "character";
    }


    @GetMapping("/character/rand6")
    public String showSixRandomCharacter(Model model) {

        Character character = new Character();
        Integer rand1 = character.randomId();
        Integer rand2 = character.randomId();
        Integer rand3 = character.randomId();
        Integer rand4 = character.randomId();
        Integer rand5 = character.randomId();
        Integer rand6 = character.randomId();

        List<Character> characters = new ArrayList();
        List<Integer> ids = new ArrayList<>();
        ids.add(rand1);
        ids.add(rand2);
        ids.add(rand3);
        ids.add(rand4);
        ids.add(rand5);
        ids.add(rand6);

        WebClient webClient = WebClient.create(RICKANDMORTY_URL);

        for (Integer id : ids) {
            Mono<Character> call = webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/character/{id}/")
                            .build(id))
                    .retrieve()
                    .bodyToMono(Character.class);
            Character response = call.block();
            characters.add(response);
        };

        model.addAttribute("characterInfos", characters);

        return "multipleCharacters";
    }
}
