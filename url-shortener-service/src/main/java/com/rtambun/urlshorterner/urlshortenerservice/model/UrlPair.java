package com.rtambun.urlshorterner.urlshortenerservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Id;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class UrlPair {

    @Id
    public String id;

    public String shortUrl;
    public String longUrl;

    @Override
    public String toString() {
        return "UrlPair{" +
                "id='" + id + '\'' +
                ", shortUrl='" + shortUrl + '\'' +
                ", longUrl='" + longUrl + '\'' +
                '}';
    }
}
