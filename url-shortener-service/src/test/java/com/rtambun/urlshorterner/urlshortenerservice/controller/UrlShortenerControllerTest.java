package com.rtambun.urlshorterner.urlshortenerservice.controller;

import com.rtambun.urlshorterner.urlshortenerservice.dto.LongUrlRequest;
import com.rtambun.urlshorterner.urlshortenerservice.dto.UrlShortenerRequest;
import com.rtambun.urlshorterner.urlshortenerservice.dto.UrlResponse;
import com.rtambun.urlshorterner.urlshortenerservice.service.ShortenUrlService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityNotFoundException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
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

    @Test
    void retrieveLongUrl_normalRequest_Response() {
        when(shortenUrlService.retrieveLongUrl(anyString())).thenReturn("longUrl");

        LongUrlRequest request = new LongUrlRequest("shortUrl");
        UrlResponse actual = urlShortenerController.retrieveLongUrl(request);

        verify(shortenUrlService, times(1)).retrieveLongUrl("shortUrl");

        UrlResponse expected = new UrlResponse("shortUrl", "longUrl");
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void retrieveLongUrl_retrieveLongUrlThrowEntityNotFoundException_ThrowHttpNotFoundException() {
        when(shortenUrlService.retrieveLongUrl(anyString())).thenThrow(new EntityNotFoundException());

        LongUrlRequest request = new LongUrlRequest("shortUrl");
        assertThrows(ResponseStatusException.class,() -> urlShortenerController.retrieveLongUrl(request));

        verify(shortenUrlService, times(1)).retrieveLongUrl("shortUrl");
    }
}