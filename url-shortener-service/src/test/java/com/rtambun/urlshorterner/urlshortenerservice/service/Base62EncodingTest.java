package com.rtambun.urlshorterner.urlshortenerservice.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class Base62EncodingTest {

    @ParameterizedTest
    @ValueSource(ints = {-1, 11})
    void constructor_whenOutputLengthExceedMaximumRangeOfLong_ThrowIllegalArgumentException(int outputLength) {
        assertThrows(IllegalArgumentException.class, () -> new Base62Encoding(outputLength));
    }

    @Test
    void encodeNumber_whenOutputLengthIsZero_ReturnEmptyString() {
        Base62Encoding base62Encoding = new Base62Encoding(0);
        String encoded = base62Encoding.encodeNumber(0);
        assertThat(encoded).isEqualTo("");
    }

    @ParameterizedTest
    @ValueSource(ints = {-2, -1, 1 ,2})
    void encodeNumber_whenOutputLengthIsZero_AndToDecodeIsBiggerThanZeroOrNegative_ThrowIllegalArgumentException(
            int outputLength) {
        Base62Encoding base62Encoding = new Base62Encoding(0);
        assertThrows(IllegalArgumentException.class, () -> base62Encoding.encodeNumber(outputLength));
    }

    @ParameterizedTest
    @MethodSource(value = "getData_encodeNumber_whenOutputLengthIsOne_ReturnStringWithLengthOne")
    void encodeNumber_whenOutputLengthIsOne_ReturnStringWithLengthOne(long toEncode, String expected) {
        Base62Encoding base62Encoding = new Base62Encoding(1);
        String encoded = base62Encoding.encodeNumber(toEncode);
        assertThat(encoded).isEqualTo(expected);
    }

    static Stream<Arguments> getData_encodeNumber_whenOutputLengthIsOne_ReturnStringWithLengthOne() {
        return Stream.of(Arguments.of(0, "0"),
                Arguments.of(1, "1"),
                Arguments.of(61, "Z"));
    }

    @ParameterizedTest
    @ValueSource(longs = {-2, -1, 62, 63})
    void encodeNumber_whenOutputLengthOne_ToEncodeIsNegativeOrExceedMaximumSupportedNumber_ThrowArgumentOutOfRangeException(
            long toEncode
    ) {
        Base62Encoding base62Encoding = new Base62Encoding(1);
        assertThrows(IllegalArgumentException.class, () -> base62Encoding.encodeNumber(toEncode));
    }

    @ParameterizedTest
    @MethodSource(value = "getData_encodeNumber_whenOutputLengthIsTwo_ReturnStringWithLengthOne")
    void encodeNumber_whenOutputLengthIsTwo_ReturnStringWithLengthOne(long toEncode, String expected) {
        Base62Encoding base62Encoding = new Base62Encoding(2);
        String encoded = base62Encoding.encodeNumber(toEncode);
        assertThat(encoded).isEqualTo(expected);
    }

    static Stream<Arguments> getData_encodeNumber_whenOutputLengthIsTwo_ReturnStringWithLengthOne() {
        return Stream.of(Arguments.of(0, "00"),
                Arguments.of(1, "01"),
                Arguments.of(61, "0Z"),
                Arguments.of(62, "10"),
                Arguments.of(62*62-1, "ZZ"));
    }

    @ParameterizedTest
    @ValueSource(longs = {-2, -1, 62*62, 62*62+1})
    void encodeNumber_whenOutputLengthTwo_ToEncodeIsNegativeOrExceedMaximumSupportedNumber_ThrowArgumentOutOfRangeException(
            long toEncode
    ) {
        Base62Encoding base62Encoding = new Base62Encoding(2);
        assertThrows(IllegalArgumentException.class, () -> base62Encoding.encodeNumber(toEncode));
    }

    @Test
    void decodeString_whenOutputLengthIsZero_StringToDecodeEmpty_ReturnZero() {
        Base62Encoding base62Encoding = new Base62Encoding(0);
        assertThat(base62Encoding.decodeString("")).isEqualTo(0);
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"a", "ab"})
    void decodeString_whenOutputLengthIsZero_StringToDecodeNullOrMoreThanZero_ThrowIllegalArgumentException(
            String toDecode) {
        Base62Encoding base62Encoding = new Base62Encoding(0);
        assertThrows(IllegalArgumentException.class, () -> base62Encoding.decodeString(toDecode));
    }

    @ParameterizedTest
    @MethodSource(value = "getData_decodeString_whenOutputLengthIsOne_StringToDecodeWithinBound_ReturnDecodedNumber")
    void decodeString_whenOutputLengthIsOne_StringToDecodeWithinBoundary_ReturnDecodedNumber(long decodedNumber
            , String toDecode) {
        Base62Encoding base62Encoding = new Base62Encoding(1);
        assertThat(base62Encoding.decodeString(toDecode)).isEqualTo(decodedNumber);
    }

    static Stream<Arguments> getData_decodeString_whenOutputLengthIsOne_StringToDecodeWithinBound_ReturnDecodedNumber() {
        return Stream.of(Arguments.of(0, "0"),
                Arguments.of(1, "1"),
                Arguments.of(61, "Z"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"!", "@"})
    void decodeString_whenOutputLengthIsOne_StringToDecodeIsNotBase62Character_ThrowIllegalArgumentException(
            String toDecode) {
        Base62Encoding base62Encoding = new Base62Encoding(1);
        assertThrows(IllegalArgumentException.class, () -> base62Encoding.decodeString(toDecode));
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"ab", "abc"})
    void decodeString_whenOutputLengthIsOne_StringToDecodeIsNullOrMoreThanOne_ThrowIllegalArgumentException(
            String toDecode) {
        Base62Encoding base62Encoding = new Base62Encoding(0);
        assertThrows(IllegalArgumentException.class, () -> base62Encoding.decodeString(toDecode));
    }

    @ParameterizedTest
    @MethodSource(value = "getData_decodeString_whenOutputLengthIsTwo_StringTwoDecodeIsWithinBoundary_ReturnDecodeNumber")
    void decodeString_whenOutputLengthIsTwo_StringTwoDecodeIsWithinBoundary_ReturnDecodeNumber(long decodedNumber
            , String toDecode) {
        Base62Encoding base62Encoding = new Base62Encoding(2);
        assertThat(base62Encoding.decodeString(toDecode)).isEqualTo(decodedNumber);
    }

    static Stream<Arguments> getData_decodeString_whenOutputLengthIsTwo_StringTwoDecodeIsWithinBoundary_ReturnDecodeNumber() {
        return Stream.of(Arguments.of(0, "00"),
                Arguments.of(1, "01"),
                Arguments.of(61, "0Z"),
                Arguments.of(62, "10"),
                Arguments.of(62*62-1, "ZZ"));
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"!a", "a!", "@#"})
    void decodeString_whenOutputLengthIsTwo_StringToDecodeIsNotBase62Character_ThrowIllegalArgumentException(
            String toDecode) {
        Base62Encoding base62Encoding = new Base62Encoding(2);
        assertThrows(IllegalArgumentException.class, () -> base62Encoding.decodeString(toDecode));
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"abc", "abcd"})
    void decodeString_whenOutputLengthIsTwo_StringToDecodeIsNullOrMoreThanTwo_ThrowIllegalArgumentException(
            String toDecode) {
        Base62Encoding base62Encoding = new Base62Encoding(0);
        assertThrows(IllegalArgumentException.class, () -> base62Encoding.decodeString(toDecode));
    }
}