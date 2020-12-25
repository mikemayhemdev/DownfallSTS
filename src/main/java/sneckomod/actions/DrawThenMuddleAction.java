package sneckomod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.EmptyDeckShuffleAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class DrawThenMuddleAction extends AbstractGameAction {
    private int bruh;
    private AbstractPlayer p;

    public DrawThenMuddleAction(int amount) {
        bruh = amount;
        p = AbstractDungeon.player;
    }

    @Override
    public void update() {
        isDone = true;
        if (AbstractDungeon.player.drawPile.size() + AbstractDungeon.player.discardPile.size() < bruh) {
        } else if (p.drawPile.isEmpty() || p.drawPile.size() - 1 <= bruh) {
            for (int i = 0; i < bruh; i++) {
                int r = i;
                addToTop(new AbstractGameAction() {
                    @Override
                    public void update() {
                        isDone = true;
                        AbstractCard q = p.drawPile.getNCardFromTop(r);
                        addToTop(new MuddleAction(q));
                    }
                });
            }
            addToTop(new DrawCardAction(p, bruh));
            addToTop(new EmptyDeckShuffleAction());// 34
        } else {
            isDone = true;
            for (int i = 0; i < bruh; i++) {
                int r = i;
                addToTop(new AbstractGameAction() {
                    @Override
                    public void update() {
                        isDone = true;
                        AbstractCard q = p.drawPile.getNCardFromTop(r);
                        addToTop(new MuddleAction(q));
                    }
                });
            }
            addToTop(new DrawCardAction(p, bruh));
        }
    }
}
