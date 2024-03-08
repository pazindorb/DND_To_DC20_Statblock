package com.pazindorb.statblockConverter.model.statblock.parts;

import java.util.Arrays;

public enum Size {
    MICRO,
    TINY,
    SMALL,
    MEDIUM,
    LARGE,
    HUGE,
    GARGANTUAN,
    COLOSSAL,
    TITANIC;

    public static Size form(String sizeName) {
        return Arrays.stream(Size.values())
                .filter(size -> sizeName.equalsIgnoreCase(size.name()))
                .findFirst()
                .orElse(Size.MEDIUM);
    }
}
