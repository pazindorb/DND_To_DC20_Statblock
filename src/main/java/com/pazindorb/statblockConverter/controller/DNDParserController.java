package com.pazindorb.statblockConverter.controller;

import com.pazindorb.statblockConverter.service.DNDParserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
}
