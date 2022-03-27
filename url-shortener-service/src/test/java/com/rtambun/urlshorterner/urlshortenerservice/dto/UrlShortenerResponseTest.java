package com.rtambun.urlshorterner.urlshortenerservice.dto;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UrlShortenerResponseTest {

    @Test
    void urlShortenerResponse_AllArgsConstructor() {
        UrlResponse actual = new UrlResponse("shortUrl", "anyUrl");
        assertThat(actual.getShortUrl()).isEqualTo("shortUrl");
        assertThat(actual.getLongUrl()).isEqualTo("anyUrl");
    }

    @Test
    void urlShortenerResponse_NoArgsConstructor() {
        UrlResponse actual = new UrlResponse();
        actual.setShortUrl("shortUrl");
        actual.setLongUrl("anyUrl");
        assertThat(actual.getShortUrl()).isEqualTo("shortUrl");
        assertThat(actual.getLongUrl()).isEqualTo("anyUrl");
    }

}