package guardian.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import guardian.orbs.StasisOrb;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;


public class PlaceRandom1CostIntoStasis extends AbstractGameAction {
    private int numCards;

    public PlaceRandom1CostIntoStasis(int numCards) {
        this.actionType = ActionType.DAMAGE;
        this.numCards = numCards;
    }

    public void update() {
        if (this.numCards == 0) {
            this.isDone = true;
        }

        ArrayList<String> tmp = new ArrayList();
        Iterator var3 = CardLibrary.cards.entrySet().iterator();

        while (var3.hasNext()) {
            Map.Entry<String, AbstractCard> c = (Map.Entry) var3.next();
            if (c.getValue().cost == 1 && c.getValue().color == AbstractDungeon.player.getCardColor()) {
                tmp.add(c.getKey());
            }
        }

        for (int i = 0; i < this.numCards; i++) {
            AbstractCard cStudy = CardLibrary.cards.get(tmp.get(AbstractDungeon.cardRng.random(0, tmp.size() - 1)));
            AbstractDungeon.actionManager.addToBottom(new ChannelAction(new StasisOrb(cStudy.makeStatEquivalentCopy(), false)));
            AbstractDungeon.actionManager.addToBottom(new WaitAction(0.1F));

        }


        this.isDone = true;
    }
}
