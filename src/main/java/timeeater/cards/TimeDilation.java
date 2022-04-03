package timeeater.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import timeeater.cards.AbstractTimeEaterCard;
import timeeater.powers.HastePower;
import timeeater.powers.TurnBasedSlowPower;

import static timeeater.TimeEaterMod.makeID;
import static timeeater.util.Wiz.*;

public class TimeDilation extends AbstractTimeEaterCard {
    public final static String ID = makeID("TimeDilation");
    // intellij stuff skill, all, uncommon, , , , , 2, 1

    public TimeDilation() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.ALL);
        baseMagicNumber = magicNumber = 2;
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new HastePower(magicNumber));
        forAllMonstersLiving(q -> applyToEnemy(q, new TurnBasedSlowPower(q, magicNumber)));
    }

    public void upp() {
        upgradeMagicNumber(1);
    }
}