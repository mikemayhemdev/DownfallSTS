package theHexaghost.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHexaghost.powers.BurnvenomPower;

public class WildfireWeapon extends AbstractHexaCard {

    public final static String ID = makeID("WildfireWeapon");

    //stupid intellij stuff POWER, SELF, RARE

    private static final int MAGIC = 4;
    private static final int UPGRADE_MAGIC = 2;

    public WildfireWeapon() {
        super(ID, 1, CardType.POWER, CardRarity.RARE, CardTarget.SELF);
        baseMagicNumber = magicNumber = MAGIC;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new BurnvenomPower(magicNumber));
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_MAGIC);
        }
    }
}