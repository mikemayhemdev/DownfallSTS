package awakenedOne.cards.tokens.spells;

import awakenedOne.cards.AbstractAwakenedCard;
import awakenedOne.powers.ConjureNextTurnPower;
import awakenedOne.powers.IntensifyPower;
import awakenedOne.relics.EyeOfTheOccult;
import awakenedOne.relics.StrengthBooster;
import champ.powers.CounterPower;
import champ.powers.DefensiveStylePower;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import gremlin.powers.PolishPower;

import java.util.Collections;
import java.util.List;

import static awakenedOne.AwakenedOneMod.UP_NEXT;
import static awakenedOne.AwakenedOneMod.makeID;

public abstract class AbstractSpellCard extends AbstractAwakenedCard {
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(makeID("SpellDescriptor"));

    public AbstractSpellCard(String cardID, final int cost, CardType type, CardTarget target) {
        super(cardID, cost, type, CardRarity.SPECIAL, target, CardColor.COLORLESS);
        this.selfRetain = true;
        exhaust = true;
    }


    @Override
    public void applyPowers() {
        if ((AbstractDungeon.player != null) && (AbstractDungeon.player.hasRelic(StrengthBooster.ID))) {
            int realBaseDamage = this.baseDamage;
            this.baseDamage += StrengthBooster.AMOUNT;
            super.applyPowers();
            this.baseDamage = realBaseDamage;
            this.isDamageModified = this.damage != this.baseDamage;

            int realBaseBlock = this.baseBlock;
            this.baseBlock += StrengthBooster.AMOUNT;
            super.applyPowers();
            this.baseBlock = realBaseBlock;
            this.isBlockModified = this.block != this.baseBlock;

            }
        super.applyPowers();
    }

    public void calculateCardDamage(AbstractMonster mo) {
        int realBaseDamage = this.baseDamage;
        if (AbstractDungeon.player.hasRelic(StrengthBooster.ID)) {
            this.baseDamage += StrengthBooster.AMOUNT;
        }

        int realBaseBlock = this.baseBlock;
        if (AbstractDungeon.player.hasRelic(StrengthBooster.ID)) {
            this.baseBlock += StrengthBooster.AMOUNT;
        }

        super.calculateCardDamage(mo);

        this.baseDamage = realBaseDamage;
        this.isDamageModified = this.damage != this.baseDamage;

        this.baseBlock = realBaseBlock;
        this.isBlockModified = this.block != this.baseBlock;

    }

    @Override
    public List<String> getCardDescriptors() {
        return Collections.singletonList(uiStrings.TEXT[0]);
    }
}
