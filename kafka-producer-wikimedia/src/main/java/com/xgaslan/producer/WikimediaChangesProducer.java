package com.xgaslan.producer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class WikimediaChangesProducer {

    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(String message){
        log.info("Sending message='{}'", message);
        kafkaTemplate.send(Constants.WIKIMEDIA_TOPIC, message);
    }

}
