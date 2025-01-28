package sneckomod;

import basemod.abstracts.CustomPlayer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ModHelper;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.scene.DefectVictoryNumberEffect;
import reskinContent.patches.CharacterSelectScreenPatches;
import reskinContent.reskinContent;
import sneckomod.cards.Defend;
import sneckomod.cards.SnekBite;
import sneckomod.cards.Strike;
import sneckomod.cards.TailWhip;
import sneckomod.cards.unknowns.*;
import sneckomod.relics.SneckoSoul;
import sneckomod.vfx.SneckoVictoryNumberEffect;

import java.util.ArrayList;

import static sneckomod.SneckoMod.*;
import static sneckomod.TheSnecko.Enums.SNECKO_CYAN;


public class TheSnecko extends CustomPlayer {
    private static final String[] orbTextures = {
            "sneckomodResources/images/char/orb/layer1.png",
            "sneckomodResources/images/char/orb/layer2.png",
            "sneckomodResources/images/char/orb/layer3.png",
            "sneckomodResources/images/char/orb/layer4.png",
            "sneckomodResources/images/char/orb/layer5.png",
            "sneckomodResources/images/char/orb/layer6.png",
            "sneckomodResources/images/char/orb/layer1d.png",
            "sneckomodResources/images/char/orb/layer2d.png",
            "sneckomodResources/images/char/orb/layer3d.png",
            "sneckomodResources/images/char/orb/layer4d.png",
            "sneckomodResources/images/char/orb/layer5d.png",};
    public static final String ID = makeID("theSnecko");
    private static final CharacterStrings characterStrings = CardCrawlGame.languagePack.getCharacterString(ID);
    private static final String[] NAMES = characterStrings.NAMES;
    private static final String[] TEXT = characterStrings.TEXT;

    public float renderscale = 1.2F;

    public TheSnecko(String name, PlayerClass setClass) {
        super(name, setClass, orbTextures, "sneckomodResources/images/char/orb/vfx.png", (String)null, (String)null);
        initializeClass(null,
                CharacterSelectScreenPatches.characters[3].skins[CharacterSelectScreenPatches.characters[3].reskinCount].SHOULDER1,
                CharacterSelectScreenPatches.characters[3].skins[CharacterSelectScreenPatches.characters[3].reskinCount].SHOULDER2,
                CharacterSelectScreenPatches.characters[3].skins[CharacterSelectScreenPatches.characters[3].reskinCount].CORPSE,
                getLoadout(), 10.0F, -20.0F, 300.0F, 300.0F, new EnergyManager(3));

//        if (CharacterSelectScreenPatches.characters[3].isOriginal()) {
//            initializeClass(null,
//                    SHOULDER1,
//                    SHOULDER2,
//                    CORPSE,
//                    getLoadout(), 10.0F, -20.0F, 300.0F, 300.0F, new EnergyManager(3));
//        } else {
//            initializeClass(null,
//                    "reskinContent/img/SneckoMod/SSSSnecko/shoulder2.png",
//                    "reskinContent/img/SneckoMod/SSSSnecko/shoulder.png",
//                    "reskinContent/img/SneckoMod/SSSSnecko/corpse.png",
//                    getLoadout(), 10.0F, -20.0F, 300.0F, 300.0F, new EnergyManager(3));
//        }


        dialogX = (drawX + 0.0F * Settings.scale);
        dialogY = (drawY + 240.0F * Settings.scale);
        this.reloadAnimation();
    }

    public void reloadAnimation() {
        this.loadAnimation(
                CharacterSelectScreenPatches.characters[3].skins[CharacterSelectScreenPatches.characters[3].reskinCount].atlasURL,
                CharacterSelectScreenPatches.characters[3].skins[CharacterSelectScreenPatches.characters[3].reskinCount].jsonURL,
                CharacterSelectScreenPatches.characters[3].skins[CharacterSelectScreenPatches.characters[3].reskinCount].renderscale);

        AnimationState.TrackEntry e = this.state.setAnimation(0, "Idle", true);
        e.setTime(e.getEndTime() * MathUtils.random());
        this.stateData.setMix("Hit", "Idle", 0.1F);
        e.setTimeScale(0.8F);
    }

    public void setRenderscale(float renderscale) {
        this.renderscale = renderscale;
        reloadAnimation();
    }

    @Override
    public CharSelectInfo getLoadout() {
        return new CharSelectInfo(NAMES[0], TEXT[0],
                85, 85, 3, 99, 5, this, getStartingRelics(),
                getStartingDeck(), false);
    }

    @Override
    public ArrayList<String> getStartingDeck() {
        ArrayList<String> retVal = new ArrayList<>();
        retVal.add(Strike.ID);
        retVal.add(Strike.ID);
        retVal.add(Strike.ID);
        retVal.add(Strike.ID);
        retVal.add(Defend.ID);
        retVal.add(Defend.ID);
        retVal.add(Defend.ID);
        retVal.add(Defend.ID);
        retVal.add(TailWhip.ID);
        retVal.add(SnekBite.ID);
        return retVal;
    }

    public ArrayList<String> getStartingRelics() {
        ArrayList<String> retVal = new ArrayList<>();
        retVal.add(SneckoSoul.ID);
        return retVal;
    }

    @Override
    public void doCharSelectScreenSelectEffect() {
        CardCrawlGame.sound.playA("MONSTER_SNECKO_GLARE", MathUtils.random(-0.2F, 0.2F));
        CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.LOW, ScreenShake.ShakeDur.SHORT,
                false);
    }

    @Override
    public String getCustomModeCharacterButtonSoundKey() {
        return "MONSTER_SNECKO_GLARE";
    }

    @Override
    public int getAscensionMaxHPLoss() {
        return 5;
    }

    @Override
    public AbstractCard.CardColor getCardColor() {
        return SNECKO_CYAN;
    }

    @Override
    public Color getCardTrailColor() {
        return placeholderColor.cpy();
    }

    @Override
    public BitmapFont getEnergyNumFont() {
        return FontHelper.energyNumFontRed;
    }

    @Override
    public String getLocalizedCharacterName() {
        return NAMES[0];
    }

    @Override
    public AbstractCard getStartCardForEvent() {
        return OffclassHelper.getARandomOffclass();
    }

    @Override
    public String getTitle(PlayerClass playerClass) {
        return NAMES[1];
    }

    @Override
    public AbstractPlayer newInstance() {
        return new TheSnecko(name, chosenClass);
    }

    @Override
    public Color getCardRenderColor() {
        return placeholderColor.cpy();
    }

    @Override
    public Color getSlashAttackColor() {
        return placeholderColor.cpy();
    }

    @Override
    public AbstractGameAction.AttackEffect[] getSpireHeartSlashEffect() {
        return new AbstractGameAction.AttackEffect[]{
                AbstractGameAction.AttackEffect.FIRE,
                AbstractGameAction.AttackEffect.BLUNT_HEAVY,
                AbstractGameAction.AttackEffect.FIRE};
    }

    /*
    @Override
    public Texture getCustomModeCharacterButtonImage() {
        return ImageMaster.loadImage(SneckoMod.getModID() + "Resources/images/charSelect/leaderboard.png");
    }
    */


    @Override
    public void updateVictoryVfx(ArrayList<AbstractGameEffect> effects) {
        if (effects.stream().filter(e -> e instanceof SneckoVictoryNumberEffect).count() < 8)
            effects.add(new SneckoVictoryNumberEffect());
    }


    @Override
    public String getSpireHeartText() {
        return TEXT[1];
    }

    @Override
    public String getVampireText() {
        return TEXT[2];
    }

    public static class Enums {
        @SpireEnum
        public static PlayerClass THE_SNECKO;
        @SpireEnum(name = "SNECKO_CYAN")
        public static AbstractCard.CardColor SNECKO_CYAN;
        @SpireEnum(name = "SNECKO_CYAN")
        @SuppressWarnings("unused")
        public static CardLibrary.LibraryType LIBRARY_COLOR;
    }
}