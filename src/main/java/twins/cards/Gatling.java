package twins.cards;

import twins.powers.GatlingPower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Gatling extends AbstractTwinsCard {

    public final static String ID = makeID("Gatling");

    //stupid intellij stuff power, self, rare

    public Gatling() {
        super(ID, 2, CardType.POWER, CardRarity.RARE, CardTarget.SELF);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new GatlingPower(1));
    }

    public void upp() {
        upgradeBaseCost(1);
    }
}