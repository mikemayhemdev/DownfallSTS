package theHexaghost.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sneckomod.SneckoMod;
import theHexaghost.HexaMod;
import theHexaghost.powers.FuturePower;

public class GhostOfSpireFuture extends AbstractHexaCard {
    public final static String ID = makeID("GhostOfSpireFuture");
    //speedrunning (old name) into shadow
    public GhostOfSpireFuture() {
        super(ID, 2, CardType.POWER, CardRarity.RARE, CardTarget.SELF);
        magicNumber = baseMagicNumber= 1;
        this.isEthereal = true;
        this.tags.add(SneckoMod.BANNEDFORSNECKO);
        HexaMod.loadJokeCardImage(this, "GhostOfSpireFuture.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
//        if(upgraded){
//            applyToSelf(new FuturePower(magicNumber, true));
//        }else{
            applyToSelf(new FuturePower(this.magicNumber, false));
//        }

    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            this.isEthereal = false;
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
