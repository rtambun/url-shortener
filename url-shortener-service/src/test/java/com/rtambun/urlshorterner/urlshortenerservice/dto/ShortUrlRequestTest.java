package com.rtambun.urlshorterner.urlshortenerservice.dto;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ShortUrlRequestTest {

    @Test
    void setLongUrl() {
        ShortUrlRequest shortUrlRequest = new ShortUrlRequest();
        assertNull(shortUrlRequest.getLongUrl());

        shortUrlRequest.setLongUrl("test long url");
        assertThat(shortUrlRequest.getLongUrl()).isEqualTo("test long url");
    }

    @Test
    void allArgsCustomer() {
        ShortUrlRequest actual = new ShortUrlRequest("anyUrl");
        assertThat(actual.getLongUrl()).isEqualTo("anyUrl");
    }

    @Test
    void noArgsCustomer() {
        ShortUrlRequest actual = new ShortUrlRequest();
        actual.setLongUrl("anyUrl");
        assertThat(actual.getLongUrl()).isEqualTo("anyUrl");
    }
}