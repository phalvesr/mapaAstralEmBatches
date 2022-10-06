package com.ada.mapaAstral.type.either;

public interface Either<TLeft, TRight> {

    TLeft unsafeGetLeft();
    TRight unsafeGetRight();
    boolean isLeft();
    boolean isRight();
}
