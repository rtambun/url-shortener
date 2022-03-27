package com.rtambun.urlshorterner.urlshortenerservice.controller;

import com.rtambun.urlshorterner.urlshortenerservice.dto.UrlShortenerRequest;
import com.rtambun.urlshorterner.urlshortenerservice.dto.UrlResponse;
import com.rtambun.urlshorterner.urlshortenerservice.service.ShortenUrlService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class UrlShortenerControllerTest {

    ShortenUrlService shortenUrlService;
    UrlShortenerController urlShortenerController;

    @BeforeEach
    void setUp() {
        shortenUrlService = mock(ShortenUrlService.class);
        urlShortenerController = new UrlShortenerController(shortenUrlService);
    }

    @Test
    void urlShortener_normalRequest_Response() {
        when(shortenUrlService.shortenUrl(anyString())).thenReturn("shortUrl");

        UrlShortenerRequest urlShortenerRequest = new UrlShortenerRequest();
        urlShortenerRequest.setLongUrl("anyUrl");
        UrlResponse actual = urlShortenerController.createShortUrl(urlShortenerRequest);

        verify(shortenUrlService, times(1)).shortenUrl("anyUrl");

        assertThat(actual.getShortUrl()).isEqualTo("shortUrl");
        assertThat(actual.getLongUrl()).isEqualTo("anyUrl");
    }
}