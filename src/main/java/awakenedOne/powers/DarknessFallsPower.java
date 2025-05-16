package awakenedOne.powers;


import com.evacipated.cardcrawl.mod.stslib.powers.abstracts.TwoAmountPower;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.StrengthPower;

import static awakenedOne.util.Wiz.applyToSelf;

public class DarknessFallsPower extends TwoAmountPower implements OnLoseEnergyPower {
    // intellij stuff buff
    public static final String POWER_ID = "awakened:DarknessFallsPower";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public DarknessFallsPower(int amount, int amount2) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = AbstractDungeon.player;
        this.amount = amount;
        this.amount2 = amount2;
        this.type = PowerType.BUFF;
        this.isTurnBased = true;
        updateDescription();
    }

    @Override
    public void LoseEnergyAction(int gained) {
        flash();
        addToBot(new GainBlockAction(owner, gained*amount));
        applyToSelf(new StrengthPower(AbstractDungeon.player, gained*amount2));
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1] + amount2 + DESCRIPTIONS[2];
    }
}




