package theHexaghost.cards;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHexaghost.HexaMod;
import theHexaghost.powers.CrispyPower;
import theHexaghost.powers.CrispyPower_new;

public class ExtraCrispy extends AbstractHexaCard {

    public final static String ID = makeID("ExtraCrispy");

    private static final int MAGIC = 1;
    // private static final int UPG_MAGIC = 1;

    public ExtraCrispy() {
        super(ID, 1, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = MAGIC;
        HexaMod.loadJokeCardImage(this, "ExtraCrispy.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new CrispyPower_new(magicNumber));
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(0);
//            isInnate = true;
//            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
