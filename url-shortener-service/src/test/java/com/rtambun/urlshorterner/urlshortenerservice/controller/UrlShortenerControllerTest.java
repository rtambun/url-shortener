package com.rtambun.urlshorterner.urlshortenerservice.controller;

import com.rtambun.urlshorterner.urlshortenerservice.dto.UrlShortenerRequest;
import com.rtambun.urlshorterner.urlshortenerservice.dto.UrlShortenerResponse;
import com.rtambun.urlshorterner.urlshortenerservice.service.Base62Encoding;
import com.rtambun.urlshorterner.urlshortenerservice.service.ShortenUrlService;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
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
        UrlShortenerResponse actual = urlShortenerController.urlShortener(urlShortenerRequest);

        verify(shortenUrlService, times(1)).shortenUrl("anyUrl");

        assertThat(actual.getShortUrl()).isEqualTo("shortUrl");
        assertThat(actual.getLongUrl()).isEqualTo("anyUrl");
    }
}