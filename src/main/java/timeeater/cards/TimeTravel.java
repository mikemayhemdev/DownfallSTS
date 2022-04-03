package timeeater.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import timeeater.actions.SuspendDiscardTopAction;
import timeeater.cards.AbstractTimeEaterCard;

import static timeeater.TimeEaterMod.makeID;
import static timeeater.util.Wiz.*;

public class TimeTravel extends AbstractTimeEaterCard {
    public final static String ID = makeID("TimeTravel");
    // intellij stuff skill, self, uncommon, , , , , 3, 1

    public TimeTravel() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 3;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new AbstractGameAction() {
            @Override
            public void update() {
                isDone = true;
                int x = Math.min(p.discardPile.size(), magicNumber);
                for (int i = 0; i < x; i++) {
                    att(new SuspendDiscardTopAction());
                }
            }
        });
    }

    public void upp() {
        upgradeMagicNumber(1);
    }
}