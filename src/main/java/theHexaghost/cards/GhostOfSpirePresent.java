package theHexaghost.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sneckomod.SneckoMod;
import theHexaghost.HexaMod;
import theHexaghost.powers.GainEnergyAtTheStartOfTurnPower;
import theHexaghost.powers.StopFromAdvancingPower;

public class GhostOfSpirePresent extends AbstractHexaCard {

    public final static String ID = makeID("GhostOfSpirePresent");

    //stupid intellij stuff POWER, SELF, UNCOMMON

    private static final int MAGIC = 1;

    public GhostOfSpirePresent() {
        super(ID, 1, CardType.POWER, CardRarity.RARE, CardTarget.SELF);
        tags.add(HexaMod.GHOSTWHEELCARD);
        this.tags.add(SneckoMod.BANNEDFORSNECKO);
        HexaMod.loadJokeCardImage(this, "GhostOfSpirePresent.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new GainEnergyAtTheStartOfTurnPower(1));
        applyToSelf(new StopFromAdvancingPower());
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(0);
        }
    }
}