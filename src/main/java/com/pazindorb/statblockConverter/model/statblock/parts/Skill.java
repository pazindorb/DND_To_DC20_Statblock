package com.pazindorb.statblockConverter.model.statblock.parts;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Skill {
    private String name;
    private int value;
    private boolean proficiency;

    public Skill(String name) {
        this.name = name;
        this.value = 0;
        this.proficiency = false;
    }
}
