package awakenedOne.cards;

import awakenedOne.powers.LibrarianPower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static awakenedOne.AwakenedOneMod.makeID;
import static awakenedOne.util.Wiz.applyToSelf;

public class Librarian extends AbstractAwakenedCard {
    public final static String ID = makeID(Librarian.class.getSimpleName());
    // intellij stuff power, self, rare, , , , , , 

    public Librarian() {
        super(ID, 1, CardType.POWER, CardRarity.RARE, CardTarget.SELF);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new LibrarianPower(1));
    }

    public void upp() {
        isInnate = true;
    }
}