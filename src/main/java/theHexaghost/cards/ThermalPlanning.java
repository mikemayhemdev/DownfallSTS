package theHexaghost.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHexaghost.powers.GainEnhanceWhenMovingPower;

public class ThermalPlanning extends AbstractHexaCard {

    public final static String ID = makeID("ThermalPlanning");

    //stupid intellij stuff SKILL, SELF, COMMON

    private static final int MAGIC = 2;
    private static final int UPG_MAGIC = 2;

    public ThermalPlanning() {
        super(ID, 0, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = MAGIC;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new GainEnhanceWhenMovingPower(magicNumber));
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPG_MAGIC);
        }
    }
}