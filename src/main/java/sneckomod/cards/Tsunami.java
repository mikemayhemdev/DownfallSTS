package sneckomod.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sneckomod.SneckoMod;
import sneckomod.powers.BlunderGuardPower;

public class Tsunami extends AbstractSneckoCard {

    public final static String ID = makeID("Tsunami");

    public Tsunami() {
        super(ID, 1, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 4;
        SneckoMod.loadJokeCardImage(this, "Tsunami.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new BlunderGuardPower(this.magicNumber));
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(1);
        }
    }
}
