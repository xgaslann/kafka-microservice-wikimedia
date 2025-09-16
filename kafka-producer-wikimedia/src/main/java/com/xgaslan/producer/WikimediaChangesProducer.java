package com.xgaslan.producer;

import com.launchdarkly.eventsource.ConnectStrategy;
import com.launchdarkly.eventsource.EventSource;
import com.launchdarkly.eventsource.background.BackgroundEventSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Headers;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class WikimediaChangesProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage() {
        log.info("[V] Starting Wikimedia EventSource connection...");

        WikimediaChangesHandler eventHandler = new WikimediaChangesHandler(kafkaTemplate, Constants.WIKIMEDIA_TOPIC);

        Headers headers = new Headers.Builder()
                .add("User-Agent", "WikimediaKafkaProducer/1.0 (gorkemaslan73@gmail.com)")
                .add("Accept", "text/event-stream")
                .add("Cache-Control", "no-cache")
                .build();

        try (BackgroundEventSource eventSource = new BackgroundEventSource.Builder(
                eventHandler,
                new EventSource.Builder(
                        ConnectStrategy.http(URI.create(Constants.WIKIMEDIA_EVENT_SOURCE_URL))
                                .headers(headers)
                                .connectTimeout(30, TimeUnit.SECONDS)
                )
                        .retryDelay(5, TimeUnit.SECONDS)
        ).build()) {

            log.info("[...] Attempting to connect to: {}", Constants.WIKIMEDIA_EVENT_SOURCE_URL);
            eventSource.start();
            log.info("[*] EventSource started, waiting for events for 10 minutes...");

            TimeUnit.MINUTES.sleep(10);

        } catch (InterruptedException e) {
            log.error("EventSource interrupted", e);
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            log.error("Error with EventSource", e);
        }

        log.info("[X] EventSource connection finished");
    }
}