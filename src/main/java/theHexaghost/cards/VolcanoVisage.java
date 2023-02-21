package theHexaghost.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHexaghost.HexaMod;
import sneckomod.SneckoMod;
import theHexaghost.powers.VolcanoVisagePower;

public class VolcanoVisage extends AbstractHexaCard {

    public final static String ID = makeID("VolcanoVisage");

    public VolcanoVisage() {
        super(ID, 1, CardType.POWER, CardRarity.RARE, CardTarget.SELF);
        baseMagicNumber = magicNumber = 4;
        this.tags.add(SneckoMod.BANNEDFORSNECKO);
        HexaMod.loadJokeCardImage(this, "VolcanoVisage.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new VolcanoVisagePower(magicNumber));
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(2);
        }
    }
}