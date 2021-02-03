package gremlin.ui.campfire;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.ui.campfire.AbstractCampfireOption;
import gremlin.GremlinMod;
import gremlin.characters.GremlinCharacter;

public class ResurrectOption extends AbstractCampfireOption {
    private static final UIStrings uiStrings;
    public static final String[] TEXT;

    public ResurrectOption() {
        this.label = ResurrectOption.TEXT[0];
        this.description = ResurrectOption.TEXT[1];
        this.img = ImageMaster.loadImage(GremlinMod.getResourcePath("ui/campfire/resurrect.png"));
    }

    @Override
    public void useOption() {
        CardCrawlGame.sound.play("EVENT_SPIRITS");
        AbstractDungeon.player.decreaseMaxHealth(1);
        if(AbstractDungeon.player instanceof GremlinCharacter){
            ((GremlinCharacter)AbstractDungeon.player).resurrect();
        }
        AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMPLETE;
    }

    static {
        uiStrings = CardCrawlGame.languagePack.getUIString("Gremlin:ResurrectOption");
        TEXT = ResurrectOption.uiStrings.TEXT;
    }
}
