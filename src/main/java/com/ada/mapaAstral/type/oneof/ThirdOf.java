package com.ada.mapaAstral.type.oneof;

import java.util.function.Function;

public class ThirdOf<TFirst, TSecond, TThird, TFourth> implements OneOf<TFirst, TSecond, TThird, TFourth> {

    private final TThird value;

    private ThirdOf(TThird value) {
        this.value = value;
    }

    @Override
    public <TReturn> TReturn match(
            Function<TFirst, TReturn> firstMather,
            Function<TSecond, TReturn> secondMather,
            Function<TThird, TReturn> thirdMather,
            Function<TFourth, TReturn> fourthMatcher) {

        return thirdMather.apply(value);
    }

    public static <TFirst, TSecond, TThird, TFourth> OneOf<TFirst, TSecond, TThird, TFourth> create(TThird value) {
        return new ThirdOf<>(value);
    }
}