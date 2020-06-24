package guardian.ui;

import basemod.ReflectionHacks;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.MathHelper;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.ui.campfire.AbstractCampfireOption;
import guardian.GuardianMod;
import guardian.relics.PickAxe;
import guardian.vfx.CampfireFindGemsEffect;


public class FindGemsOption extends AbstractCampfireOption {
    public static final String[] DESCRIPTIONS;
    private static final UIStrings UI_STRINGS;

    static {
        UI_STRINGS = CardCrawlGame.languagePack.getUIString("Guardian:FindGemsOption");
        DESCRIPTIONS = UI_STRINGS.TEXT;

    }

    //private ArrayList<String> idleMessages;
    public FindGemsOption(boolean active) {
        this.label = DESCRIPTIONS[0];
        this.description = DESCRIPTIONS[1];

        this.usable = active;
        if (active) {
            this.description = DESCRIPTIONS[1];
            this.img = ImageMaster.loadImage(GuardianMod.getResourcePath("ui/minecampfire.png"));
        } else {
            this.description = DESCRIPTIONS[2];
            this.img = ImageMaster.loadImage(GuardianMod.getResourcePath("ui/minecampfiredisabled.png"));
        }
    }

    @Override
    public void useOption() {

        AbstractDungeon.effectList.add(new CampfireFindGemsEffect());
        AbstractDungeon.player.getRelic(PickAxe.ID).onTrigger();

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
    }
}
