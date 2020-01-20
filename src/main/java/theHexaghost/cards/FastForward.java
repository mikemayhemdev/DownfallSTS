package theHexaghost.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHexaghost.actions.AdvanceAction;
import theHexaghost.actions.ChargeCurrentFlameAction;

public class FastForward extends AbstractHexaCard {

    public final static String ID = makeID("FastForward");

    //stupid intellij stuff SKILL, SELF, COMMON

    public FastForward() {
        super(ID, 2, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new AdvanceAction());
        atb(new ChargeCurrentFlameAction());
        atb(new AdvanceAction());
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(1);
        }
    }
}