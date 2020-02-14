package evilWithin.ui.campfire;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.ui.campfire.AbstractCampfireOption;
import evilWithin.EvilWithinMod;
import evilWithin.events.GremlinWheelGame_Rest;
import evilWithin.relics.GremlinWheel;
import evilWithin.util.TextureLoader;

public class WheelSpinButton extends AbstractCampfireOption {
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(EvilWithinMod.makeID("WheelSpinButton"));
    public static final String[] TEXT = uiStrings.TEXT;

    public WheelSpinButton(boolean bruh) {
        label = TEXT[0];
        description = TEXT[1];
        this.usable = bruh;
        this.img = TextureLoader.getTexture("evilWithinResources/images/ui/campfire/wheel.png");
    }

    @Override
    public void useOption() {
        AbstractDungeon.player.getRelic(GremlinWheel.ID).setCounter(AbstractDungeon.player.getRelic(GremlinWheel.ID).counter - 1);
        AbstractDungeon.getCurrRoom().event = new GremlinWheelGame_Rest();
        AbstractDungeon.getCurrRoom().event.onEnterRoom();
    }
}
