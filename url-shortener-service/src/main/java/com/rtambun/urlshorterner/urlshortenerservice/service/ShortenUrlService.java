package com.rtambun.urlshorterner.urlshortenerservice.service;

import com.rtambun.urlshorterner.urlshortenerservice.model.CounterRange;
import com.rtambun.urlshorterner.urlshortenerservice.model.UrlPair;
import com.rtambun.urlshorterner.urlshortenerservice.repository.UrlPairRepository;
import com.rtambun.urlshorterner.urlshortenerservice.service.client.CounterRangeServerClient;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class ShortenUrlService {

    private Base62Encoding base62Encoding;
    private UrlPairRepository urlPairRepository;
    private CounterRange counterRange;
    private CounterRangeServerClient counterRangeServerClient;
    private ModelMapper modelMapper;

    public ShortenUrlService(Base62Encoding base62Encoding,
                             UrlPairRepository urlPairRepository,
                             CounterRangeServerClient counterRangeServerClient,
                             ModelMapper modelMapper) {
        this.base62Encoding = base62Encoding;
        this.urlPairRepository = urlPairRepository;
        this.counterRangeServerClient = counterRangeServerClient;
        this.modelMapper = modelMapper;
    }

    public String shortenUrl (String longUrl) {
        if (counterRange == null || counterRange.getCounter() > counterRange.getMax()) {
            counterRange = modelMapper.map(counterRangeServerClient
                    .getCounterRange()
                    .block(), CounterRange.class);
            counterRange.setCounter(counterRange.getMin());
        }

        String shortUrl = base62Encoding.encodeNumber(counterRange.getCounter());
        counterRange.increaseCounter();

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
