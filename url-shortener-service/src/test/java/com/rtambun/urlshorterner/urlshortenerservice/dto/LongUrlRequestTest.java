package com.rtambun.urlshorterner.urlshortenerservice.dto;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class LongUrlRequestTest {

    @Test
    void constructorNoArgs() {
        LongUrlRequest longUrlRequest = new LongUrlRequest();
        longUrlRequest.setShortUrl("test");
        assertThat(longUrlRequest.getShortUrl()).isEqualTo("test");
    }

    @Test
    void constructorAllArgs() {
        LongUrlRequest longUrlRequest = new LongUrlRequest("test");
        assertThat(longUrlRequest.getShortUrl()).isEqualTo("test");
    }
}