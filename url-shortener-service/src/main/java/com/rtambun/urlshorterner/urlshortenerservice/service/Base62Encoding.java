package com.rtambun.urlshorterner.urlshortenerservice.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class Base62Encoding {

    private static final String base62 = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    private int outputLength;
    private long maxEncodedNumber = 1;
    private HashMap<Integer, Character> digitToBase62Map;
    private HashMap<Character, Integer> base62ToDigitMap;

    public Base62Encoding(@Value("${base62.encoding.length}") int outputLength) {
        long max = Long.MAX_VALUE;
        int maxOutputLength = 0;
        while (max > 0) {
            max /= 62;
            if (max > 0) {
                maxOutputLength++;
            }
        }
        if (outputLength > maxOutputLength || outputLength < 0) {
            throw new IllegalArgumentException("Output length of " + outputLength +
                    " is bigger than max output length of " + maxOutputLength );
        }

        for (int i = 0; i < outputLength; i++) {
            maxEncodedNumber *= 62;
        }

        this.outputLength = outputLength;
        char[] base62CharArray = base62.toCharArray();
        digitToBase62Map = new HashMap<>();
        base62ToDigitMap = new HashMap<>();
        int i = 0;
        for(char c : base62CharArray) {
            digitToBase62Map.put(i, c);
            base62ToDigitMap.put(c, i++);
        }
    }

    public String encodeNumber(long toEncode) {

        if (toEncode >= maxEncodedNumber ||  toEncode < 0) {
            throw new IllegalArgumentException("Number to encode " + toEncode + " is out of range");
        }

        StringBuilder base62 = new StringBuilder();
        for (int i = 0; i < outputLength; i++) {
            int digit = (int)(toEncode % 62);
            toEncode = toEncode / 62;
            base62.insert(0, digitToBase62Map.get(digit));
        }
        return base62.toString();
    }

    public long decodeString(String toDecode) {

        if (toDecode == null || toDecode.length() > outputLength) {
            throw new IllegalArgumentException("String to decode " + toDecode +
                    " is longer than supported length of " + outputLength);
        }

        char[] charToDecode = toDecode.toCharArray();
        long l = 0;
        for (char c : charToDecode) {
            if (!base62ToDigitMap.containsKey(c)) {
                throw new IllegalArgumentException("String to decode " + toDecode +
                        " is having unsupported character " + c  + "to decode");
            }
            l = 62 * l + base62ToDigitMap.get(c);
        }
        return l;
    }
}
