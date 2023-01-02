package awakenedOne.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static awakenedOne.AwakenedOneMod.makeID;
import static awakenedOne.util.Wiz.awakenedAmt;
import static awakenedOne.util.Wiz.makeInHand;

public class PanickedRitual extends AbstractAwakenedCard {
    public final static String ID = makeID(PanickedRitual.class.getSimpleName());
    // intellij stuff skill, self, uncommon, , , , , , 

    public PanickedRitual() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        int amt = awakenedAmt();
        AbstractCard c = AbstractDungeon.returnTrulyRandomCardInCombat().makeCopy();
        c.setCostForTurn(0);
        if (amt >= 1) {
            c.upgrade();
        }
        makeInHand(c);
        if (amt >= 3) {
            makeInHand(c);
        }
    }

    public void upp() {
        upgradeBaseCost(0);
    }
}