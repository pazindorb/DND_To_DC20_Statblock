package com.pazindorb.statblockConverter.model.statblock.parts;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Movement {
    private String name;
    private int speed;

    public Movement(String name) {
        this.name = name;
        this.speed = 0;
    }
}
