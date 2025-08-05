package awakenedOne.cards;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static awakenedOne.AwakenedOneMod.*;

public class DesperatePrayer extends AbstractAwakenedCard {
    public final static String ID = makeID(DesperatePrayer.class.getSimpleName());
    // intellij stuff skill, self, basic, , , 5, 3, ,

    public DesperatePrayer() {
        super(ID, 3, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        loadJokeCardImage(this, makeBetaCardPath(DesperatePrayer.class.getSimpleName() + ".png"));
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractCard c = AbstractDungeon.returnTrulyRandomCardInCombat(CardType.ATTACK).makeCopy();
        c.setCostForTurn(0);
        //if (upgraded) c.upgrade();
        this.addToBot(new MakeTempCardInHandAction(c, true));
        AbstractCard d = AbstractDungeon.returnTrulyRandomCardInCombat(CardType.SKILL).makeCopy();
        d.setCostForTurn(0);
        //if (upgraded) d.upgrade();
        this.addToBot(new MakeTempCardInHandAction(d, true));
        AbstractCard e = AbstractDungeon.returnTrulyRandomCardInCombat(CardType.POWER).makeCopy();
        e.setCostForTurn(0);
        //if (upgraded) e.upgrade();
        this.addToBot(new MakeTempCardInHandAction(e, true));
    }

    public void upp() {
        upgradeBaseCost(2);

    }
}