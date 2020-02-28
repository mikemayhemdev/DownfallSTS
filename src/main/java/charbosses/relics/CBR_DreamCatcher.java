package charbosses.relics;

import charbosses.bosses.AbstractCharBoss;
import charbosses.cards.AbstractBossCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.DreamCatcher;
import com.megacrit.cardcrawl.relics.TinyHouse;
import evilWithin.EvilWithinMod;
import slimebound.SlimeboundMod;

import java.util.ArrayList;

public class CBR_DreamCatcher extends AbstractCharbossRelic {
    public static final String ID = "DreamCatcher";
    private int numCards;

    public CBR_DreamCatcher() {
        super(new DreamCatcher());
    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + CardCrawlGame.languagePack.getRelicStrings(EvilWithinMod.makeID(ID)).DESCRIPTIONS[0] + this.numCards + CardCrawlGame.languagePack.getRelicStrings(EvilWithinMod.makeID(ID)).DESCRIPTIONS[1];
    }



    @Override
    public void modifyCardsOnCollect(ArrayList<AbstractBossCard> groupToModify, int actIndex) {
        SlimeboundMod.logger.info("Dream catcher received act index: " + actIndex);
        for (int i = actIndex; i < 3; i++) {
            AbstractCharBoss.boss.chosenArchetype.addRandomGlobalClassCard("Dream Catcher");
            AbstractCharBoss.boss.chosenArchetype.addRandomSynergyCard("Dream Catcher");
            this.numCards += 2;
        }

        this.description = getUpdatedDescription();
        this.refreshDescription();
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_DreamCatcher();
    }
}
