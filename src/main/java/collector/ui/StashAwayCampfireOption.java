package collector.ui;

import basemod.ReflectionHacks;
import collector.CollectorCollection;
import collector.util.EssenceSystem;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.MathHelper;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.ui.campfire.AbstractCampfireOption;
import downfall.util.TextureLoader;


public class StashAwayCampfireOption extends AbstractCampfireOption {
    public static final String[] DESCRIPTIONS;
    private static final UIStrings UI_STRINGS;

    public static final int GOLD_GRANTED = 40;


    static {
        UI_STRINGS = CardCrawlGame.languagePack.getUIString("collector:StashAwayCampfireOption");
        DESCRIPTIONS = UI_STRINGS.TEXT;
    }

    //private ArrayList<String> idleMessages;
    public StashAwayCampfireOption() {
        this.label = DESCRIPTIONS[0];
        this.description = DESCRIPTIONS[1];
        boolean active = false;
        if (EssenceSystem.essenceCount() >= 10 && CollectorCollection.collection.size() > 0) {
            active = true;
        }

        updateImage(active);
        this.usable = active;

    }

    public void updateImage(boolean active) {
        if (active) {
            this.img = TextureLoader.getTexture("collectorResources/images/ui/release_campfire.png");
        } else {
            this.img = TextureLoader.getTexture("collectorResources/images/ui/release_campfire_disabled.png");
        }
    }

    @Override
    public void useOption() {
        if (this.usable) {
            StashAwayCampfireEffect e = new StashAwayCampfireEffect();
            AbstractDungeon.effectList.add(e);
        }
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
