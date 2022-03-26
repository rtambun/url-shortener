package com.rtambun.urlshorterner.urlshortenerservice.controller;

import com.rtambun.urlshorterner.urlshortenerservice.dto.UrlShortenerRequest;
import com.rtambun.urlshorterner.urlshortenerservice.dto.UrlShortenerResponse;
import com.rtambun.urlshorterner.urlshortenerservice.service.Base62Encoding;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class UrlShortenerControllerTest {

    Base62Encoding mockBase62Encoding;
    UrlShortenerController urlShortenerController;

    @BeforeEach
    void setUp() {
        mockBase62Encoding = mock(Base62Encoding.class);
        urlShortenerController = new UrlShortenerController(mockBase62Encoding);
    }

    @Test
    void urlShortener_normalRequest_Response() {
        when(mockBase62Encoding.encodeNumber(anyLong())).thenReturn("anyDecodedNumber");

        UrlShortenerRequest urlShortenerRequest = new UrlShortenerRequest();
        urlShortenerRequest.setLongUrl("anyUrl");
        UrlShortenerResponse actual = urlShortenerController.urlShortener(urlShortenerRequest);

        verify(mockBase62Encoding, times(1)).encodeNumber(0);

        assertThat(actual.getShortUrl()).isEqualTo("anyDecodedNumber");
        assertThat(actual.getLongUrl()).isEqualTo("anyUrl");
    }
}