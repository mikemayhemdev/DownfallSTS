package automaton.cards;

import automaton.powers.LibraryPower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Library extends AbstractBronzeCard {

    public final static String ID = makeID("Library");

    //stupid intellij stuff power, self, rare

    public Library() {
        super(ID, 3, CardType.POWER, CardRarity.RARE, CardTarget.SELF);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new LibraryPower(1));
    }

    public void upp() {
        upgradeBaseCost(2);
    }
}