package com.ada.mapaAstral.type.oneof;

import java.util.function.Function;

public final class SecondOf<TFirst, TSecond, TThird, TFourth> implements OneOf<TFirst, TSecond, TThird, TFourth>{

    private final TSecond value;

    private SecondOf(TSecond value) {
        this.value = value;
    }

    @Override
    public <TReturn> TReturn match(
            Function<TFirst, TReturn> firstMather,
            Function<TSecond, TReturn> secondMather,
            Function<TThird, TReturn> thirdMather,
            Function<TFourth, TReturn> fourthMatcher) {

        return secondMather.apply(value);
    }

    public static <TFirst, TSecond, TThird, TFourth> OneOf<TFirst, TSecond, TThird, TFourth> create(TSecond value) {
        return new SecondOf<>(value);
    }
}
