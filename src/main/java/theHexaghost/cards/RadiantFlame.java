package theHexaghost.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHexaghost.powers.RadiantPower;

public class RadiantFlame extends AbstractHexaCard {

    public final static String ID = makeID("RadiantFlame");

    //stupid intellij stuff POWER, SELF, UNCOMMON

    private static final int MAGIC = 4;
    private static final int UPG_MAGIC = 2;

    public RadiantFlame() {
        super(ID, 1, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = MAGIC;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new RadiantPower(magicNumber));
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPG_MAGIC);
        }
    }
}