package timeEater.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import timeEater.powers.LunchBreakPower;

public class LunchBreak extends AbstractTimeCard {

    public final static String ID = makeID("LunchBreak");

    // intellij stuff power, self, uncommon

    public LunchBreak() {
        super(ID, 1, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 6;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new LunchBreakPower(magicNumber));
    }

    public void upp() {
        upgradeMagicNumber(2);
    }
}