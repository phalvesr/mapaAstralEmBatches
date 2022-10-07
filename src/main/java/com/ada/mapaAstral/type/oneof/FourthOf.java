package com.ada.mapaAstral.type.oneof;

import java.util.function.Function;

public class FourthOf<TFirst, TSecond, TThird, TFourth> implements OneOf<TFirst, TSecond, TThird, TFourth> {

    private final TFourth value;

    private FourthOf(TFourth value) {
        this.value = value;
    }

    @Override
    public <TReturn> TReturn match(
            Function<TFirst, TReturn> firstMather,
            Function<TSecond, TReturn> secondMather,
            Function<TThird, TReturn> thirdMather,
            Function<TFourth, TReturn> fourthMatcher) {

        return fourthMatcher.apply(value);
    }

    public static <TFirst, TSecond, TThird, TFourth> OneOf<TFirst, TSecond, TThird, TFourth> create(TFourth value) {
        return new FourthOf<>(value);
    }
}
