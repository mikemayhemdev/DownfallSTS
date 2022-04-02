package timeeater.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import timeeater.actions.SuspendTopCardAction;
import timeeater.cards.AbstractTimeEaterCard;

import static timeeater.TimeEaterMod.makeID;
import static timeeater.util.Wiz.*;

public class Planning extends AbstractTimeEaterCard {
    public final static String ID = makeID("Planning");
    // intellij stuff skill, self, common, , , , , 2, 1

    public Planning() {
        super(ID, 0, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 2;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < magicNumber; i++) {
            atb(new SuspendTopCardAction());
        }
    }

    public void upp() {
        upgradeMagicNumber(1);
    }
}