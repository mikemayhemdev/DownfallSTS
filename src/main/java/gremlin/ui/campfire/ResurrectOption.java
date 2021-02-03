package gremlin.ui.campfire;

import basemod.ReflectionHacks;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.MathHelper;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.CampfireUI;
import com.megacrit.cardcrawl.rooms.RestRoom;
import com.megacrit.cardcrawl.ui.campfire.AbstractCampfireOption;
import gremlin.GremlinMod;
import gremlin.characters.GremlinCharacter;

public class ResurrectOption extends AbstractCampfireOption {
    private static final UIStrings uiStrings;
    public static final String[] TEXT;

    private boolean used;
    private boolean hacked;

    public ResurrectOption() {
        this.usable = ((GremlinCharacter)AbstractDungeon.player).canRez();
        updateImage();
    }

    public void updateImage() {
        this.label = TEXT[0];
        if (this.usable) {
            this.img = ImageMaster.loadImage(GremlinMod.getResourcePath("ui/campfire/resurrect.png"));
        } else {
            this.img = ImageMaster.loadImage(GremlinMod.getResourcePath("ui/campfire/resurrect_unusable.png"));
        }
        if (!this.used) {
            if (this.usable) {
                this.description += TEXT[1];
            } else {
                this.description = TEXT[2];
            }
        } else {
            this.description = TEXT[2];
        }
    }

    @Override
    public void useOption() {
        CardCrawlGame.sound.play("EVENT_SPIRITS");
        AbstractDungeon.player.decreaseMaxHealth(1);
        if(AbstractDungeon.player instanceof GremlinCharacter){
            ((GremlinCharacter)AbstractDungeon.player).resurrect();
        }
        this.used = true;
        AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMPLETE;
    }

    @Override
    public void update() {
        float hackScale = (float) ReflectionHacks.getPrivate(this, AbstractCampfireOption.class, "scale");

        if (this.hb.hovered) {

            if (!this.hb.clickStarted) {
                ReflectionHacks.setPrivate(this, AbstractCampfireOption.class, "scale", MathHelper.scaleLerpSnap(hackScale, Settings.scale));
                ReflectionHacks.setPrivate(this, AbstractCampfireOption.class, "scale", MathHelper.scaleLerpSnap(hackScale, Settings.scale));

            } else {
                ReflectionHacks.setPrivate(this, AbstractCampfireOption.class, "scale", MathHelper.scaleLerpSnap(hackScale, 0.9F * Settings.scale));

            }
        } else {
            ReflectionHacks.setPrivate(this, AbstractCampfireOption.class, "scale", MathHelper.scaleLerpSnap(hackScale, 0.9F * Settings.scale));
        }

        super.update();

        if (!this.used) {
            if (!((GremlinCharacter)AbstractDungeon.player).canRez() && this.usable) {
                this.usable = false;
                updateImage();
            }
            if (((GremlinCharacter)AbstractDungeon.player).canRez() && !this.usable) {
                this.usable = true;
                updateImage();
            }
        }

        CampfireUI campfire = ((RestRoom) AbstractDungeon.getCurrRoom()).campfireUI;


        if (this.used && !this.hacked) {
            this.hacked = true;
            campfire.somethingSelected = false;
            campfire.touchOption = null;
            campfire.confirmButton.hide();
            campfire.confirmButton.hideInstantly();
            campfire.confirmButton.isDisabled = true;

            AbstractDungeon.overlayMenu.proceedButton.hide();
            AbstractDungeon.overlayMenu.proceedButton.hideInstantly();
        }
    }

    static {
        uiStrings = CardCrawlGame.languagePack.getUIString("Gremlin:ResurrectOption");
        TEXT = ResurrectOption.uiStrings.TEXT;
    }
}
