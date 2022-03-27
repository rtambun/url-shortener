package com.rtambun.urlshorterner.urlshortenerservice.repository;

import com.rtambun.urlshorterner.urlshortenerservice.model.UrlPair;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
class UrlPairRepositoryTest {

    @Autowired
    UrlPairRepository urlRepository;

    @Test
    void findByShortUrl() {
        UrlPair urlPair = new UrlPair();
        urlPair.setShortUrl("anyUrl");
        urlPair.setLongUrl("longUrl");
        urlRepository.save(urlPair);

        UrlPair actual = urlRepository.findByShortUrl("any");
        assertThat(actual).isNull();

        actual = urlRepository.findByShortUrl("anyUrl");
        assertThat(actual).usingRecursiveComparison().isEqualTo(urlPair);

    }
}