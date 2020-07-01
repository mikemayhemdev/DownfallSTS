package charbosses.cards.anticards;

import charbosses.powers.general.PoisonProtectionPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveAllBlockAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import charbosses.powers.general.EnemyPoisonPower;
import com.megacrit.cardcrawl.powers.WeakPower;
import expansioncontent.cards.AbstractExpansionCard;

public class Antidote extends AbstractExpansionCard {
    public final static String ID = makeID("Antidote");

    public Antidote() {
        super(ID, 1, CardType.SKILL, CardRarity.SPECIAL, CardTarget.SELF);
        exhaust = true;
        selfRetain = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new RemoveSpecificPowerAction(p, p, EnemyPoisonPower.POWER_ID));
        this.addToBot(new ApplyPowerAction(p, p, new PoisonProtectionPower(p), this.magicNumber));
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(0);
        }
    }

}


