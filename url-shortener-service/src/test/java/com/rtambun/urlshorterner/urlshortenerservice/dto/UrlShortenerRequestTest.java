package com.rtambun.urlshorterner.urlshortenerservice.dto;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class UrlShortenerRequestTest {

    @Test
    void urlShortenerRequest_AllArgsCustomer() {
        UrlShortenerRequest actual = new UrlShortenerRequest("anyUrl");
        assertThat(actual.getLongUrl()).isEqualTo("anyUrl");
    }

    @Test
    void urlShortenerRequest_NoArgsCustomer() {
        UrlShortenerRequest actual = new UrlShortenerRequest();
        actual.setLongUrl("anyUrl");
        assertThat(actual.getLongUrl()).isEqualTo("anyUrl");
    }
}