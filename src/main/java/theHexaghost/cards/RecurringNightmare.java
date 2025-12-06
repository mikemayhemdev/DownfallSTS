package theHexaghost.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sneckomod.SneckoMod;
import theHexaghost.HexaMod;
import downfall.util.CardIgnore;
import theHexaghost.actions.RecurringNightmareAction;
import theHexaghost.actions.RetractAction;

public class RecurringNightmare extends AbstractHexaCard {

    public final static String ID = makeID("NightmareVision");

    public RecurringNightmare() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        exhaust = true;
        baseMagicNumber = magicNumber = 1;
        tags.add(HexaMod.GHOSTWHEELCARD);
        this.tags.add(SneckoMod.BANNEDFORSNECKO);
        this.tags.add(CardTags.HEALING);
        HexaMod.loadJokeCardImage(this, "RecurringNightmare.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new RetractAction());
        atb(new RecurringNightmareAction(magicNumber));
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(0);
            //upgradeMagicNumber(1);
            //rawDescription = UPGRADE_DESCRIPTION;
            //initializeDescription();
        }
    }

//    @Override
//    public float getTitleFontSize() {
//        if(Settings.language != Settings.GameLanguage.ENG) {
//            return 19.0F;
//        }else {
//            return 20.0F;
//        }
//    }
}
