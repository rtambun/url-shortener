package com.rtambun.urlshorterner.urlshortenerservice.service.client;

import com.rtambun.urlshorterner.urlshortenerservice.dto.CounterRangeResponse;
import com.rtambun.urlshorterner.urlshortenerservice.model.CounterRange;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.w3c.dom.css.Counter;
import reactor.core.publisher.Mono;

import java.util.function.Predicate;

@Service
@Log4j2
public class CounterRangeServerClient {

    private String counterRangeServerUrl;
    private WebClient webClient;

    public CounterRangeServerClient(@Value("{counter_range.url}") String counterRangeServerUrl,
                                    ModelMapper modelMapper) {
        this.counterRangeServerUrl = counterRangeServerUrl;
        webClient = WebClient.create(counterRangeServerUrl);
    }

    public Mono<CounterRangeResponse> getCounterRange() {
        return webClient
                .get()
                .uri("/counter")
                .retrieve()
                .onStatus(HttpStatus::isError, status -> {
                    if(status.statusCode().is4xxClientError()) {
                        log.error("Response from " + counterRangeServerUrl + "is 4xx");
                    } else {
                        log.error("Response from " + counterRangeServerUrl + "is 5xx");
                    }
                    return Mono.error(new ClientException(ClientException.SERVICE_RESPONSE_STATUS_NON_2XX));
                })
                .bodyToMono(CounterRangeResponse.class)
                .onErrorMap(Predicate.not(ClientException.class::isInstance), throwable -> {
                    log.error("Failed to send request to service", throwable);
                    return new ClientException(ClientException.SERVICE_REQUEST_FAILED);
                });
    }
}
