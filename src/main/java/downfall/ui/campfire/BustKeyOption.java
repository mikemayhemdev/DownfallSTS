package downfall.ui.campfire;

import basemod.ReflectionHacks;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.MathHelper;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.CampfireUI;
import com.megacrit.cardcrawl.rooms.RestRoom;
import com.megacrit.cardcrawl.ui.campfire.AbstractCampfireOption;
import downfall.downfallMod;
import downfall.patches.ui.campfire.AddBustKeyButtonPatches;
import downfall.relics.HeartBlessingBlue;
import downfall.relics.HeartBlessingGreen;
import downfall.relics.HeartBlessingRed;
import downfall.relics.Hecktoplasm;
import downfall.util.TextureLoader;
import downfall.vfx.campfire.BustKeyEffect;
import guardian.ui.EnhanceBonfireOption;

import java.util.ArrayList;

public class BustKeyOption extends AbstractCampfireOption {
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(downfallMod.makeID("BustKeyButton"));
    public static final String[] TEXT = uiStrings.TEXT;
    private static final int MAXHP_INC = 10;
    private Keys key;
    private boolean used;
    private boolean hacked;
    private float hackTime = 0F;
    private int soulToCost = 100;

    public BustKeyOption() {
        this(Keys.RUBY);
    }

    public BustKeyOption(Keys key) {
        this.key = key;
        if (AbstractDungeon.player.hasRelic(Hecktoplasm.ID)) soulToCost = 0;
        if (AbstractDungeon.player.gold < soulToCost) {
            this.usable = false;
            updateImage(key);
        } else {
            this.usable = true;
            updateImage(key);
        }
    }


    public void updateImage(Keys key) {
        if (AbstractDungeon.player.hasRelic(Hecktoplasm.ID)){
            this.description = TEXT[10];
        } else {
            this.description = TEXT[3];
        }
        switch (key) {
            case SAPPHIRE:
                this.label = TEXT[1];
                if (this.usable) {
                    this.img = TextureLoader.getTexture(downfallMod.assetPath("images/ui/campfire/sapphire.png"));
                } else {
                    this.img = TextureLoader.getTexture(downfallMod.assetPath("images/ui/campfire/sapphireDisabled.png"));
                }
                if (!this.used) {
                    if (this.usable) {
                        this.description += TEXT[5];
                    } else {
                        this.description = TEXT[8] + soulToCost + TEXT[9];
                    }
                } else {
                    this.description = TEXT[7];
                }
                break;
            case EMERALD:
                this.label = TEXT[2];
                if (this.usable) {
                    this.img = TextureLoader.getTexture(downfallMod.assetPath("images/ui/campfire/emerald.png"));
                } else {
                    this.img = TextureLoader.getTexture(downfallMod.assetPath("images/ui/campfire/emeraldDisabled.png"));
                }
                if (!this.used) {
                    if (this.usable) {
                        this.description += TEXT[6];
                    } else {
                        this.description = TEXT[8] + soulToCost + TEXT[9];
                    }
                } else {
                    this.description = TEXT[7];
                }
                break;
            default:
                this.label = TEXT[0];
                if (this.usable) {
                    this.img = TextureLoader.getTexture(downfallMod.assetPath("images/ui/campfire/ruby.png"));
                } else {
                    this.img = TextureLoader.getTexture(downfallMod.assetPath("images/ui/campfire/rubyDisabled.png"));
                }
                if (!this.used) {
                    if (this.usable) {
                        this.description += TEXT[4];
                    } else {
                        this.description = TEXT[8] + soulToCost + TEXT[9];
                    }
                } else {
                    this.description = TEXT[7];
                }
        }
    }

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
            if (AbstractDungeon.player.gold < soulToCost && this.usable) {
                this.usable = false;
                updateImage(key);
            }
            if (AbstractDungeon.player.gold >= soulToCost && !this.usable) {
                this.usable = true;
                updateImage(key);
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
            AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.INCOMPLETE;

        }
    }


    @Override
    public void useOption() {

        if (this.usable) {
            AbstractDungeon.effectList.add(new BustKeyEffect());
            AbstractPlayer p = AbstractDungeon.player;
            p.loseGold(soulToCost);
            this.used = true;
            this.usable = false;
            switch (key) {
                case SAPPHIRE:
                    AddBustKeyButtonPatches.KeyFields.bustedSapphire.set(p, true);
                    AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F, new HeartBlessingBlue());
                    break;
                case EMERALD:
                    AddBustKeyButtonPatches.KeyFields.bustedEmerald.set(p, true);
                    AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F, new HeartBlessingGreen());
                    break;
                default:
                    AddBustKeyButtonPatches.KeyFields.bustedRuby.set(p, true);
                    AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F, new HeartBlessingRed());
            }
            updateImage(key);
        }

    }

    public enum Keys {
        RUBY, EMERALD, SAPPHIRE
    }
}
