package charbosses.relics;

import charbosses.bosses.AbstractBossDeckArchetype;
import charbosses.bosses.AbstractCharBoss;
import charbosses.cards.AbstractBossCard;
import charbosses.cards.curses.EnCurseOfTheBell;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.CallingBell;

import java.util.ArrayList;

public class CBR_CallingBell extends AbstractCharbossRelic {
    public static final String ID = "CallingBell";

    public CBR_CallingBell() {
        super(new CallingBell());
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public void modifyCardsOnCollect(ArrayList<AbstractBossCard> groupToModify, int actIndex) {
        final AbstractCard bellCurse = new EnCurseOfTheBell();
        AbstractCharBoss.boss.masterDeck.addToTop(bellCurse.makeCopy());
        AbstractBossDeckArchetype.logger.info("Calling Bell added 1 " + bellCurse.name + ".");
        AbstractCharBoss.boss.chosenArchetype.addRandomGlobalRelic(actIndex, AbstractCharBoss.boss, "Calling Bell", groupToModify);
        AbstractCharBoss.boss.chosenArchetype.addRandomGlobalRelic(actIndex, AbstractCharBoss.boss, "Calling Bell", groupToModify);
        AbstractCharBoss.boss.chosenArchetype.addRandomGlobalRelic(actIndex, AbstractCharBoss.boss, "Calling Bell", groupToModify);
    }

    @Override
    public void update() {
        super.update();
        if (this.hb.hovered && InputHelper.justClickedLeft) {
            CardCrawlGame.sound.playA("BELL", MathUtils.random(-0.2f, -0.3f));
            this.flash();
        }
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_CallingBell();
    }
}
