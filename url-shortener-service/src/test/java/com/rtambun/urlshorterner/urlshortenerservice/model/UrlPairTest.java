package com.rtambun.urlshorterner.urlshortenerservice.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class UrlPairTest {

    @Test
    void testToString() {
        UrlPair urlPair = new UrlPair("test", "shortUrl", "longUrl");
        assertThat(urlPair.toString()).isEqualTo("UrlPair{id='test', shortUrl='shortUrl', longUrl='longUrl'}");
    }

    @Test
    void getId() {
        UrlPair urlPair = new UrlPair();
        urlPair.setId("test");
        assertThat(urlPair.getId()).isEqualTo("test");
    }

    @Test
    void getShortUrl() {
    }

    @Test
    void getLongUrl() {
    }

    @Test
    void setId() {
    }

    @Test
    void setShortUrl() {
    }

    @Test
    void setLongUrl() {
    }
}