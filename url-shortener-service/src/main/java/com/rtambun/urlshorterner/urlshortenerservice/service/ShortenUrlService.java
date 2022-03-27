package com.rtambun.urlshorterner.urlshortenerservice.service;

import com.rtambun.urlshorterner.urlshortenerservice.model.UrlPair;
import com.rtambun.urlshorterner.urlshortenerservice.repository.UrlPairRepository;
import org.hibernate.ObjectNotFoundException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class ShortenUrlService {

    private Base62Encoding base62Encoding;
    private UrlPairRepository urlPairRepository;
    private long counter = 0;

    public ShortenUrlService(Base62Encoding base62Encoding, UrlPairRepository urlPairRepository) {
        this.base62Encoding = base62Encoding;
        this.urlPairRepository = urlPairRepository;
    }

    public String shortenUrl (String longUrl) {
        String shortUrl = base62Encoding.encodeNumber(counter++);
        UrlPair urlPair = new UrlPair();
        urlPair.longUrl = longUrl;
        urlPair.shortUrl = shortUrl;
        urlPairRepository.save(urlPair);
        return shortUrl;
    }

    public String retrieveLongUrl(String shortUrl) throws EntityNotFoundException {
        UrlPair url = urlPairRepository.findByShortUrl(shortUrl);
        if (url == null) {
            throw new EntityNotFoundException();
        }
        return url.getLongUrl();
    }
}
