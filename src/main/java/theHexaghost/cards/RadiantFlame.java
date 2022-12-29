package theHexaghost.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHexaghost.HexaMod;
import sneckomod.SneckoMod;
import theHexaghost.powers.RadiantPower;

public class RadiantFlame extends AbstractHexaCard {

    public final static String ID = makeID("RadiantFlame");

    public RadiantFlame() {
        super(ID, 1, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 3;
        this.tags.add(SneckoMod.BANNEDFORSNECKO);
        HexaMod.loadJokeCardImage(this, "RadiantFlame.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new RadiantPower(magicNumber));
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(1);
        }
    }
}