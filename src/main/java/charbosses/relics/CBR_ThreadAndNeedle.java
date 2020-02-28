package charbosses.relics;

import charbosses.bosses.AbstractCharBoss;
import charbosses.cards.AbstractBossCard;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.TinyHouse;

import java.util.ArrayList;

public class CBR_ThreadAndNeedle extends AbstractCharbossRelic {
    public static final String ID = "ThreadAndNeedle";

    public CBR_ThreadAndNeedle() {
        super(new TinyHouse());
    }

    @Override
    public void modifyCardsOnCollect(ArrayList<AbstractBossCard> groupToModify) {
        AbstractCharBoss.boss.chosenArchetype.removeBasicCard("Tiny House");
        AbstractCharBoss.boss.chosenArchetype.upgradeRandomCard("Tiny House");
        AbstractCharBoss.boss.chosenArchetype.addRandomGlobalClassCard("Tiny House");
    }

    @Override
    public void onEquip() {
        this.owner.increaseMaxHp(5, true);
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_ThreadAndNeedle();
    }
}
