package sneckomod.ui;

import basemod.ReflectionHacks;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.MathHelper;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.ui.campfire.AbstractCampfireOption;
import guardian.GuardianMod;
import guardian.vfx.SocketGemEffect;
import sneckomod.SneckoMod;
import sneckomod.cards.unknowns.AbstractUnknownCard;
import sneckomod.patches.UnknownExtraUiPatch;
import sneckomod.relics.SuperSneckoSoul;

import java.lang.reflect.Array;
import java.util.ArrayList;


public class LockInCampfireOption extends AbstractCampfireOption {
    public static final String[] DESCRIPTIONS;
    private static final UIStrings UI_STRINGS;

    private ArrayList<AbstractCard> validCards = new ArrayList<>();

    static {
        UI_STRINGS = CardCrawlGame.languagePack.getUIString("sneckomod:LockInBonfireOptions");
        DESCRIPTIONS = UI_STRINGS.TEXT;

    }

    //private ArrayList<String> idleMessages;
    public LockInCampfireOption() {
        this.label = DESCRIPTIONS[0];
        boolean active = false;
        for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
            c.update();
            if (c instanceof AbstractUnknownCard){
                active = true;
            }
        }


        this.usable = active;
        if (active) {
            if (AbstractDungeon.player.hasRelic(SuperSneckoSoul.ID)) {
                this.description = DESCRIPTIONS[4];
            } else {
                this.description = DESCRIPTIONS[1];
            }
            this.img = ImageMaster.loadImage("sneckomodResources/images/ui/lockincampfire.png");
        } else {
            this.description = DESCRIPTIONS[2];
            this.img = ImageMaster.loadImage("sneckomodResources/images/ui/lockincampfiredisabled.png");
        }
    }

    @Override
    public void useOption() {
        if (this.usable) {
            AbstractDungeon.effectList.add(new LockInCampfireEffect());
            this.usable = false;
            this.img = ImageMaster.loadImage("sneckomodResources/images/ui/lockincampfiredisabled.png");
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
