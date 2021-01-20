package downfall.relics;

import basemod.ReflectionHacks;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.CallingBell;
import com.megacrit.cardcrawl.relics.CursedKey;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import downfall.downfallMod;

public class ExtraCursedBell extends CallingBell {

    public static String ID = downfallMod.makeID("ExtraCursedBell");

    public ExtraCursedBell() {
        super();
        this.tier = RelicTier.SPECIAL;
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }


    @Override
    public void onEquip() {
        ReflectionHacks.setPrivate(this, CallingBell.class, "cardsReceived", false);
        CardGroup group = new CardGroup(com.megacrit.cardcrawl.cards.CardGroup.CardGroupType.UNSPECIFIED);
        AbstractCard bellCurse = new com.megacrit.cardcrawl.cards.curses.CurseOfTheBell();
        UnlockTracker.markCardAsSeen(bellCurse.cardID);
        group.addToBottom(bellCurse.makeCopy());
        group.addToBottom(bellCurse.makeCopy());

        AbstractDungeon.gridSelectScreen.openConfirmationGrid(group, this.DESCRIPTIONS[1]);
        CardCrawlGame.sound.playA("BELL", MathUtils.random(-0.2F, -0.3F));
    }

    @Override
    public AbstractRelic makeCopy() {
        return new ExtraCursedBell();
    }
}
