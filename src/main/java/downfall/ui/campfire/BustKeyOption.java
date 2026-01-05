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
    private int lastKnownKeysBroken = 0;

    private int const_FirstKeyCardsShown = 5;
    private int const_FirstKeyCardsToGain = 1;
    private AbstractCard.CardRarity const_FirstKeyCardsRarity = AbstractCard.CardRarity.COMMON;
    private int const_FirstKeySoulsCost = 25;

    private int const_SecondKeyCardsShown = 4;
    private int const_SecondKeyCardsToGain = 1;
    private AbstractCard.CardRarity const_SecondKeyCardsRarity = AbstractCard.CardRarity.UNCOMMON;
    private int const_SecondKeySoulsCost = 75;

    private int const_ThirdKeyCardsShown = 3;
    private int const_ThirdKeyCardsToGain = 1;
    private AbstractCard.CardRarity const_ThirdKeyCardsRarity = AbstractCard.CardRarity.RARE;
    private int const_ThirdKeySoulsCost = 125;

    public static ArrayList<AbstractCard> cardsChosen = new ArrayList<>();

    public BustKeyOption() {
        this(Keys.RUBY);
    }

    public BustKeyOption(Keys key) {
        this.key = key;
        calcCost(key);
        lastKnownKeysBroken = downfallMod.getNumKeysBroken();
        if (AbstractDungeon.player.gold < soulToCost) {
            this.usable = false;
            updateImage(key);
        } else {
            this.usable = true;
            updateImage(key);
        }
    }
    public void calcCost(Keys key) {
        switch (downfallMod.getNumKeysBroken()) {
            case 1:
                soulToCost = const_SecondKeySoulsCost;
                break;
            case 2:
                soulToCost = const_ThirdKeySoulsCost;
                break;
            default:
                soulToCost = const_FirstKeySoulsCost;
        }
    }

    public void updateImage(Keys key) {

        switch (key) {
            case SAPPHIRE:
                this.label = TEXT[1];
                if (this.usable) {
                    this.img = TextureLoader.getTexture(downfallMod.assetPath("images/ui/campfire/sapphire.png"));
                } else {
                    this.img = TextureLoader.getTexture(downfallMod.assetPath("images/ui/campfire/sapphireDisabled.png"));
                }
                break;
            case EMERALD:
                this.label = TEXT[2];
                if (this.usable) {
                    this.img = TextureLoader.getTexture(downfallMod.assetPath("images/ui/campfire/emerald.png"));
                } else {
                    this.img = TextureLoader.getTexture(downfallMod.assetPath("images/ui/campfire/emeraldDisabled.png"));
                }
                break;
            default:
                this.label = TEXT[0];
                if (this.usable) {
                    this.img = TextureLoader.getTexture(downfallMod.assetPath("images/ui/campfire/ruby.png"));
                } else {
                    this.img = TextureLoader.getTexture(downfallMod.assetPath("images/ui/campfire/rubyDisabled.png"));
                }
        }

        this.description = TEXT[3];

        switch (downfallMod.getNumKeysBroken()) {
            case 1:
                if (!this.used) {
                    if (this.usable) {
                        this.description = this.description + soulToCost + TEXT[11] + const_SecondKeyCardsToGain + TEXT[12] + const_SecondKeyCardsShown + TEXT[5] + TEXT[13];
                    } else {
                        this.description = TEXT[8] + soulToCost + TEXT[9];
                    }
                } else {
                    this.description = TEXT[7]; //"Key Shattered!"
                }
                break;
            case 2:
                if (!this.used) {
                    if (this.usable) {
                        this.description = this.description + soulToCost + TEXT[11] + const_ThirdKeyCardsToGain + TEXT[12] + const_ThirdKeyCardsShown + TEXT[6] + TEXT[13];
                    } else {
                        this.description = TEXT[8] + soulToCost + TEXT[9];
                    }
                } else {
                    this.description = TEXT[7]; //"Key Shattered!"
                }
                break;
            default:
                if (!this.used) {
                    if (this.usable) {
                        this.description = this.description + soulToCost + TEXT[11] + const_FirstKeyCardsToGain + TEXT[12] + const_FirstKeyCardsShown + TEXT[4] + TEXT[13];
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
            if (lastKnownKeysBroken != downfallMod.getNumKeysBroken() || (AbstractDungeon.player.gold < soulToCost && this.usable)) {
                lastKnownKeysBroken = downfallMod.getNumKeysBroken();
                this.usable = false;
                calcCost(key);
                updateImage(key);
            }
            if (lastKnownKeysBroken != downfallMod.getNumKeysBroken() || (AbstractDungeon.player.gold >= soulToCost && !this.usable)) {
                lastKnownKeysBroken = downfallMod.getNumKeysBroken();
                this.usable = true;
                calcCost(key);
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
                AbstractDungeon.gridSelectScreen.selectedCards.clear();
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
            switch (downfallMod.getNumKeysBroken()) {
                case 1:
                    AbstractDungeon.effectList.add(new BustKeyEffect(const_SecondKeyCardsToGain, const_SecondKeyCardsShown, const_SecondKeyCardsRarity, TEXT[5]));
                    break;
                case 2:
                    AbstractDungeon.effectList.add(new BustKeyEffect(const_ThirdKeyCardsToGain, const_ThirdKeyCardsShown, const_ThirdKeyCardsRarity, TEXT[6]));
                    break;
                default:
                    AbstractDungeon.effectList.add(new BustKeyEffect(const_FirstKeyCardsToGain, const_FirstKeyCardsShown, const_FirstKeyCardsRarity, TEXT[4]));
                    }
            switch (this.key) {
                case SAPPHIRE:
                    AddBustKeyButtonPatches.KeyFields.bustedSapphire.set(p, true);
                case EMERALD:
                    AddBustKeyButtonPatches.KeyFields.bustedEmerald.set(p, true);
                    break;
                default:
                    AddBustKeyButtonPatches.KeyFields.bustedRuby.set(p, true);

            }
            updateImage(key);
        }

    }

    public enum Keys {
        RUBY, EMERALD, SAPPHIRE
    }
}
