package timeeater.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.blue.Leap;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import timeeater.actions.SuspendAction;
import timeeater.cards.AbstractTimeEaterCard;

import static timeeater.TimeEaterMod.makeID;
import static timeeater.util.Wiz.*;

public class LeapAhead extends AbstractTimeEaterCard {
    public final static String ID = makeID("LeapAhead");
    // intellij stuff skill, self, uncommon, , , 9, 3, , 

    public LeapAhead() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseBlock = 9;
        cardsToPreview = new Leap();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        AbstractCard q = new Leap();
        if (upgraded) q.upgrade();
        atb(new SuspendAction(q));
    }

    public void upp() {
        upgradeBlock(3);
        AbstractCard q = new Leap();
        q.upgrade();
        cardsToPreview = q;
    }
}