package sneckomod.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sneckomod.SneckoMod;
import sneckomod.powers.FountainPower;

public class PoisonParadise extends AbstractSneckoCard {

    public final static String ID = SneckoMod.makeID("PoisonParadise");

    public PoisonParadise() {
        super(ID, 1, CardType.POWER, CardRarity.RARE, CardTarget.SELF);
        baseMagicNumber = magicNumber = 4;
        SneckoMod.loadJokeCardImage(this, "PoisonParadise.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new FountainPower(this.magicNumber));
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(2);
        }
    }
}
