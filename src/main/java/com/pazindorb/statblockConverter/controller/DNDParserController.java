package com.pazindorb.statblockConverter.controller;

import com.pazindorb.statblockConverter.service.DNDParserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
@RequestMapping("/parser")
public class DNDParserController {

    private final DNDParserService service;

    @PostMapping()
    @ResponseStatus(OK)
    public void sendStatblockToParser(@RequestBody String statblock) {
        service.sendParseMessage(statblock);
    }

    @CrossOrigin
    @GetMapping("/users")
    @ResponseStatus(OK)
    public List<Map<String, String>> test() {
        return List.of(
                Map.of("id", "2137", "name", "Jan Paweł", "email", "jan@pawel.com"),
                Map.of("id", "1945", "name", "Adolf Himler", "email", "adi@him.com")
        );
    }
}