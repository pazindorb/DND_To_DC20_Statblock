package com.pazindorb.statblockConverter.model.statblock.parts;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Sense {
    private String name;
    private int distance;

    public Sense(String name) {
        this.name = name;
        this.distance = 0;
    }
}
