package awakenedOne.powers;


import awakenedOne.cards.Retaliation;
import awakenedOne.powers.AbstractAwakenedPower;
import awakenedOne.powers.OnLoseEnergyPower;
import awakenedOne.powers.PrimacyPower;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.powers.abstracts.TwoAmountPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.StrengthPower;
import downfall.util.TextureLoader;
import expansioncontent.expansionContentMod;
import hermit.patches.EnumPatch;

import static awakenedOne.AwakenedOneMod.makeID;
import static awakenedOne.util.Wiz.applyToSelf;

public class ManaburnPower extends AbstractAwakenedPower  implements OnLoseEnergyPower {
    // intellij stuff buff


    public static final String POWER_ID = "awakened:ManaburnPower";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;


    public ManaburnPower(final AbstractCreature owner, int amount) {
        super(NAME, PowerType.DEBUFF, false, owner, null, amount);
        updateDescription();
    }


    @Override
    public void LoseEnergyAction(int gained) {
        this.flash();
        this.addToBot(new DamageAction(owner, new DamageInfo(owner, amount, DamageInfo.DamageType.HP_LOSS), EnumPatch.HERMIT_GHOSTFIRE));
    }

    @Override
    public void triggerMarks(AbstractCard card) {
        if (card.cardID.equals(Retaliation.ID)) {
            this.addToBot(new LoseHPAction(this.owner, (AbstractCreature)null, this.amount, EnumPatch.HERMIT_GHOSTFIRE));
        }
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }
}