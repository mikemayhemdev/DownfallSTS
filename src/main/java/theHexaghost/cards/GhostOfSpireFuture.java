package theHexaghost.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sneckomod.SneckoMod;
import theHexaghost.HexaMod;
import theHexaghost.powers.FuturePower;

public class GhostOfSpireFuture extends AbstractHexaCard {
    public final static String ID = makeID("GhostOfSpireFuture");
    //speedrunning
    public GhostOfSpireFuture() {
        super(ID, 0, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
        magicNumber = baseMagicNumber= 1;
        this.tags.add(SneckoMod.BANNEDFORSNECKO);
        HexaMod.loadJokeCardImage(this, "GhostOfSpireFuture.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
//        if(upgraded){
//            applyToSelf(new FuturePower(magicNumber, true));
//        }else{
            applyToSelf(new FuturePower(magicNumber, false));
//        }

    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(1);
//            isInnate = true;
//            rawDescription = UPGRADE_DESCRIPTION;
//            initializeDescription();
        }
    }
}
