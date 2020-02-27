package charbosses.powers.cardpowers;

import charbosses.bosses.AbstractCharBoss;
import charbosses.relics.AbstractCharbossRelic;
import charbosses.relics.CBR_Akabeko;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.AbstractPower.PowerType;

public class EnemyVigorPower extends AbstractPower {
    public static final String POWER_ID = "Vigor";
    private static final PowerStrings powerStrings;

    public EnemyVigorPower(AbstractCreature owner, int amount) {
        this.name = powerStrings.NAME;
        this.ID = "Vigor";
        this.owner = owner;
        this.amount = amount;
        this.updateDescription();
        this.loadRegion("vigor");
        this.type = PowerType.BUFF;
        this.isTurnBased = false;
    }

    public void updateDescription() {
        this.description = powerStrings.DESCRIPTIONS[0] + this.amount + powerStrings.DESCRIPTIONS[1];
    }

    public float atDamageGive(float damage, DamageType type) {
        return type == DamageType.NORMAL ? damage + (float)this.amount : damage;
    }

    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.type == CardType.ATTACK) {
            this.flash();
            AbstractCharBoss cB = (AbstractCharBoss)this.owner;
            if (cB.hasRelic(CBR_Akabeko.ID)){
                cB.getRelic(CBR_Akabeko.ID).flash();
                this.addToTop(new RelicAboveCreatureAction(this.owner, cB.getRelic(CBR_Akabeko.ID)));
            }

            this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, "Vigor"));

        }

    }

    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("Vigor");
    }
}
