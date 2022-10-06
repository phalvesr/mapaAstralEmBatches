package com.ada.mapaAstral.type.either;

public class Left<TLeft, TRight> implements Either<TLeft, TRight> {

    private TLeft value;

    public boolean isLeft() {
        return true;
    }

    public boolean isRight() {
        return false;
    }

    public TLeft unsafeGetLeft() {
        if (value == null) {
            throw new IllegalArgumentException("Left value was empty");
        } else {
            return value;
        }
    }

    public TRight unsafeGetRight() {
        throw new IllegalStateException("Left instance cannot contain right values");
    }
}
