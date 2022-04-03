package timeeater.cards;

import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import timeeater.actions.SuspendAction;
import timeeater.cards.AbstractTimeEaterCard;

import static timeeater.TimeEaterMod.makeID;
import static timeeater.util.Wiz.*;

public class BorrowedTime extends AbstractTimeEaterCard {
    public final static String ID = makeID("BorrowedTime");
    // intellij stuff skill, self, uncommon, , , , , 2, -1

    public BorrowedTime() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 2;
        cardsToPreview = new VoidCard();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new GainEnergyAction(3));
        for (int i = 0; i < magicNumber; i++) {
            atb(new SuspendAction(new VoidCard()));
        }
    }

    public void upp() {
        upgradeMagicNumber(-1);
    }
}