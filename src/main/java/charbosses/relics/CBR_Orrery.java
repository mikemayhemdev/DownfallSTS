package charbosses.relics;

import charbosses.bosses.AbstractCharBoss;
import charbosses.cards.AbstractBossCard;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.Orrery;
import com.megacrit.cardcrawl.relics.TinyHouse;

import java.util.ArrayList;

public class CBR_Orrery extends AbstractCharbossRelic {
    public static final String ID = "Orrery";

    public CBR_Orrery() {
        super(new Orrery());
        this.tier = RelicTier.UNCOMMON;
    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public void modifyCardsOnCollect(ArrayList<AbstractBossCard> groupToModify, int actIndex) {
        AbstractCharBoss.boss.chosenArchetype.addRandomSynergyCard("Orrery");
        AbstractCharBoss.boss.chosenArchetype.addRandomSynergyCard("Orrery");
        AbstractCharBoss.boss.chosenArchetype.addRandomGlobalClassCard("Orrery");
        AbstractCharBoss.boss.chosenArchetype.addRandomGlobalClassCard("Orrery");
        AbstractCharBoss.boss.chosenArchetype.addRandomGlobalClassCard("Orrery");
    }

    @Override
    public void onEquip() {

    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_Orrery();
    }
}
