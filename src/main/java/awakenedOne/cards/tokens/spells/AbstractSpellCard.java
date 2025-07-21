package awakenedOne.cards.tokens.spells;

import awakenedOne.cards.AbstractAwakenedCard;
import awakenedOne.powers.ConjureNextTurnPower;
import awakenedOne.powers.IntensifyPower;
import awakenedOne.relics.EyeOfTheOccult;
import champ.powers.DefensiveStylePower;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;

import java.util.Collections;
import java.util.List;

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
        super.applyPowers();
        if (AbstractDungeon.player.hasPower(IntensifyPower.POWER_ID)) {
            this.costForTurn = 0;
            this.isCostModifiedForTurn = true;
        }
        if (AbstractDungeon.actionManager.turnHasEnded) {
            if (!AbstractDungeon.player.hasPower(IntensifyPower.POWER_ID)) {
                this.costForTurn = this.cost;
                this.isCostModifiedForTurn = false;
            }
        }
    }


    @Override
    public List<String> getCardDescriptors() {
        return Collections.singletonList(uiStrings.TEXT[0]);
    }
}
