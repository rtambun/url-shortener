package com.rtambun.urlshorterner.urlshortenerservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CounterRange {
    long min;
    long max;
    long counter;

    public void increaseCounter() {
        counter++;
    }

}
