package timeeater.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import gremlin.cards.Ward;
import timeeater.actions.SuspendAction;
import timeeater.cards.AbstractTimeEaterCard;

import static timeeater.TimeEaterMod.makeID;
import static timeeater.util.Wiz.*;

public class Alacrity extends AbstractTimeEaterCard {
    public final static String ID = makeID("Alacrity");
    // intellij stuff skill, self, common, , , 9, 3, , 

    public Alacrity() {
        super(ID, 2, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseBlock = 9;
        cardsToPreview = new Ward();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        for (int i = 0; i < 3; i++) {
            AbstractCard q = new Ward();
            if (upgraded) q.upgrade();
            atb(new SuspendAction(q));
        }
    }

    public void upp() {
        upgradeBlock(3);
        AbstractCard q = new Ward();
        q.upgrade();
        cardsToPreview = q;
        uDesc();
    }
}