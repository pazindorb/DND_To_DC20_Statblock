package com.pazindorb.statblockConverter.model.statblock;

import com.pazindorb.statblockConverter.model.statblock.parts.*;
import lombok.Getter;
import lombok.Setter;
import org.apache.tomcat.util.collections.CaseInsensitiveKeyMap;

import java.util.LinkedHashMap;
import java.util.Map;

@Getter @Setter
public class DNDStatblock extends BaseStatblock {
    private int AC;
    private int CR;

    public DNDStatblock() {
        initAbilities();
        initSkills();
        initDmgModifications();
        initConditions();
        initLanguages();
        initSenses();
        initSpeeds();
    }

    private void initAbilities() {
        Map<String, Ability> abilities = new LinkedHashMap<>();
        abilities.put("STR", new Ability("STR"));
        abilities.put("DEX", new Ability("DEX"));
        abilities.put("CON", new Ability("CON"));
        abilities.put("INT", new Ability("INT"));
        abilities.put("WIS", new Ability("WIS"));
        abilities.put("CHA", new Ability("CHA"));
        this.setAbilities(abilities);
    }

    private void initSkills() {
        Map<String, Skill> skills = new CaseInsensitiveKeyMap<>();
        skills.put("Athletics", new Skill("Athletics"));
        skills.put("Acrobatics", new Skill("Acrobatics"));
        skills.put("Sleight of Hand", new Skill("Sleight of Hand"));
        skills.put("Stealth", new Skill("Stealth"));
        skills.put("Arcana", new Skill("Arcana"));
        skills.put("History", new Skill("History"));
        skills.put("Investigation", new Skill("Investigation"));
        skills.put("Nature", new Skill("Nature"));
        skills.put("Religion", new Skill("Religion"));
        skills.put("Animal Handling", new Skill("Animal Handling"));
        skills.put("Insight", new Skill("Insight"));
        skills.put("Medicine", new Skill("Medicine"));
        skills.put("Perception", new Skill("Perception"));
        skills.put("Survival", new Skill("Survival"));
        skills.put("Deception", new Skill("Deception"));
        skills.put("Intimidation", new Skill("Intimidation"));
        skills.put("Performance", new Skill("Performance"));
        skills.put("Persuasion", new Skill("Persuasion"));
        this.setSkills(skills);
    }

    private void initDmgModifications() {
        Map<String, DmgModifications> dmgModifications = new CaseInsensitiveKeyMap<>();
        dmgModifications.put("Slashing", new DmgModifications("Slashing"));
        dmgModifications.put("Piercing", new DmgModifications("Piercing"));
        dmgModifications.put("Bludgeoning", new DmgModifications("Bludgeoning"));
        dmgModifications.put("Poison", new DmgModifications("Poison"));
        dmgModifications.put("Acid", new DmgModifications("Acid"));
        dmgModifications.put("Fire", new DmgModifications("Fire"));
        dmgModifications.put("Cold", new DmgModifications("Cold"));
        dmgModifications.put("Radiant", new DmgModifications("Radiant"));
        dmgModifications.put("Necrotic", new DmgModifications("Necrotic"));
        dmgModifications.put("Lightning", new DmgModifications("Lightning"));
        dmgModifications.put("Thunder", new DmgModifications("Thunder"));
        dmgModifications.put("Force", new DmgModifications("Force"));
        dmgModifications.put("Psychic", new DmgModifications("Psychic"));
        this.setDmgModifications(dmgModifications);
    }

    private void initConditions() {
        Map<String, Condition> conditions = new CaseInsensitiveKeyMap<>();
        conditions.put("Blinded", new Condition("Blinded"));
        conditions.put("Charmed", new Condition("Charmed"));
        conditions.put("Deafened", new Condition("Deafened"));
        conditions.put("Exhaustion", new Condition("Exhaustion"));
        conditions.put("Frightened", new Condition("Frightened"));
        conditions.put("Grappled", new Condition("Grappled"));
        conditions.put("Incapacitated", new Condition("Incapacitated"));
        conditions.put("Invisible", new Condition("Invisible"));
        conditions.put("Paralyzed", new Condition("Paralyzed"));
        conditions.put("Petrified", new Condition("Petrified"));
        conditions.put("Poisoned", new Condition("Poisoned"));
        conditions.put("Prone", new Condition("Prone"));
        conditions.put("Restrained", new Condition("Restrained"));
        conditions.put("Stunned", new Condition("Stunned"));
        conditions.put("Unconscious", new Condition("Unconscious"));
        this.setConditions(conditions);
    }

    private void initLanguages() {
        Map<String, Language> languages = new CaseInsensitiveKeyMap<>();
        languages.put("Common", new Language("Common"));
        languages.put("Dwarvish", new Language("Dwarvish"));
        languages.put("Elvish", new Language("Elvish"));
        languages.put("Giant", new Language("Giant"));
        languages.put("Gnomish", new Language("Gnomish"));
        languages.put("Goblin", new Language("Goblin"));
        languages.put("Halfling", new Language("Halfling"));
        languages.put("Orc", new Language("Orc"));
        languages.put("Druidic", new Language("Druidic"));
        languages.put("Thieves' Cant", new Language("Thieves' Cant"));
        languages.put("Abyssal", new Language("Abyssal"));
        languages.put("Celestial", new Language("Celestial"));
        languages.put("Draconic", new Language("Draconic"));
        languages.put("Deep Speech", new Language("Deep Speech"));
        languages.put("Infernal", new Language("Infernal"));
        languages.put("Primordial", new Language("Primordial"));
        languages.put("Sylvan", new Language("Sylvan"));
        languages.put("Undercommon", new Language("Undercommon"));
        this.setLanguages(languages);
    }

    private void initSenses() {
        Map<String, Sense> senses = new CaseInsensitiveKeyMap<>();
        senses.put("Blindsight", new Sense("Blindsight"));
        senses.put("Darkvision", new Sense("Darkvision"));
        senses.put("Tremorsense", new Sense("Tremorsense"));
        senses.put("Truesight", new Sense("Truesight"));
        this.setSenses(senses);
    }

    private void initSpeeds() {
        Map<String, Movement> movements = new CaseInsensitiveKeyMap<>();
        movements.put("Speed", new Movement("Speed"));
        movements.put("Burrow", new Movement("Burrow"));
        movements.put("Climb", new Movement("Climb"));
        movements.put("Swim", new Movement("Swim"));
        movements.put("Fly", new Movement("Fly"));
        this.setMovements(movements);
    }
}
