package timeeater.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import timeeater.cards.AbstractTimeEaterCard;
import timeeater.powers.GoldHourglassPower;

import static timeeater.TimeEaterMod.makeID;
import static timeeater.util.Wiz.*;

public class GoldHourglass extends AbstractTimeEaterCard {
    public final static String ID = makeID("GoldHourglass");
    // intellij stuff skill, self, rare, , , , , 6, 3

    public GoldHourglass() {
        super(ID, 1, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        baseMagicNumber = magicNumber = 6;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new GoldHourglassPower(magicNumber));
    }

    public void upp() {
        upgradeMagicNumber(3);
    }
}