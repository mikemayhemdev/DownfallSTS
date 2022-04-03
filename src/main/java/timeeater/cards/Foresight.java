package timeeater.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.watcher.ForesightPower;
import timeeater.cards.AbstractTimeEaterCard;

import static timeeater.TimeEaterMod.makeID;
import static timeeater.util.Wiz.*;

public class Foresight extends AbstractTimeEaterCard {
    public final static String ID = makeID("Foresight");
    // intellij stuff power, self, uncommon, , , , , 3, 1

    public Foresight() {
        super(ID, 1, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 3;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new ForesightPower(p, magicNumber));
    }

    public void upp() {
        upgradeMagicNumber(1);
    }
}