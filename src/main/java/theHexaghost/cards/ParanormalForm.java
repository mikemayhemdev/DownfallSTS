package theHexaghost.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHexaghost.HexaMod;
import theHexaghost.powers.ParanormalFormPower;

public class ParanormalForm extends AbstractHexaCard {

    public final static String ID = makeID("ParanormalForm");

    //Poltergeist easier search

    private static final int MAGIC = 6;
    private static final int UPG_MAGIC = 3;

    public ParanormalForm() {
        super(ID, 1, CardType.POWER, CardRarity.RARE, CardTarget.SELF);
        baseMagicNumber = magicNumber = MAGIC;
        HexaMod.loadJokeCardImage(this, "ParanormalForm.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new ParanormalFormPower(1));
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            this.isInnate = true;
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
//            upgradeBaseCost(1);
//            upgradeMagicNumber(UPG_MAGIC);
        }
    }
}
