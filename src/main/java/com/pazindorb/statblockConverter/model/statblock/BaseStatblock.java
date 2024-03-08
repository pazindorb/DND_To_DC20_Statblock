package com.pazindorb.statblockConverter.model.statblock;

import com.pazindorb.statblockConverter.model.feature.Feature;
import com.pazindorb.statblockConverter.model.statblock.parts.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter @Setter
public abstract class BaseStatblock {
    private String name;
    private int health;
    private Size size;
    private List<Feature> features = new ArrayList<>();
    private Map<String, Ability> abilities;
    private Map<String, Skill> skills;
    private Map<String, Condition> conditions;
    private Map<String, DmgModifications> dmgModifications;
    private Map<String, Language> languages;
    private Map<String, Sense> senses;
    private Map<String, Movement> movements;

    public void addFeature(Feature feature) {
        features.add(feature);
    }
}
