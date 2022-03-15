package charbosses.powers.general;

import charbosses.cards.AbstractBossCard;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class EnemyVigorPower extends AbstractPower {
    public static final String POWER_ID = "downfall:EnemyVigor";
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    private static final PowerStrings powerStrings;

    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("Vigor");
        NAME = EnemyVigorPower.powerStrings.NAME;
        DESCRIPTIONS = EnemyVigorPower.powerStrings.DESCRIPTIONS;
    }

    public EnemyVigorPower(final AbstractCreature owner, final int drawAmount) {
        this.name = EnemyVigorPower.NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = drawAmount;
        this.updateDescription();
        this.loadRegion("vigor");
        this.type = PowerType.BUFF;
        this.isTurnBased = false;
    }

    public void updateDescription() {
        this.description = powerStrings.DESCRIPTIONS[0] + this.amount + powerStrings.DESCRIPTIONS[1];
    }

    public float atDamageGive(float damage, DamageInfo.DamageType type) {
        return type == DamageInfo.DamageType.NORMAL ? damage + (float) this.amount : damage;
    }

    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.type == AbstractCard.CardType.ATTACK && card instanceof AbstractBossCard) {
            this.flash();
            this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, "Vigor"));
        }

    }
}
