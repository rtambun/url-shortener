package com.rtambun.urlshorterner.urlshortenerservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UrlResponse {
    private String shortUrl;
    private String longUrl;
}
