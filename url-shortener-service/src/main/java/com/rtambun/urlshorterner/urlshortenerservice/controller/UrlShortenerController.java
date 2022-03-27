package com.rtambun.urlshorterner.urlshortenerservice.controller;

import com.rtambun.urlshorterner.urlshortenerservice.dto.LongUrlRequest;
import com.rtambun.urlshorterner.urlshortenerservice.dto.UrlShortenerRequest;
import com.rtambun.urlshorterner.urlshortenerservice.dto.UrlResponse;
import com.rtambun.urlshorterner.urlshortenerservice.service.ShortenUrlService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityNotFoundException;

@Controller
@ResponseBody
@RequestMapping("/api/v1")
@Log4j2
public class UrlShortenerController {

    private ShortenUrlService shortenUrlService;

    public UrlShortenerController(ShortenUrlService shortenUrlService) {
        this.shortenUrlService = shortenUrlService;
    }

    @PostMapping(value = "/shortUrl")
    public UrlResponse createShortUrl(@RequestBody UrlShortenerRequest request) {
        log.info("Request to short following url : " + request.getLongUrl());
        UrlResponse response = new UrlResponse();
        response.setShortUrl(shortenUrlService.shortenUrl(request.getLongUrl()));
        response.setLongUrl(request.getLongUrl());
        return response;
    }

    @PostMapping(value = "/longUrl")
    public UrlResponse retrieveLongUrl(@RequestBody LongUrlRequest request) {
        log.info("Request to have long url from following url : "  + request.getShortUrl());
        UrlResponse response = new UrlResponse();
        response.setShortUrl(request.getShortUrl());
        try {
            response.setLongUrl(shortenUrlService.retrieveLongUrl(request.getShortUrl()));
        } catch (EntityNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Short url " + request.shortUrl + "not found");
        }
        return response;
    }
}
