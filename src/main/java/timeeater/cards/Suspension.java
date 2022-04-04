package timeeater.cards;

import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import timeeater.suspend.SuspendHelper;

import static timeeater.TimeEaterMod.makeID;
import static timeeater.util.Wiz.atb;

public class Suspension extends AbstractTimeEaterCard {
    public final static String ID = makeID("Suspension");
    // intellij stuff skill, self, basic, , , , , , 

    public Suspension() {
        super(ID, 0, CardType.SKILL, CardRarity.BASIC, CardTarget.SELF);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (upgraded) {
            atb(new DrawCardAction(1));
        }
        atb(new SelectCardsInHandAction(cardStrings.EXTENDED_DESCRIPTION[0], (cards) -> {
            AbstractCard q = cards.get(0);
            AbstractDungeon.player.hand.removeCard(q);
            AbstractDungeon.handCardSelectScreen.selectedCards.removeCard(q);
            SuspendHelper.suspend(q.makeSameInstanceOf());
        }));
    }

    public void upp() {
        uDesc();
    }
}