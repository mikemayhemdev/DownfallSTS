package awakenedOne.powers;

import com.evacipated.cardcrawl.mod.stslib.powers.abstracts.TwoAmountPower;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.StrengthPower;

import static awakenedOne.powers.AbstractAwakenedPower.makeID;
import static awakenedOne.util.Wiz.applyToSelf;

public class DarknessFallsPower extends TwoAmountPower implements OnLoseEnergyPower {
    // intellij stuff buff

    public static final String NAME = DarknessFallsPower.class.getSimpleName();
    public static final String POWER_ID = makeID(NAME);


    public DarknessFallsPower(AbstractCreature owner, int amount, int amount2)  {
        this.ID = POWER_ID;
        this.owner = owner;
        this.loadRegion("darkembrace");
        this.type = PowerType.BUFF;
        DESCRIPTIONS = CardCrawlGame.languagePack.getPowerStrings(this.ID).DESCRIPTIONS;
        this.amount2 = amount2;
        this.amount = amount;
        this.name = CardCrawlGame.languagePack.getPowerStrings(this.ID).NAME;

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