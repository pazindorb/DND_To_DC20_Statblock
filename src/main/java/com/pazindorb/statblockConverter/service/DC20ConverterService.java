package com.pazindorb.statblockConverter.service;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import static com.pazindorb.statblockConverter.kafka.KafkaTopicConfig.TO_DC20_CONVERTER;

@Service
@RequiredArgsConstructor
public class DC20ConverterService {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public void sendConvertMessage(String textFormatStatblock) {
        kafkaTemplate.send(TO_DC20_CONVERTER, textFormatStatblock);
    }
}
