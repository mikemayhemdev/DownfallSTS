package theHexaghost.cards;

import basemod.helpers.BaseModCardTags;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sneckomod.SneckoMod;
import theHexaghost.HexaMod;
import theHexaghost.powers.EnhancePower;
import theHexaghost.powers.HexalevelPower;

public class HexaLevel extends AbstractHexaCard {

    public final static String ID = makeID("HexaLevel");

    //infernal form

    private static final int MAGIC = 1;

    public HexaLevel() {
        super(ID, 3, CardType.POWER, CardRarity.RARE, CardTarget.SELF);
        baseMagicNumber = magicNumber = MAGIC;
        tags.add(HexaMod.GHOSTWHEELCARD);
        this.tags.add(SneckoMod.BANNEDFORSNECKO);
        tags.add(BaseModCardTags.FORM);
        HexaMod.loadJokeCardImage(this, "HexaLevel.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        //applyToSelf(new HexalevelPower(magicNumber));
        if (upgraded) applyToSelf(new EnhancePower(magicNumber));
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(1);
//            rawDescription = UPGRADE_DESCRIPTION;
//            initializeDescription();
        }
    }
}