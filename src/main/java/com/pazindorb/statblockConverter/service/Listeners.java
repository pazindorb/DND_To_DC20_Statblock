package com.pazindorb.statblockConverter.service;

import com.pazindorb.statblockConverter.model.statblock.DNDStatblock;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import static com.pazindorb.statblockConverter.kafka.KafkaConsumerConfig.DND_PARSER_GROUP;
import static com.pazindorb.statblockConverter.kafka.KafkaConsumerConfig.TO_DC20_CONVERTER_GROUP;
import static com.pazindorb.statblockConverter.kafka.KafkaTopicConfig.DND_PARSER;
import static com.pazindorb.statblockConverter.kafka.KafkaTopicConfig.TO_DC20_CONVERTER;

@Component
@RequiredArgsConstructor
public class Listeners {

    private final DNDParserService parserService;
    private final DC20ConverterService converterService;

    @KafkaListener(topics = DND_PARSER, groupId = DND_PARSER_GROUP)
    public void listenDNDParserGroup(String textStatblock) {
        DNDStatblock statblock = parserService.parse(textStatblock);
    }

    @KafkaListener(topics = TO_DC20_CONVERTER, groupId = TO_DC20_CONVERTER_GROUP)
    public void listenDC20ConverterGroup(String message) {
        System.out.println("Received Message in group foo: " + message);
    }
}
