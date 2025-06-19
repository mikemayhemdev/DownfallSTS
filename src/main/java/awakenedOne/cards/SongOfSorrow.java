package awakenedOne.cards;

import awakenedOne.powers.SongOfSorrowPower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static awakenedOne.AwakenedOneMod.makeID;
import static awakenedOne.util.Wiz.*;

public class SongOfSorrow extends AbstractAwakenedCard {
    public final static String ID = makeID(SongOfSorrow.class.getSimpleName());
    // intellij stuff power, self, rare, , , , , ,

    public SongOfSorrow() {
        super(ID, 1, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
        this.baseMagicNumber = 2;
        this.magicNumber = this.baseMagicNumber;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new SongOfSorrowPower(magicNumber));
    }

    @Override
    public void upp() {
        upgradeMagicNumber(1);
    }
}