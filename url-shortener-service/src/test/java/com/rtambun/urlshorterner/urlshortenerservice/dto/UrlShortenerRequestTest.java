package com.rtambun.urlshorterner.urlshortenerservice.dto;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UrlShortenerRequestTest {

    @Test
    void urlShortenerRequest_AllArgsCustomer() {
        ShortUrlRequest actual = new ShortUrlRequest("anyUrl");
        assertThat(actual.getLongUrl()).isEqualTo("anyUrl");
    }

    @Test
    void urlShortenerRequest_NoArgsCustomer() {
        ShortUrlRequest actual = new ShortUrlRequest();
        actual.setLongUrl("anyUrl");
        assertThat(actual.getLongUrl()).isEqualTo("anyUrl");
    }
}