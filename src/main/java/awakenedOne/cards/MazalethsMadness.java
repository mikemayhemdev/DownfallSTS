package awakenedOne.cards;

import awakenedOne.powers.MazalethsMadnessPower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static awakenedOne.AwakenedOneMod.makeID;
import static awakenedOne.util.Wiz.applyToSelf;

public class MazalethsMadness extends AbstractAwakenedCard {
    public final static String ID = makeID(MazalethsMadness.class.getSimpleName());
    // intellij stuff power, self, rare, , , , , 9, 3

    public MazalethsMadness() {
        super(ID, 1, CardType.POWER, CardRarity.RARE, CardTarget.SELF);
        baseMagicNumber = magicNumber = 9;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new MazalethsMadnessPower(magicNumber));
    }

    public void upp() {
        upgradeMagicNumber(3);
    }
}