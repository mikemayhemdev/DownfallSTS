package charbosses.cards.anticards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveAllBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.NoBlockPower;
import expansioncontent.cards.AbstractExpansionCard;

public class ShieldSmash extends AbstractExpansionCard {
    public final static String ID = makeID("ShieldSmash");

    public ShieldSmash() {
        super(ID, 1, CardType.SKILL, CardRarity.SPECIAL, CardTarget.ENEMY);
        exhaust = true;
        selfRetain = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new RemoveAllBlockAction(m, p));
        this.addToBot(new ApplyPowerAction(m, m, new DexterityPower(m, -2), -2));


    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(0);
        }
    }

}


