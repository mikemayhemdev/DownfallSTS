package sneckomod.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sneckomod.SneckoMod;
import sneckomod.powers.SerpentineSleuthPower;
import sneckomod.powers.SerpentsNestPower;

public class SerpentsNest extends AbstractSneckoCard {

    public final static String ID = makeID("SerpentsNest");

    //stupid intellij stuff POWER, SELF, RARE

    public SerpentsNest() {
        super(ID, 1, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 9;

        SneckoMod.loadJokeCardImage(this, "SerpentsNest.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new SerpentsNestPower(this.magicNumber));
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(3);
        }
    }
}