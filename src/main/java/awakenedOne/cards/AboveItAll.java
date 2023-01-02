package awakenedOne.cards;

import awakenedOne.powers.AboveItAllPower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static awakenedOne.AwakenedOneMod.makeID;
import static awakenedOne.util.Wiz.applyToSelf;

public class AboveItAll extends AbstractAwakenedCard {
    public final static String ID = makeID(AboveItAll.class.getSimpleName());
    // intellij stuff power, self, uncommon, , , , , , 

    public AboveItAll() {
        super(ID, 1, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 6;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new AboveItAllPower(magicNumber));
    }

    public void upp() {
        upgradeBaseCost(0);
    }
}