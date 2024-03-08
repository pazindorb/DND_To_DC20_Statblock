package com.pazindorb.statblockConverter.service;

import com.pazindorb.statblockConverter.model.feature.Feature;
import com.pazindorb.statblockConverter.model.feature.FeatureType;
import com.pazindorb.statblockConverter.model.statblock.DNDStatblock;
import com.pazindorb.statblockConverter.model.statblock.parts.*;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.pazindorb.statblockConverter.kafka.KafkaTopicConfig.DND_PARSER;
import static com.pazindorb.statblockConverter.model.feature.FeatureType.*;
import static java.lang.Integer.parseInt;
import static org.springframework.util.StringUtils.startsWithIgnoreCase;

@Service
@RequiredArgsConstructor
public class DNDParserService {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public void sendParseMessage(String textFormatStatblock) {
        kafkaTemplate.send(DND_PARSER, textFormatStatblock);
//        kafkaTemplate.send(TO_DC20_CONVERTER, textFormatStatblock);
    }

    public DNDStatblock parse(String textStatblock) {
//        log.info("Beginning of parsing");
        List<String> statblockLines = textStatblock.lines().toList();
        return parseLinesToStatblock(statblockLines);
    }

    private DNDStatblock parseLinesToStatblock(List<String> lines) {
        DNDStatblock statblock = new DNDStatblock();
        if (lines.isEmpty()) return statblock;
        lines = prepareLines(lines);

        // Assume the first line is the name.
        statblock.setName(lines.getFirst());
        // Assume second line is a creature size.
        statblock.setSize(Size.form(lines.get(1).split(" ")[0]));

        StatblockSection currentSection = StatblockSection.TOP_SECTION;
        for(String line : lines) {
            String currentLine = line.trim().replaceAll(" +", " ");
            currentSection = checkCurrentSection(currentLine, currentSection);
            switch (currentSection) {
                case TOP_SECTION -> recognizeTopSection(currentLine, statblock);
                case ABILITIES -> recognizeAbilitySection(currentLine, statblock);
                case MIDDLE_SECTION -> recognizeMiddleSection(currentLine, statblock);
                case FEATURES -> recognizeFeaturesSection(currentLine, statblock, FEATURE);
                case ACTIONS -> recognizeFeaturesSection(currentLine, statblock, ACTION);
                case LEGENDARY -> recognizeFeaturesSection(currentLine, statblock, LEGENDARY);
            }
        }
        return statblock;
    }

    private List<String> prepareLines(List<String> lines) {
        return lines.stream()
                .map(line -> line.trim().replaceAll(" +", " "))
                .filter(line -> !line.isBlank())
                .toList();
    }

    private StatblockSection checkCurrentSection(String line, StatblockSection currentSection) {
        List<String> middleRowPatterns = List.of("Saving Throws", "Skills", "Damage Resistances", "Damage Immunities", "Damage Vulnerabilities", "Condition Immunities", "Senses", "Languages", "Challenge", "Proficiency Bonus");
        List<String> abilitiesRowPatterns = List.of("STR", "DEX", "CON", "INT", "WIS", "CHA");
        if (currentSection.equals(StatblockSection.TOP_SECTION) && startsWithAny(line, abilitiesRowPatterns)) return StatblockSection.ABILITIES;
        if (currentSection.equals(StatblockSection.ABILITIES) && startsWithAny(line, middleRowPatterns)) return StatblockSection.MIDDLE_SECTION;
        if (currentSection.equals(StatblockSection.MIDDLE_SECTION) && !startsWithAny(line, middleRowPatterns)) return StatblockSection.FEATURES;
        if (line.equalsIgnoreCase("ACTIONS")) return StatblockSection.ACTIONS;
        if (line.equalsIgnoreCase("LEGENDARY ACTIONS")) return StatblockSection.LEGENDARY;
        return currentSection;
    }

    private void recognizeTopSection(String line, DNDStatblock statblock) {
        if (startsWithIgnoreCase(line, "Armor Class")) {
            statblock.setAC(parseInt(line.split(" ")[2]));
        }

        if (startsWithIgnoreCase(line, "Hit Points")) {
            statblock.setHealth(parseInt(line.split(" ")[2]));
        }

        if (startsWithIgnoreCase(line, "Speed")) {
            String[] speedLines = line
                    .replaceAll("ft\\.", "")
                    .split(",");

            Arrays.stream(speedLines).forEach(speedLine -> {
                String[] speedParts = speedLine.trim().split(" ");
                String key = speedParts[0];
                if (statblock.getMovements().containsKey(key)) {
                    Movement movement = statblock.getMovements().get(key);
                    movement.setSpeed(clearInt(speedParts[1]));
                }
            });
        }
    }

    private void recognizeAbilitySection(String line, DNDStatblock statblock) {
        if (noneMach(line, List.of("STR", "DEX", "CON", "INT", "WIS", "CHA"))) {
            String[] abilityParts = line.split("\s*\\(\s*|\s*\\)\s*");
            for(int i = 0; i < abilityParts.length; i+=2) {
                Ability ability = getFirstNotFilledAbility(statblock.getAbilities());
                ability.setValue(clearInt(abilityParts[i]));
                ability.setMod(clearInt(abilityParts[i+1]));
                ability.setFilled(true);
            }
        }
    }

    private void recognizeMiddleSection(String line, DNDStatblock statblock) {
        if (startsWithIgnoreCase(line, "Saving Throws")) {
            String[] savesLines = line.replaceAll("Saving Throws", "").split(",");
            Arrays.stream(savesLines).forEach(save -> {
                String[] saveParts = save.trim().split(" ");
                String key = saveParts[0];
                if (statblock.getAbilities().containsKey(key)) {
                    Ability ability = statblock.getAbilities().get(key);
                    ability.setSaveMod(clearInt(saveParts[1]));
                    ability.setSaveProf(true);
                }
            });
        }

        if (startsWithIgnoreCase(line, "Skills")) {
            String[] skillLines = line.replaceAll("Skills", "").split(",");
            Arrays.stream(skillLines).forEach(skillLine -> {
                String[] skillParts = skillLine.trim().split(" ");
                String key = skillParts[0];
                if (statblock.getSkills().containsKey(key)) {
                    Skill skill = statblock.getSkills().get(key);
                    skill.setValue(clearInt(skillParts[1]));
                    skill.setProficiency(true);
                }
            });
        }

        if (startsWithIgnoreCase(line, "Damage Immunities")) {
            line = line.toLowerCase();
            Set<Map.Entry<String, DmgModifications>> entries = statblock.getDmgModifications().entrySet();
            for (Map.Entry<String, DmgModifications> entry : entries) {
                if (line.contains(entry.getKey().toLowerCase())) {
                    entry.getValue().setImmunity(true);
                }
            }
        }

        if (startsWithIgnoreCase(line, "Damage Resistances")) {
            line = line.toLowerCase();
            Set<Map.Entry<String, DmgModifications>> entries = statblock.getDmgModifications().entrySet();
            for (Map.Entry<String, DmgModifications> entry : entries) {
                if (line.contains(entry.getKey().toLowerCase())) {
                    entry.getValue().setResistance(true);
                }
            }
        }

        if (startsWithIgnoreCase(line, "Damage Vulnerabilities")) {
            line = line.toLowerCase();
            Set<Map.Entry<String, DmgModifications>> entries = statblock.getDmgModifications().entrySet();
            for (Map.Entry<String, DmgModifications> entry : entries) {
                if (line.contains(entry.getKey().toLowerCase())) {
                    entry.getValue().setVulnerability(true);
                }
            }
        }

        if (startsWithIgnoreCase(line, "Condition Immunities")) {
            line = line.toLowerCase();
            Set<Map.Entry<String, Condition>> entries = statblock.getConditions().entrySet();
            for (Map.Entry<String, Condition> entry : entries) {
                if (line.contains(entry.getKey().toLowerCase())) {
                    entry.getValue().setImmune(true);
                }
            }
        }

        if (startsWithIgnoreCase(line, "Languages")) {
            line = line.toLowerCase();
            Set<Map.Entry<String, Language>> entries = statblock.getLanguages().entrySet();
            for (Map.Entry<String, Language> entry : entries) {
                if (line.contains(entry.getKey().toLowerCase())) {
                    entry.getValue().setProficient(true);
                }
            }
        }

        if (startsWithIgnoreCase(line, "Senses")) {
            String[] senseLines = line
                    .replaceAll("Senses", "")
                    .replaceAll("ft\\.", "")
                    .split(",");

            Arrays.stream(senseLines).forEach(senseLine -> {
                String[] senseParts = senseLine.trim().split(" ");
                String key = senseParts[0];
                if (statblock.getSenses().containsKey(key)) {
                    Sense sense = statblock.getSenses().get(key);
                    sense.setDistance(clearInt(senseParts[1]));
                }
            });
        }

        if (startsWithIgnoreCase(line, "Challenge")) {
            int challengeRating =  clearInt(line.split(" ")[1]);
            statblock.setCR(challengeRating);
        }
    }

    private void recognizeFeaturesSection(String line, DNDStatblock statblock, FeatureType type) {
        // Skip if line is a section title
        if (line.equalsIgnoreCase("Actions")) return;
        if (line.equalsIgnoreCase("Legendary Actions")) return;

        String[] featureParts = line.split("\\.", 2);
        if (featureParts.length < 1) return;

        if (featureParts.length == 2) {
            statblock.addFeature(Feature.builder()
                    .name(featureParts[0])
                    .description(featureParts[1])
                    .featureType(type)
                    .build()
            );
        }

        // In that case we expect that it is not a separate feature and is still a part of last feature.
        if (featureParts.length != 2) {
            Feature last = statblock.getFeatures().getLast();
            String desc = last.getDescription();
            desc += "\n" + line;
            last.setDescription(desc);
        }
    }

    private boolean startsWithAny(String line, List<String> patterns) {
        for(String pattern : patterns) {
            if (startsWithIgnoreCase(line, pattern)) return true;
        }
        return false;
    }

    private boolean noneMach(String line, List<String> values) {
        return values.stream().noneMatch(line::equalsIgnoreCase);
    }

    private int clearInt(String line) {
        return parseInt(line
                .replaceAll("−", "-")   // "-" symbol copied from statblock is not normal "-" sign
                .replaceAll("\\+", "+") // "+" symbol copied from statblock is not normal "+" sign
                .replaceAll(" ", "")
                .replaceAll("\\(", "")
                .replaceAll("\\)", "")
        );
    }

    private Ability getFirstNotFilledAbility(Map<String, Ability> abilities) {
        return abilities.values().stream()
                .filter(ability -> !ability.isFilled())
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Parsing Error"));
    }

    private enum StatblockSection {
        TOP_SECTION, ABILITIES, MIDDLE_SECTION, FEATURES, ACTIONS, LEGENDARY;
    }

}
