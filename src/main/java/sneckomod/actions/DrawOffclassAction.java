package sneckomod.actions;

import basemod.BaseMod;
import com.evacipated.cardcrawl.mod.stslib.actions.common.FetchAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import hermit.util.Wiz;
import sneckomod.actions.MuddleAction;

import java.util.stream.Collectors;

public class DrawOffclassAction extends AbstractGameAction {
    private int draw;
    private AbstractPlayer p;

    public DrawOffclassAction(int amount) {
        draw = amount;
        p = AbstractDungeon.player;
    }

    @Override
    public void update() {
        int count = 0;

        // fully loaded code
        CardGroup tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        tmp.group.addAll(AbstractDungeon.player.drawPile.group.stream()
                .filter(c -> c.color != AbstractDungeon.player.getCardColor())
                .limit(draw)
                .collect(Collectors.toList()));

        for (AbstractCard c : tmp.group) {
            if (Wiz.hand().size() < BaseMod.MAX_HAND_SIZE) {
                addToBot(new FetchAction(Wiz.p().drawPile, card -> card == c));
                count++;
                if (count >= draw) break;
            }
        }
        isDone = true;
    }
}