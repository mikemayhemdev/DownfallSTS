package charbosses.relics;

import charbosses.bosses.AbstractCharBoss;
import charbosses.cards.AbstractBossCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.DollysMirror;
import downfall.downfallMod;

import java.util.ArrayList;

public class CBR_DollysMirror extends AbstractCharbossRelic {
    public static final String ID = "DollysMirror";

    private String addedDesc = "";

    public CBR_DollysMirror() {
        super(new DollysMirror());
        this.tier = RelicTier.RARE;
    }

    public CBR_DollysMirror(String cardName) {
        super(new DollysMirror());
        this.tier = RelicTier.RARE;
        addedDesc = cardName;

        this.description = getUpdatedDescription();
        this.refreshDescription();
    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + this.addedDesc;
    }



    @Override
    public void modifyCardsOnCollect(ArrayList<AbstractBossCard> groupToModify, int actIndex) {
        AbstractCharBoss.boss.chosenArchetype.logger.info("Dolly's Mirror is trying to duplicate " + AbstractCharBoss.boss.chosenArchetype.signatureCardPerAct[actIndex].name);

        if (AbstractCharBoss.boss.chosenArchetype.signatureCardPerAct[actIndex] != null) {
            groupToModify.add(AbstractCharBoss.boss.chosenArchetype.signatureCardPerAct[actIndex]);
            AbstractCharBoss.boss.chosenArchetype.logger.info("Dolly's Mirror duplicated " + AbstractCharBoss.boss.chosenArchetype.signatureCardPerAct[actIndex].name);
            addedDesc += CardCrawlGame.languagePack.getRelicStrings(downfallMod.makeID(ID)).DESCRIPTIONS[0] + AbstractCharBoss.boss.chosenArchetype.signatureCardPerAct[actIndex].name + ".";
        }

        this.description = getUpdatedDescription();
        this.refreshDescription();
    }

    @Override
    public void onEquip() {

    }

    @Override
    public void update() {
        super.update();
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_DollysMirror();
    }
}
