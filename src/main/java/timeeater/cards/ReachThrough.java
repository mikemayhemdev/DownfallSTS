package timeeater.cards;

import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsCenteredAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import timeeater.actions.SuspendAction;
import timeeater.cards.AbstractTimeEaterCard;

import java.util.ArrayList;

import static timeeater.TimeEaterMod.makeID;
import static timeeater.util.Wiz.*;

public class ReachThrough extends AbstractTimeEaterCard {
    public final static String ID = makeID("ReachThrough");
    // intellij stuff skill, self, uncommon, , , , , , 

    public ReachThrough() {
        super(ID, 0, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        ArrayList<AbstractCard> options = new ArrayList<AbstractCard>();
        options.addAll(AbstractDungeon.getCurrRoom().rewards.get(0).cards);
        atb(new SelectCardsCenteredAction(options, AbstractMemoryCard.uiStrings.TEXT[0], (cards) -> {
            AbstractCard q = cards.get(0);
            if (upgraded) q.updateCost(-99);
            atb(new SuspendAction(q));
        }));
    }

    public void upp() {
        uDesc();
    }
}