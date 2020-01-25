package theHexaghost.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHexaghost.powers.VolcanoVisagePower;

public class VolcanoVisage extends AbstractHexaCard {

    public final static String ID = makeID("VolcanoVisage");

    //stupid intellij stuff POWER, SELF, RARE

    private static final int MAGIC = 4;
    private static final int UPG_MAGIC = 2;

    public VolcanoVisage() {
        super(ID, 1, CardType.POWER, CardRarity.RARE, CardTarget.SELF);
        baseMagicNumber = magicNumber = MAGIC;
        isEthereal = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new VolcanoVisagePower(magicNumber));
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPG_MAGIC);
        }
    }
}