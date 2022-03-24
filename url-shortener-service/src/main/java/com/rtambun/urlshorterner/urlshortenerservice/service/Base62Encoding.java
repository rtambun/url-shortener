package com.rtambun.urlshorterner.urlshortenerservice.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class Base62Encoding {

    private int outputLength;

    public Base62Encoding(@Value("${base62.encoding.length}") int outputLength) {
        this.outputLength = outputLength;
    }

    public String encodeNumber(long i) {
        return "aaa";
    }
}
