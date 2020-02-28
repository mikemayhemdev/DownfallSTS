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
import com.megacrit.cardcrawl.relics.EmptyCage;

import java.util.ArrayList;

public class CBR_EmptyCage extends AbstractCharbossRelic {
    public static final String ID = "EmptyCage";

    public CBR_EmptyCage() {
        super(new EmptyCage());
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public void modifyCardsOnCollect(ArrayList<AbstractBossCard> groupToModify, int actIndex) {
        AbstractCharBoss.boss.chosenArchetype.removeBasicCard("Empty Cage");
        AbstractCharBoss.boss.chosenArchetype.removeBasicCard("Empty Cage");
    }

    @Override
    public void onEquip() {

    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_EmptyCage();
    }
}
