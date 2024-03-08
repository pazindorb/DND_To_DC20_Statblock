package com.pazindorb.statblockConverter.model.statblock.parts;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class Condition {
    private String name;
    private boolean immune;

    public Condition(String name) {
        this.name = name;
        this.immune = false;
    }
}
