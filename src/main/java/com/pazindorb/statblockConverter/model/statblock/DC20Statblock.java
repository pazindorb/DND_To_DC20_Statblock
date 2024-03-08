package com.pazindorb.statblockConverter.model.statblock;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class DC20Statblock extends BaseStatblock {
    private int PD;
    private int MD;
    private int PDR;
    private int MDR;
    private int attackMod;
    private int saveDC;
    private int actionPoints;
    private int staminaPoints;
    private int manaPoints;
    private int jump;
}
