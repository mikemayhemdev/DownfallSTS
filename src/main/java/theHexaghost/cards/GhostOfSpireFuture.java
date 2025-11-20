package theHexaghost.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sneckomod.SneckoMod;
import theHexaghost.HexaMod;
import theHexaghost.powers.FuturePower;

public class GhostOfSpireFuture extends AbstractHexaCard {
    public final static String ID = makeID("IntoShadow");
    //into shadow
    public GhostOfSpireFuture() {
        super(ID, 3, CardType.POWER, CardRarity.RARE, CardTarget.SELF);
        magicNumber = baseMagicNumber = 1;
        this.isEthereal = true;
        this.tags.add(SneckoMod.BANNEDFORSNECKO);
        HexaMod.loadJokeCardImage(this, "IntoShadow.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new FuturePower(this.magicNumber));
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            //upgradeBaseCost(2);
            this.isEthereal = false;
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
