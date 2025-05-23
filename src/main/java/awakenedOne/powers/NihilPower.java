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
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import downfall.util.TextureLoader;
import expansioncontent.expansionContentMod;
import hermit.patches.EnumPatch;
import slimebound.powers.SlimedPower;

import static awakenedOne.AwakenedOneMod.makeID;
import static awakenedOne.util.Wiz.applyToSelf;

public class NihilPower extends AbstractAwakenedPower {

    public static final String NAME = NihilPower.class.getSimpleName();
    public static final String POWER_ID = makeID(NAME);


    public NihilPower(final AbstractCreature owner, int amount) {
        super(NAME, PowerType.DEBUFF, false, owner, null, amount);
        updateDescription();
    }

    @Override
    public void atStartOfTurn() {
        if (owner instanceof AbstractMonster) {
            flash();
            addToBot(new ApplyPowerAction(owner, AbstractDungeon.player, new ManaburnPower(owner, amount), amount));
        }
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }
}