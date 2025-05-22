package awakenedOne.cards;

import awakenedOne.powers.InvasionPower;
import awakenedOne.powers.LibrarianPower;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static awakenedOne.AwakenedOneMod.makeID;
import static awakenedOne.util.Wiz.applyToSelf;

public class Invasion extends AbstractAwakenedCard {
    public final static String ID = makeID(Invasion.class.getSimpleName());
    // intellij stuff power, self, rare, , , , , ,

    public Invasion() {
        super(ID, 3, CardType.POWER, CardRarity.RARE, CardTarget.SELF);
        cardsToPreview = new VoidCard();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new InvasionPower(1));
    }

    public void upp() {
        upgradeBaseCost(2);
    }
}