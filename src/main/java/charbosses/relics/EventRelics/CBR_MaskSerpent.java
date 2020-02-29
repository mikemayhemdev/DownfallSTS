package charbosses.relics.EventRelics;

import charbosses.bosses.AbstractCharBoss;
import charbosses.cards.AbstractBossCard;
import charbosses.relics.AbstractCharbossRelic;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.FaceOfCleric;
import com.megacrit.cardcrawl.relics.SsserpentHead;

import java.util.ArrayList;

public class CBR_MaskSerpent extends AbstractCharbossRelic {
    public static final String ID = "CBRSerpentHead";
    public CBR_MaskSerpent() {
        super(new SsserpentHead());
        this.tier = RelicTier.SPECIAL;
    }

    @Override
    public void modifyCardsOnCollect(ArrayList<AbstractBossCard> groupToModify, int actIndex) {
        AbstractCharBoss.boss.chosenArchetype.removeBasicCard("Empty Cage");
        AbstractCharBoss.boss.chosenArchetype.removeBasicCard("Empty Cage");
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_MaskSerpent();
    }
}
