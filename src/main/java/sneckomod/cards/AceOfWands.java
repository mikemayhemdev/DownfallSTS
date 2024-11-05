package sneckomod.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sneckomod.SneckoMod;
import sneckomod.powers.AceOfWandsPower;

public class AceOfWands extends AbstractSneckoCard {

    public final static String ID = makeID("AceOfWands");

    //stupid intellij stuff POWER, SELF, RARE

    public AceOfWands() {
        super(ID, 3, CardType.POWER, CardRarity.RARE, CardTarget.SELF);
        baseMagicNumber = magicNumber = 4;
        isEthereal = true;
        SneckoMod.loadJokeCardImage(this, "AceOfWands.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new AceOfWandsPower(this.magicNumber));
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            isEthereal = false;
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}