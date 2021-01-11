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
import reskinContent.reskinContent;
import sneckomod.cards.Defend;
import sneckomod.cards.SnekBite;
import sneckomod.cards.Strike;
import sneckomod.cards.TailWhip;
import sneckomod.cards.unknowns.*;
import sneckomod.relics.SneckoSoul;

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
    private static final String ID = makeID("theSnecko");
    private static final CharacterStrings characterStrings = CardCrawlGame.languagePack.getCharacterString(ID);
    private static final String[] NAMES = characterStrings.NAMES;
    private static final String[] TEXT = characterStrings.TEXT;

    public float renderscale = 1.2F;

    public TheSnecko(String name, PlayerClass setClass) {
        super(name, setClass, orbTextures, "sneckomodResources/images/char/orb/vfx.png", (String)null, (String)null);

        if (!reskinContent.sneckoReskinAnimation) {
            initializeClass(null,
                    SHOULDER1,
                    SHOULDER2,
                    CORPSE,
                    getLoadout(), 10.0F, -20.0F, 300.0F, 300.0F, new EnergyManager(3));
        } else {
            initializeClass(null,
                    "reskinContent/img/SneckoMod/shoulder2.png",
                    "reskinContent/img/SneckoMod/shoulder.png",
                    "reskinContent/img/SneckoMod/corpse.png",
                    getLoadout(), 10.0F, -20.0F, 300.0F, 300.0F, new EnergyManager(3));
        }


        dialogX = (drawX + 0.0F * Settings.scale);
        dialogY = (drawY + 240.0F * Settings.scale);
        this.reloadAnimation();
    }

    public void reloadAnimation() {
        if (reskinContent.sneckoReskinAnimation && reskinContent.sneckoReskinUnlock) {
            loadAnimation("reskinContent/img/SneckoMod/animation/Snecko_waifu.atlas", "reskinContent/img/SneckoMod/animation/Snecko_waifu.json", renderscale);
        } else {
            loadAnimation("sneckomodResources/images/char/skeleton.atlas", "sneckomodResources/images/char/skeleton.json", renderscale);
        }

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
                90, 90, 2, 99, 5, this, getStartingRelics(),
                getStartingDeck(), false);
    }


    @Override
    public ArrayList<AbstractCard> getCardPool(ArrayList<AbstractCard> tmpPool) {
        if (ModHelper.isModEnabled("Red Cards")) {
            CardLibrary.addRedCards(tmpPool);
        }
        if (ModHelper.isModEnabled("Green Cards")) {
            CardLibrary.addGreenCards(tmpPool);
        }

        if (ModHelper.isModEnabled("Blue Cards")) {
            CardLibrary.addBlueCards(tmpPool);
        }

        if (ModHelper.isModEnabled("Purple Cards")) {
            CardLibrary.addPurpleCards(tmpPool);
        }

        return super.getCardPool(tmpPool);
    }


    @Override
    public ArrayList<String> getStartingDeck() {
        ArrayList<String> retVal = new ArrayList<>();
        retVal.add(Strike.ID);
        retVal.add(Strike.ID);
        retVal.add(Strike.ID);
        retVal.add(Defend.ID);
        retVal.add(Defend.ID);
        retVal.add(Defend.ID);
        retVal.add(TailWhip.ID);
        retVal.add(SnekBite.ID);
        retVal.add(Unknown.ID);
        retVal.add(Unknown.ID);
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
        return 7;
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
        ArrayList<AbstractCard> cardList = new ArrayList<>(CardLibrary.getAllCards());
        return cardList.get(AbstractDungeon.cardRandomRng.random(cardList.size() - 1));
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