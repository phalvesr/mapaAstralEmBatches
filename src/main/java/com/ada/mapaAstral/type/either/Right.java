package com.ada.mapaAstral.type.either;

public class Right<TLeft, TRight> {
    private TRight value;

    public boolean isLeft() {
        return true;
    }

    public boolean isRight() {
        return false;
    }

    public TRight unsafeGetRight() {
        if (value == null) {
            throw new IllegalArgumentException("Left value was empty");
        } else {
            return value;
        }
    }

    public TLeft unsafeGetLeft() {
        throw new IllegalStateException("Left instance cannot contain right values");
    }
}
