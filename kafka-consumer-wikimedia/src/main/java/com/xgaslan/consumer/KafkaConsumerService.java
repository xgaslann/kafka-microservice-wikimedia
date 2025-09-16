package com.xgaslan.consumer;

import com.xgaslan.consumer.entity.WikimediaRecentChange;
import com.xgaslan.consumer.repository.WikimediaRecentChangeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaConsumerService {

    private final WikimediaRecentChangeRepository repository;

    @KafkaListener(
            topics = Constants.TOPIC,
            groupId = Constants.GROUP_ID
    )
    public void consume(String eventMessage) {
        log.info("Event message received -> {}", eventMessage);
        WikimediaRecentChange message = WikimediaRecentChange.builder()
                .wikiEvent(eventMessage)
                .build();
        repository.save(message);
    }
}
