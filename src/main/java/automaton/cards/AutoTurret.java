package automaton.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class AutoTurret extends AbstractBronzeCard {

    public final static String ID = makeID("AutoTurret");

    //stupid intellij stuff power, self, uncommon

    public AutoTurret() {
        super(ID, -1, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        //TODO: "At the start of your turn, Repeat X Peashooters"
    }

    public void upp() {
    }
}