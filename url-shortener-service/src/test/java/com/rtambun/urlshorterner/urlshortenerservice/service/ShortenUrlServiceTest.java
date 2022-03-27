package com.rtambun.urlshorterner.urlshortenerservice.service;

import com.rtambun.urlshorterner.urlshortenerservice.model.UrlPair;
import com.rtambun.urlshorterner.urlshortenerservice.repository.UrlPairRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import javax.persistence.EntityNotFoundException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class ShortenUrlServiceTest {

    Base62Encoding base62Encoding;
    UrlPairRepository urlPairRepository;
    ShortenUrlService shortenUrlService;

    @BeforeEach
    void setUp() {
        base62Encoding = mock(Base62Encoding.class);
        urlPairRepository = mock(UrlPairRepository.class);
        shortenUrlService = new ShortenUrlService(base62Encoding, urlPairRepository);
    }

    @Test
    void shortenUrl_NormalExecution() {
        when(base62Encoding.encodeNumber(anyLong())).thenReturn("shortUrl");

        String actual = shortenUrlService.shortenUrl("longUrl");
        assertThat(actual).isEqualTo("shortUrl");

        verify(base62Encoding, times(1)).encodeNumber(0);
        ArgumentCaptor<UrlPair> captorUrlPair = ArgumentCaptor.forClass(UrlPair.class);
        verify(urlPairRepository, times(1)).save(captorUrlPair.capture());

        UrlPair actualUrlPair = captorUrlPair.getValue();
        UrlPair expectedUrlPair = new UrlPair(null, "shortUrl", "longUrl");
        assertThat(actualUrlPair).usingRecursiveComparison().isEqualTo(expectedUrlPair);
    }

    @Test
    void retrieveLongUrl_NormalExecution() {
        when(urlPairRepository.findByShortUrl(anyString())).thenReturn(new UrlPair(null,
                "shortUrl",
                "longUrl"));

        String longUrl = shortenUrlService.retrieveLongUrl("shortUrl");
        assertThat(longUrl).isEqualTo("longUrl");
    }

    @Test
    void retrieveLongUrl_ShortUrlNotFound_ThrowEntityNotFoundException() {
        when(urlPairRepository.findByShortUrl(anyString())).thenReturn(null);

        assertThrows(EntityNotFoundException.class,() -> shortenUrlService.retrieveLongUrl("shortUrl"));
    }
}