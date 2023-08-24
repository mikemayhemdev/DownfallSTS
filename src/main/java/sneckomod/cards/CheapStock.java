package sneckomod.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sneckomod.powers.CheapStockPower;
import sneckomod.SneckoMod;

public class CheapStock extends AbstractSneckoCard {

    public final static String ID = makeID("CheapStock");

    //stupid intellij stuff POWER, SELF, UNCOMMON

    public CheapStock() {
        super(ID, 2, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
        SneckoMod.loadJokeCardImage(this, "CheapStock.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new CheapStockPower(1));
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(1);
        }
    }
}