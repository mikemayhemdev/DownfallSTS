package awakenedOne.cards;

import awakenedOne.cards.AbstractAwakenedCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.EnergizedPower;

import static awakenedOne.AwakenedOneMod.makeID;
import static awakenedOne.util.Wiz.*;

public class VoidChannel extends AbstractAwakenedCard {
    public final static String ID = makeID(VoidChannel.class.getSimpleName());
    // intellij stuff skill, self, basic, , , , , 1, 1

    public VoidChannel() {
        super(ID, 0, CardType.SKILL, CardRarity.BASIC, CardTarget.SELF);
        baseMagicNumber = magicNumber = 1;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new EnergizedPower(p, magicNumber));
    }

    public void upp() {
        upgradeMagicNumber(1);
    }
}