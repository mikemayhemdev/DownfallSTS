package theHexaghost.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHexaghost.powers.GainStrengthThatGoesAwayPower;

public class GhostOfSpirePresent extends AbstractHexaCard {

    public final static String ID = makeID("GhostOfSpirePresent");

    //stupid intellij stuff POWER, SELF, UNCOMMON

    private static final int MAGIC = 3;

    public GhostOfSpirePresent() {
        super(ID, 1, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = MAGIC;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new GainStrengthThatGoesAwayPower(magicNumber));
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            isInnate = true;
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}