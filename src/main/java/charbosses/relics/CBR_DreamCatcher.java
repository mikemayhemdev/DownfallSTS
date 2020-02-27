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

import java.util.ArrayList;

public class CBR_DreamCatcher extends AbstractCharbossRelic {
    public static final String ID = "DreamCatcher";
    private int numCards;

    public CBR_DreamCatcher() {
        super(new DreamCatcher());
    }

    private String setDescription(final AbstractPlayer.PlayerClass c) {
        return this.DESCRIPTIONS[0] + CardCrawlGame.languagePack.getRelicStrings(EvilWithinMod.makeID(ID)).DESCRIPTIONS[0] + this.numCards + CardCrawlGame.languagePack.getRelicStrings(EvilWithinMod.makeID(ID)).DESCRIPTIONS[1];
    }

    @Override
    public void modifyCardsOnCollect(ArrayList<AbstractBossCard> groupToModify) {
        for (int i = AbstractDungeon.actNum; i < 3; i++) {
            AbstractCharBoss.boss.chosenArchetype.addRandomGlobalClassCard("Tiny House");
            AbstractCharBoss.boss.chosenArchetype.addRandomSynergyCard("Tiny House");
            this.numCards += 2;
        }
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_DreamCatcher();
    }
}
