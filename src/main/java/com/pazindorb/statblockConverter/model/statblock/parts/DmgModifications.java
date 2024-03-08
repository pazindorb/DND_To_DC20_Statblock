package com.pazindorb.statblockConverter.model.statblock.parts;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class DmgModifications {
    private String name;
    private boolean immunity;
    private boolean resistance;
    private boolean vulnerability;
    private int resit;
    private int vulnerable;

    public DmgModifications(String name) {
        this.name = name;
        this.immunity = false;
        this.resistance = false;
        this.vulnerability = false;
    }
}
