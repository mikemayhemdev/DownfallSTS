package automaton.cards;

import automaton.powers.BarragePower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class Barrage extends AbstractBronzeCard {

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