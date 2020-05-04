package charbosses.cards.anticards;

import com.megacrit.cardcrawl.actions.common.RemoveAllBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
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
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(0);
        }
    }

}


