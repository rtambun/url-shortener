package com.rtambun.urlshorterner.urlshortenerservice.service;

import com.rtambun.urlshorterner.urlshortenerservice.dto.CounterRangeResponse;
import com.rtambun.urlshorterner.urlshortenerservice.model.UrlPair;
import com.rtambun.urlshorterner.urlshortenerservice.repository.UrlPairRepository;
import com.rtambun.urlshorterner.urlshortenerservice.service.client.ClientException;
import com.rtambun.urlshorterner.urlshortenerservice.service.client.CounterRangeServerClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.modelmapper.ModelMapper;
import reactor.core.publisher.Mono;

import javax.persistence.EntityNotFoundException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class ShortenUrlServiceTest {

    Base62Encoding base62Encoding;
    UrlPairRepository urlPairRepository;
    ShortenUrlService shortenUrlService;
    CounterRangeServerClient counterRangeServerClient;
    ModelMapper modelMapper;

    @BeforeEach
    void setUp() {
        base62Encoding = mock(Base62Encoding.class);
        urlPairRepository = mock(UrlPairRepository.class);
        counterRangeServerClient = mock(CounterRangeServerClient.class);
        modelMapper = new ModelMapper();
        shortenUrlService = new ShortenUrlService(base62Encoding, urlPairRepository, counterRangeServerClient,
                modelMapper);
    }

    @Test
    void shortenUrl_NormalExecution() {
        when(counterRangeServerClient.getCounterRange()).thenReturn(Mono.just(new CounterRangeResponse(0, 2)));

        when(base62Encoding.encodeNumber(anyLong())).thenReturn("shortUrl");

        String actual = shortenUrlService.shortenUrl("longUrl");
        assertThat(actual).isEqualTo("shortUrl");

        verify(counterRangeServerClient, times(1)).getCounterRange();

        verify(base62Encoding, times(1)).encodeNumber(0);
        ArgumentCaptor<UrlPair> captorUrlPair = ArgumentCaptor.forClass(UrlPair.class);
        verify(urlPairRepository, times(1)).save(captorUrlPair.capture());

        UrlPair actualUrlPair = captorUrlPair.getValue();
        UrlPair expectedUrlPair = new UrlPair(null, "shortUrl", "longUrl");
        assertThat(actualUrlPair).usingRecursiveComparison().isEqualTo(expectedUrlPair);

        actual = shortenUrlService.shortenUrl("longUrl1");
        assertThat(actual).isEqualTo("shortUrl");

        verify(counterRangeServerClient, times(1)).getCounterRange();

        verify(base62Encoding, times(1)).encodeNumber(1);
        captorUrlPair = ArgumentCaptor.forClass(UrlPair.class);
        verify(urlPairRepository, times(2)).save(captorUrlPair.capture());

        actualUrlPair = captorUrlPair.getValue();
        expectedUrlPair = new UrlPair(null, "shortUrl", "longUrl1");
        assertThat(actualUrlPair).usingRecursiveComparison().isEqualTo(expectedUrlPair);
    }

    @Test
    void shortenUrl_CounterExceed() {
        when(counterRangeServerClient.getCounterRange()).thenReturn(Mono.just(new CounterRangeResponse(0, 0)));

        when(base62Encoding.encodeNumber(anyLong())).thenReturn("shortUrl");

        String actual = shortenUrlService.shortenUrl("longUrl");
        assertThat(actual).isEqualTo("shortUrl");

        verify(counterRangeServerClient, times(1)).getCounterRange();

        verify(base62Encoding, times(1)).encodeNumber(0);
        ArgumentCaptor<UrlPair> captorUrlPair = ArgumentCaptor.forClass(UrlPair.class);
        verify(urlPairRepository, times(1)).save(captorUrlPair.capture());

        UrlPair actualUrlPair = captorUrlPair.getValue();
        UrlPair expectedUrlPair = new UrlPair(null, "shortUrl", "longUrl");
        assertThat(actualUrlPair).usingRecursiveComparison().isEqualTo(expectedUrlPair);

        when(counterRangeServerClient.getCounterRange()).thenReturn(Mono.just(new CounterRangeResponse(1, 1)));

        actual = shortenUrlService.shortenUrl("longUrl1");
        assertThat(actual).isEqualTo("shortUrl");

        verify(counterRangeServerClient, times(2)).getCounterRange();

        verify(base62Encoding, times(1)).encodeNumber(1);
        captorUrlPair = ArgumentCaptor.forClass(UrlPair.class);
        verify(urlPairRepository, times(2)).save(captorUrlPair.capture());

        actualUrlPair = captorUrlPair.getValue();
        expectedUrlPair = new UrlPair(null, "shortUrl", "longUrl1");
        assertThat(actualUrlPair).usingRecursiveComparison().isEqualTo(expectedUrlPair);
    }

    @Test
    void shortenUrl_CounterRangeServer_ReturnError() {
        when(counterRangeServerClient.getCounterRange())
                .thenReturn(Mono.error(new ClientException(ClientException.SERVICE_REQUEST_FAILED)));

        assertThrows(ClientException.class, () -> shortenUrlService.shortenUrl("longUrl"));
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