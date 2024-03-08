package com.pazindorb.statblockConverter.model.statblock.parts;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Ability {
    private String name;
    private int value;
    private int mod;
    private int saveMod;
    private boolean saveProf;
    private transient boolean filled;

    public Ability(String name) {
        this.name = name;
        this.value = 10;
        this.mod = 0;
        this.saveMod = 0;
        this.saveProf = false;
        this.filled = false;
    }
}
