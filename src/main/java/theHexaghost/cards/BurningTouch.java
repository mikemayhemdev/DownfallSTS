package theHexaghost.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHexaghost.powers.BurnPower;

public class BurningTouch extends AbstractHexaCard {

    public final static String ID = makeID("BurningTouch");

    //stupid intellij stuff SKILL, ENEMY, COMMON

    private static final int MAGIC = 7;
    private static final int UPG_MAGIC = 4;

    public BurningTouch() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.ENEMY);
        baseBurn = burn = MAGIC;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (m.hasPower(BurnPower.POWER_ID)) {
            burn(m, burn);
        }
        burn(m, burn);
    }

    @Override
    public void triggerOnGlowCheck() {
        burnGlowCheck();
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBurn(UPG_MAGIC);
        }
    }
}