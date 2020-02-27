package charbosses.relics;

import charbosses.bosses.AbstractCharBoss;
import charbosses.cards.AbstractBossCard;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.EmptyCage;
import com.megacrit.cardcrawl.relics.PeacePipe;

import java.util.ArrayList;

public class CBR_PeacePipe extends AbstractCharbossRelic {

    public CBR_PeacePipe() {
        super(new PeacePipe());
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public void modifyCardsOnCollect(ArrayList<AbstractBossCard> groupToModify) {
        AbstractCharBoss.boss.chosenArchetype.removeBasicCard("Peace Pipe");
        AbstractCharBoss.boss.chosenArchetype.removeBasicCard("Peace Pipe");
    }

    @Override
    public void onEquip() {

    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_PeacePipe();
    }
}
