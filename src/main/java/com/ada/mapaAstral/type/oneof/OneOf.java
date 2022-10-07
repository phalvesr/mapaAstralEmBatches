package com.ada.mapaAstral.type.oneof;

import java.util.function.Function;

public interface OneOf<TFirst, TSecond, TThird, TFourth> {

    <TReturn> TReturn match(
        Function<TFirst, TReturn> firstMather,
        Function<TSecond, TReturn> secondMather,
        Function<TThird, TReturn> thirdMather,
        Function<TFourth, TReturn> fourthMatcher);
}
