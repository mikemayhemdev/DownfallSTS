package theHexaghost.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sneckomod.SneckoMod;
import theHexaghost.HexaMod;
import theHexaghost.powers.GainStrengthThatGoesAwayPower;

public class GhostOfSpirePresent extends AbstractHexaCard {

    public final static String ID = makeID("GhostOfSpirePresent");

    //stupid intellij stuff POWER, SELF, UNCOMMON

    private static final int MAGIC = 1;

    public GhostOfSpirePresent() {
        super(ID, 1, CardType.POWER, CardRarity.RARE, CardTarget.SELF);
        baseMagicNumber = magicNumber = MAGIC;
        tags.add(HexaMod.GHOSTWHEELCARD);
        this.tags.add(SneckoMod.BANNEDFORSNECKO);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new GainStrengthThatGoesAwayPower(magicNumber));
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(1);
        }
    }
}