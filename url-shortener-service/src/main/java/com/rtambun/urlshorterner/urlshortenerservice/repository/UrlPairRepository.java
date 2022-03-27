package com.rtambun.urlshorterner.urlshortenerservice.repository;

import com.rtambun.urlshorterner.urlshortenerservice.model.UrlPair;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UrlPairRepository extends MongoRepository<UrlPair, String> {
    UrlPair findByShortUrl(String shortUrl);
}
