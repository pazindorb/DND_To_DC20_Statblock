package com.pazindorb.statblockConverter.model.statblock.parts;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Language {

    private String name;
    private boolean proficient;

    public Language(String name) {
        this.name = name;
        this.proficient = false;
    }
}
