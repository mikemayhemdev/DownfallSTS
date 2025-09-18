package downfall.ui.campfire;

import basemod.ReflectionHacks;
import champ.relics.DeflectingBracers;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.MathHelper;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.relics.Ectoplasm;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.CampfireUI;
import com.megacrit.cardcrawl.rooms.RestRoom;
import com.megacrit.cardcrawl.ui.campfire.AbstractCampfireOption;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import downfall.downfallMod;
import downfall.patches.ui.campfire.AddBustKeyButtonPatches;
import downfall.relics.*;
import downfall.util.TextureLoader;
import downfall.vfx.campfire.BustKeyEffect;
import slimebound.SlimeboundMod;

import java.util.ArrayList;

public class BustKeyOption extends AbstractCampfireOption {
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(downfallMod.makeID("BustKeyButton"));
    public static final String[] TEXT = uiStrings.TEXT;
    private Keys key;
    private boolean used;
    private boolean hacked;
    private float hackTime = 0F;
    private int soulToCost = 0;

    public static ArrayList<AbstractCard> cardsChosen = new ArrayList<>();

    public BustKeyOption() {
        this(Keys.RUBY);
    }

    public BustKeyOption(Keys key) {
        this.key = key;
        calcCost(key);
        if (AbstractDungeon.player.gold < soulToCost) {
            this.usable = false;
            updateImage(key);
        } else {
            this.usable = true;
            updateImage(key);
        }
    }
    public void calcCost(Keys key) {
        switch (key) {
            case SAPPHIRE:
                soulToCost = 65;
                break;
            case EMERALD:
                soulToCost = 80;
                break;
            default:
                soulToCost = 50;
        }
    }

    public void updateImage(Keys key) {

        this.description = TEXT[3];
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
                        this.description = this.description + soulToCost + TEXT[11] + TEXT[5];
                    } else {
                        this.description = TEXT[8] + soulToCost + TEXT[9];
                    }
                } else {
                    this.description = TEXT[7]; //"Key Shattered!"
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
                        this.description = this.description + soulToCost + TEXT[11] + TEXT[6];
                    } else {
                        this.description = TEXT[8] + soulToCost + TEXT[9];
                    }
                } else {
                    this.description = TEXT[7]; //"Key Shattered!"
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
                        this.description = this.description + soulToCost + TEXT[11] + TEXT[4];
                    } else {
                        this.description = TEXT[8] + soulToCost + TEXT[9];
                    }
                } else {
                    this.description = TEXT[7]; //"Key Shattered!"
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

        if (!cardsChosen.isEmpty()){
            SlimeboundMod.logger.info("SIZE IN OPTION " + cardsChosen.size());
            if (!cardsChosen.isEmpty()) {
                AbstractCard c = ((AbstractCard) cardsChosen.get(0)).makeCopy();
                if (cardsChosen.size() >= 2) {
                    AbstractDungeon.topLevelEffects.add(new ShowCardAndObtainEffect(c, (float) Settings.WIDTH * 0.4F, (float) Settings.HEIGHT * 0.4F));
                    c = ((AbstractCard) cardsChosen.get(1)).makeCopy();
                    AbstractDungeon.topLevelEffects.add(new ShowCardAndObtainEffect(c, (float) Settings.WIDTH * 0.6F, (float) Settings.HEIGHT * 0.6F));
                } else {
                    AbstractDungeon.topLevelEffects.add(new ShowCardAndObtainEffect(c, (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
                }
                cardsChosen.clear();
            }
        }
    }


    @Override
    public void useOption() {

        if (this.usable) {
            AbstractPlayer p = AbstractDungeon.player;
            p.loseGold(soulToCost);

            this.used = true;
            this.usable = false;
            switch (key) {
                case SAPPHIRE:

                    AbstractDungeon.effectList.add(new BustKeyEffect(2, 4, AbstractCard.CardRarity.UNCOMMON, TEXT[5]));
                    AddBustKeyButtonPatches.KeyFields.bustedSapphire.set(p, true);

                    /*
                    if (!AbstractDungeon.player.hasRelic(BurdenOfKnowledge.ID)) {
                        AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F, new HeartBlessingBlue());
                    }

                     */
                    break;
                case EMERALD:

                    AbstractDungeon.effectList.add(new BustKeyEffect(1, 3, AbstractCard.CardRarity.RARE, TEXT[6]));
                    AddBustKeyButtonPatches.KeyFields.bustedEmerald.set(p, true);

                    /*
                    if (!AbstractDungeon.player.hasRelic(BurdenOfKnowledge.ID)) {
                        AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F, new HeartBlessingGreen());
                    }
                     */
                    break;

                default:
                    AbstractDungeon.effectList.add(new BustKeyEffect(2, 6, AbstractCard.CardRarity.COMMON, TEXT[4]));
                    AddBustKeyButtonPatches.KeyFields.bustedRuby.set(p, true);

                    /*
                    if (!AbstractDungeon.player.hasRelic(BurdenOfKnowledge.ID)) {
                        AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F, new HeartBlessingRed());
                    }

                     */


                    }
            updateImage(key);
        }

    }

    public enum Keys {
        RUBY, EMERALD, SAPPHIRE
    }
}
