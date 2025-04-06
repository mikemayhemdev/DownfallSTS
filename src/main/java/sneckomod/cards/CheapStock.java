package sneckomod.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sneckomod.SneckoMod;
import sneckomod.powers.CheapStockPower;

public class CheapStock extends AbstractSneckoCard {

    public final static String ID = makeID("CheapStock");

    public CheapStock() {
        super(ID, 1, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 1;
        SneckoMod.loadJokeCardImage(this, "CheapStock.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new CheapStockPower(this.magicNumber));
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