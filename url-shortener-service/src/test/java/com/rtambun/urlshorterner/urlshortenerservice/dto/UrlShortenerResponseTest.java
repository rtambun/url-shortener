package com.rtambun.urlshorterner.urlshortenerservice.dto;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class UrlShortenerResponseTest {

    @Test
    void urlShortenerResponse_AllArgsConstructor() {
        UrlShortenerResponse actual = new UrlShortenerResponse("shortUrl", "anyUrl");
        assertThat(actual.getShortUrl()).isEqualTo("shortUrl");
        assertThat(actual.getLongUrl()).isEqualTo("anyUrl");
    }

    @Test
    void urlShortenerResponse_NoArgsConstructor() {
        UrlShortenerResponse actual = new UrlShortenerResponse();
        actual.setShortUrl("shortUrl");
        actual.setLongUrl("anyUrl");
        assertThat(actual.getShortUrl()).isEqualTo("shortUrl");
        assertThat(actual.getLongUrl()).isEqualTo("anyUrl");
    }

}