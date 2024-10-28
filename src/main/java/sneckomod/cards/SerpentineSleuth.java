package sneckomod.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sneckomod.SneckoMod;
import sneckomod.powers.MudshieldPower;
import sneckomod.powers.SerpentineSleuthPower;

public class SerpentineSleuth extends AbstractSneckoCard {

    public final static String ID = makeID("SerpentineSleuth");

    //stupid intellij stuff POWER, SELF, RARE

    public SerpentineSleuth() {
        super(ID, 1, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 1;

        SneckoMod.loadJokeCardImage(this, "SerpentineSleuth.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new SerpentineSleuthPower(this.magicNumber));
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(1);
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}