package awakenedOne.actions;


import automaton.FunctionHelper;
import awakenedOne.cards.Caw;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import guardian.orbs.StasisOrb;

import java.util.Iterator;

public class GashCawAction extends AbstractGameAction {
    private AbstractCard card;

    public GashCawAction(AbstractCard card, int amount) {
        this.card = card;
        this.amount = amount;
    }

    public void update() {
        AbstractCard var10000 = this.card;
        var10000.baseDamage += this.amount;
        this.card.applyPowers();
        Iterator var1 = AbstractDungeon.player.discardPile.group.iterator();

        AbstractCard c;
        while(var1.hasNext()) {
            c = (AbstractCard)var1.next();
            if (c instanceof Caw) {
                c.baseDamage += this.amount;
                c.applyPowers();
            }
        }

        var1 = AbstractDungeon.player.drawPile.group.iterator();

        while(var1.hasNext()) {
            c = (AbstractCard)var1.next();
            if (c instanceof Caw) {
                c.baseDamage += this.amount;
                c.applyPowers();
            }
        }

        var1 = AbstractDungeon.player.hand.group.iterator();

        while(var1.hasNext()) {
            c = (AbstractCard)var1.next();
            if (c instanceof Caw) {
                c.baseDamage += this.amount;
                c.applyPowers();
            }
        }

        //Claw doesn't even hit Claws in the exhaust pile? Caw >>>> Claw, clearly.
        var1 = AbstractDungeon.player.exhaustPile.group.iterator();

        while(var1.hasNext()) {
            c = (AbstractCard)var1.next();
            if (c instanceof Caw) {
                c.baseDamage += this.amount;
                c.applyPowers();
            }
        }

        for (AbstractOrb o : AbstractDungeon.player.orbs) {
            if (o instanceof StasisOrb) {
                if (((StasisOrb) o).stasisCard instanceof Caw) {
                    ((StasisOrb) o).stasisCard.baseDamage += this.amount;
                    ((StasisOrb) o).stasisCard.applyPowers();
                }
            }
        }

        if (FunctionHelper.doStuff) {
            for (AbstractCard q : FunctionHelper.held.group) {
                if (q instanceof Caw) {
                    q.baseDamage += this.amount;
                }
            }
            FunctionHelper.genPreview();
        }

        this.isDone = true;
    }
}
