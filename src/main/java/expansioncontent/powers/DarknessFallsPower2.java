package expansioncontent.powers;


import awakenedOne.powers.OnLoseEnergyPower;
import com.evacipated.cardcrawl.mod.stslib.powers.abstracts.TwoAmountPower;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.StrengthPower;

import static awakenedOne.util.Wiz.applyToSelf;

public class DarknessFallsPower2 extends TwoAmountPower implements OnLoseEnergyPower {
    // intellij stuff buff
    public static final String POWER_ID = "expansioncontent:DarknessFallsPower";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public DarknessFallsPower2(int amount, int amount2) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.amount = amount;
        this.amount2 = amount2;
        this.type = PowerType.BUFF;
        this.loadRegion("hex");
        updateDescription();
    }

    @Override
    public void LoseEnergyAction(int gained) {
        this.flash();
        addToBot(new GainBlockAction(owner, gained*amount));
        applyToSelf(new StrengthPower(AbstractDungeon.player, gained*amount2));
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1] + amount2 + DESCRIPTIONS[2];
    }
}