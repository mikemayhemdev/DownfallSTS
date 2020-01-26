package theHexaghost.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHexaghost.powers.ApocalypticArmorPower;

public class ApocalypticArmor extends AbstractHexaCard {

    public final static String ID = makeID("ApocalypticArmor");

    //stupid intellij stuff POWER, SELF, RARE

    private static final int MAGIC = 6;
    private static final int UPG_MAGIC = -1;

    public ApocalypticArmor() {
        super(ID, 0, CardType.POWER, CardRarity.RARE, CardTarget.SELF);
        baseMagicNumber = magicNumber = MAGIC;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new ApocalypticArmorPower(magicNumber));
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPG_MAGIC);
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}