package com.rtambun.urlshorterner.urlshortenerservice.controller;

import com.rtambun.urlshorterner.urlshortenerservice.dto.UrlShortenerRequest;
import com.rtambun.urlshorterner.urlshortenerservice.dto.UrlResponse;
import com.rtambun.urlshorterner.urlshortenerservice.service.ShortenUrlService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
}
