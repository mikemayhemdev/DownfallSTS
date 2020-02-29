package charbosses.relics;

import charbosses.cards.AbstractBossCard;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.NlothsMask;

import java.util.ArrayList;

public class CBR_MaskNloth extends AbstractCharbossRelic {
    public static final String ID = "CBRNlothsMask";
    public CBR_MaskNloth() {
        super(new NlothsMask());
        this.tier = RelicTier.SPECIAL;
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public void modifyCardsOnCollect(ArrayList<AbstractBossCard> groupToModify, int actIndex) {
        for (int i = 0; i <= 2; i++) {
            if (this.owner.chosenArchetype.globalRelicAcquisitionsPerAct[i] > 0) {
                this.owner.chosenArchetype.globalRelicAcquisitionsPerAct[i] = 0;
                this.usedUp();
                this.counter = -2;
                return;
            }
        }

    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_MaskNloth();
    }
}
