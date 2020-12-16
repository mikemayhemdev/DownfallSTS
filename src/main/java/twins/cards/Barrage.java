package twins.cards;

import twins.powers.BarragePower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Barrage extends AbstractTwinsCard {

    public final static String ID = makeID("Barrage");

    //stupid intellij stuff power, self, uncommon

    public Barrage() {
        super(ID, 1, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new BarragePower(1));
    }

    public void upp() {
        upgradeBaseCost(1);
    }
}