package theHexaghost.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHexaghost.powers.CrispyPower;

public class ExtraCrispy extends AbstractHexaCard {

    public final static String ID = makeID("ExtraCrispy");

    //stupid intellij stuff POWER, SELF, UNCOMMON

    private static final int MAGIC = 1;
   // private static final int UPG_MAGIC = 1;

    public ExtraCrispy() {
        super(ID, 1, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = MAGIC;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new CrispyPower(1));
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            //upgradeMagicNumber(UPG_MAGIC);
            isInnate = true;
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}