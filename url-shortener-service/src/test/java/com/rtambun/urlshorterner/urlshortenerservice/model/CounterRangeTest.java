package com.rtambun.urlshorterner.urlshortenerservice.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class CounterRangeTest {

    @Test
    void allArgsConstructor() {
        CounterRange counterRange = new CounterRange(1,1,0);
        assertThat(counterRange.getMin()).isEqualTo(1);
        assertThat(counterRange.getMax()).isEqualTo(1);
        assertThat(counterRange.getCounter()).isEqualTo(0);
    }

    @Test
    void increaseCounter() {
        CounterRange counterRange = new CounterRange();
        counterRange.increaseCounter();
        assertThat(counterRange.getCounter()).isEqualTo(1);
    }

    @Test
    void setMin() {
        CounterRange counterRange = new CounterRange();
        assertThat(counterRange.getMin()).isEqualTo(0);
        counterRange.setMin(1);
        assertThat(counterRange.getMin()).isEqualTo(1);
    }

    @Test
    void setMax() {
        CounterRange counterRange = new CounterRange();
        assertThat(counterRange.getMax()).isEqualTo(0);
        counterRange.setMax(1);
        assertThat(counterRange.getMax()).isEqualTo(1);
    }

    @Test
    void setCounter() {
        CounterRange counterRange = new CounterRange();
        assertThat(counterRange.getCounter()).isEqualTo(0);
        counterRange.setCounter(1);
        assertThat(counterRange.getCounter()).isEqualTo(1);
    }
}