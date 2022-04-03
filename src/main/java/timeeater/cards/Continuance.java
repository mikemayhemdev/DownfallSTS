package timeeater.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import timeeater.cards.AbstractTimeEaterCard;
import timeeater.powers.ContinuancePower;

import static timeeater.TimeEaterMod.makeID;
import static timeeater.util.Wiz.*;

public class Continuance extends AbstractTimeEaterCard {
    public final static String ID = makeID("Continuance");
    // intellij stuff power, self, uncommon, , , , , 2, 1

    public Continuance() {
        super(ID, 2, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 2;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new ContinuancePower(magicNumber));
    }

    public void upp() {
        upgradeMagicNumber(1);
    }
}