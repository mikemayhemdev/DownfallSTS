package timeeater.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import timeeater.cards.AbstractTimeEaterCard;
import timeeater.powers.MultiversePower;

import static timeeater.TimeEaterMod.makeID;
import static timeeater.util.Wiz.*;

public class Multiverse extends AbstractTimeEaterCard {
    public final static String ID = makeID("Multiverse");
    // intellij stuff power, self, rare, , , , , , 

    public Multiverse() {
        super(ID, 3, CardType.POWER, CardRarity.RARE, CardTarget.SELF);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new MultiversePower(1));
    }

    public void upp() {
        upgradeBaseCost(2);
    }
}