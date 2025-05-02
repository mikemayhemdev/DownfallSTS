package awakenedOne.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.CuriosityPower;

import static awakenedOne.AwakenedOneMod.makeID;
import static awakenedOne.util.Wiz.applyToSelfTop;

public class Curiosity extends AbstractAwakenedCard {
    public final static String ID = makeID(Curiosity.class.getSimpleName());
    // intellij stuff skill, enemy, uncommon, , , , , 3, 1

    public Curiosity() {
        super(ID, 3, CardType.POWER, CardRarity.RARE, CardTarget.SELF);
        baseMagicNumber = magicNumber = 2;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelfTop(new CuriosityPower(p, magicNumber));
    }

    public void upp() {
        upgradeMagicNumber(1);
    }
}