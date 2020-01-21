package theHexaghost.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHexaghost.actions.BurnAction;
import theHexaghost.powers.BurnPower;

public class Sear extends AbstractHexaCard {

    public final static String ID = makeID("Sear");

    //stupid intellij stuff SKILL, ENEMY, BASIC

    private static final int MAGIC = 5;

    public Sear() {
        super(ID, 1, CardType.SKILL, CardRarity.BASIC, CardTarget.ENEMY);
        baseMagicNumber = magicNumber = MAGIC;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new BurnAction(m, magicNumber));
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(0);
        }
    }
}