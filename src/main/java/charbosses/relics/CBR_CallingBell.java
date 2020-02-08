package charbosses.relics;

import charbosses.bosses.AbstractCharBoss;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.curses.CurseOfTheBell;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.CallingBell;

public class CBR_CallingBell extends AbstractCharbossRelic {

    public CBR_CallingBell() {
        super(new CallingBell());
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public void onEquip() {
        final AbstractCard bellCurse = new CurseOfTheBell();
        AbstractCharBoss.boss.masterDeck.addToTop(bellCurse.makeCopy());
        AbstractCharBoss.boss.relicBudget += 3;
        AbstractCharBoss.boss.uncommonRelicBudget += 1;
        AbstractCharBoss.boss.rareRelicBudget += 1;
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
