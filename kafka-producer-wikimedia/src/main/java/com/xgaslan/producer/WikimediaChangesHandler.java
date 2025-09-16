package com.xgaslan.producer;


import com.launchdarkly.eventsource.MessageEvent;
import com.launchdarkly.eventsource.background.BackgroundEventHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;

@Slf4j
@RequiredArgsConstructor
public class WikimediaChangesHandler implements BackgroundEventHandler {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final String topic;

    @Override
    public void onOpen() {
        log.info("[V] EventSource connection opened successfully!");
    }

    @Override
    public void onClosed() {
        log.info("[X] EventSource connection closed");
    }

    @Override
    public void onMessage(String event, MessageEvent messageEvent) {
        String data = messageEvent.getData();
        log.info("[~] Event received - Type: {}, Data length: {}", event, data.length());
        log.debug("[~] Full event data: {}", data);

        kafkaTemplate.send(topic, data)
                .thenAccept(result -> log.info("[V] Message sent to Kafka successfully"))
                .exceptionally(failure -> {
                    log.error("[X] Failed to send message to Kafka", failure);
                    return null;
                });
    }

    @Override
    public void onComment(String comment) {
        log.info("[~] Comment received: {}", comment);
    }

    @Override
    public void onError(Throwable throwable) {
        log.error("[X]EventSource error occurred", throwable);
    }
}
