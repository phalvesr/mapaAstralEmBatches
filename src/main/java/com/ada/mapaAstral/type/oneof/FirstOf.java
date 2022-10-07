package com.ada.mapaAstral.type.oneof;

import java.util.function.Function;

public class FirstOf<TFirst, TSecond, TThird, TFourth> implements OneOf<TFirst, TSecond, TThird, TFourth>{

    private final TFirst value;

    private FirstOf(TFirst value) {
        this.value = value;
    }

    @Override
    public <TReturn> TReturn match(
            Function<TFirst, TReturn> firstMather,
            Function<TSecond, TReturn> secondMather,
            Function<TThird, TReturn> thirdMather,
            Function<TFourth, TReturn> fourthMatcher) {

        return firstMather.apply(value);
    }

    public static <TFirst, TSecond, TThird, TFourth> OneOf<TFirst, TSecond, TThird, TFourth> create(TFirst value) {
        return new FirstOf<>(value);
    }
}
