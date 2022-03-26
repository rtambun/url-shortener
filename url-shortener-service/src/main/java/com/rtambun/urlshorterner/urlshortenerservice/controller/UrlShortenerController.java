package com.rtambun.urlshorterner.urlshortenerservice.controller;

import com.rtambun.urlshorterner.urlshortenerservice.dto.UrlShortenerRequest;
import com.rtambun.urlshorterner.urlshortenerservice.dto.UrlShortenerResponse;
import com.rtambun.urlshorterner.urlshortenerservice.service.Base62Encoding;
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

    private Base62Encoding base62Encoding;
    private long currentCounter = 0;

    public UrlShortenerController(Base62Encoding base62Encoding) {
        this.base62Encoding = base62Encoding;
    }

    @PostMapping(value = "/url-shortener")
    public UrlShortenerResponse urlShortener (@RequestBody UrlShortenerRequest request) {
        log.info("Request to short following url : " + request.getLongUrl());
        UrlShortenerResponse response = new UrlShortenerResponse();
        response.setShortUrl(base62Encoding.encodeNumber(currentCounter++));
        response.setLongUrl(request.getLongUrl());
        return response;
    }
}
