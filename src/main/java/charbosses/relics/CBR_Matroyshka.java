package charbosses.relics;

import charbosses.bosses.AbstractCharBoss;
import charbosses.cards.AbstractBossCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.Matryoshka;
import downfall.downfallMod;

import java.util.ArrayList;

public class CBR_Matroyshka extends AbstractCharbossRelic {
    public static final String ID = "Matroyshka";
    private int numRelics;


    public CBR_Matroyshka() {
        super(new Matryoshka());
        this.counter = 2;
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + CardCrawlGame.languagePack.getRelicStrings(downfallMod.makeID(ID)).DESCRIPTIONS[0] + this.numRelics + CardCrawlGame.languagePack.getRelicStrings(downfallMod.makeID(ID)).DESCRIPTIONS[1];
    }

    @Override
    public void modifyCardsOnCollect(ArrayList<AbstractBossCard> groupToModify, int actIndex) {
        for (int i = actIndex; i < 3; i++) {
            AbstractCharBoss.boss.chosenArchetype.addRandomGlobalRelic(actIndex,this.owner,"Matroyshka", groupToModify);
            this.numRelics++;
            this.counter--;
            if (this.counter == 0){
                this.usedUp();
                break;
            }
        }

        this.description = getUpdatedDescription();
        this.refreshDescription();
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_Matroyshka();
    }
}
