package charbosses.powers.cardpowers;

import charbosses.cards.AbstractBossCard;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;



public class EnemyPenNibPower extends AbstractPower {
    public static final String POWER_ID = "Pen Nib";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;

    public EnemyPenNibPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = "Pen Nib";
        this.owner = owner;
        this.amount = amount;
        this.updateDescription();
        this.loadRegion("penNib");
        this.type = PowerType.BUFF;
        this.isTurnBased = true;
        this.priority = 6;
    }

    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.type == AbstractCard.CardType.ATTACK && card instanceof AbstractBossCard) {
            this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, "Pen Nib"));
        }

    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

    public float atDamageGive(float damage, DamageInfo.DamageType type) {
        return type == DamageInfo.DamageType.NORMAL ? damage * 2.0F : damage;
    }

    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("Pen Nib");
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}
