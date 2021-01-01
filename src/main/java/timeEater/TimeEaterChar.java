package timeEater;

import automaton.AutomatonMod;
import automaton.cards.Goto;
import basemod.abstracts.CustomPlayer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.colorless.Madness;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import timeEater.cards.Defend;
import timeEater.cards.ReapTime;
import timeEater.cards.Strike;
import timeEater.relics.OldWatch;

import java.util.ArrayList;

import static automaton.AutomatonMod.*;

public class TimeEaterChar extends CustomPlayer {
    public static final String ID = makeID("theTimeEater");
    public static final CharacterStrings characterStrings = CardCrawlGame.languagePack.getCharacterString(ID);
    private static final String[] orbTextures = {
            "timeResources/images/char/mainChar/orb/layer1.png",
            "timeResources/images/char/mainChar/orb/layer2.png",
            "timeResources/images/char/mainChar/orb/layer3.png",
            "timeResources/images/char/mainChar/orb/layer4.png",
            "timeResources/images/char/mainChar/orb/layer5.png",
            "timeResources/images/char/mainChar/orb/layer6.png",
            "timeResources/images/char/mainChar/orb/layer1d.png",
            "timeResources/images/char/mainChar/orb/layer2d.png",
            "timeResources/images/char/mainChar/orb/layer3d.png",
            "timeResources/images/char/mainChar/orb/layer4d.png",
            "timeResources/images/char/mainChar/orb/layer5d.png",};
    private static final String[] NAMES = characterStrings.NAMES;
    private static final String[] TEXT = characterStrings.TEXT;

    public float renderscale = 1.2F;

    private String atlasURL = "timeResources/images/char/mainChar/time.atlas";
    private String jsonURL = "timeResources/images/char/mainChar/time.json";

    public TimeEaterChar(String name, PlayerClass setClass) {
        super(name, setClass, orbTextures, "timeResources/images/char/mainChar/orb/vfx.png", (String) null, (String) null);
        initializeClass(null,
                SHOULDER1,
                SHOULDER2,
                CORPSE,
                getLoadout(), 0.0F, -30.0F, 270.0F, 300.0F, new EnergyManager(3));

        this.reloadAnimation();

    }

    public void reloadAnimation() {
        this.loadAnimation(atlasURL, this.jsonURL, renderscale);
        this.state.setAnimation(0, "idle", true);
    }


    @Override
    public Texture getCustomModeCharacterButtonImage() {
        return ImageMaster.loadImage(AutomatonMod.getModID() + "Resources/images/charSelect/leaderboard.png");
    }


    @Override
    public CharSelectInfo getLoadout() {
        return new CharSelectInfo(NAMES[0], TEXT[0],
                70, 70, 0, 99, 5, this, getStartingRelics(),
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
        for (int i = 0; i < 4; i++) retVal.add(Strike.ID);
        for (int i = 0; i < 4; i++) retVal.add(Defend.ID);
        retVal.add(Madness.ID); // TODO: Special card 1
        retVal.add(ReapTime.ID);
        return retVal;
    }

    public ArrayList<String> getStartingRelics() {
        ArrayList<String> retVal = new ArrayList<>();
        retVal.add(OldWatch.ID);
        return retVal;
    }

    @Override
    public void doCharSelectScreenSelectEffect() {

        if (MathUtils.randomBoolean()) {
            CardCrawlGame.sound.play("MONSTER_AUTOMATON_SUMMON", 0.1F);
        } else {
            CardCrawlGame.sound.play("AUTOMATON_ORB_SPAWN", 0.1F);
        }
        CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.LOW, ScreenShake.ShakeDur.SHORT,
                false);
    }

    @Override
    public String getCustomModeCharacterButtonSoundKey() {

        if (MathUtils.randomBoolean()) {
            return "MONSTER_AUTOMATON_SUMMON";
        } else {
            return "AUTOMATON_ORB_SPAWN";
        }
    }

    @Override
    public int getAscensionMaxHPLoss() {
        return 7;
    }

    @Override
    public AbstractCard.CardColor getCardColor() {
        return Enums.TIME_EATER_PURPLE;
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
        return new Goto();
    }

    @Override
    public String getTitle(PlayerClass playerClass) {
        return NAMES[1];
    }

    @Override
    public AbstractPlayer newInstance() {
        return new TimeEaterChar(name, chosenClass);
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
                AbstractGameAction.AttackEffect.SLASH_VERTICAL,
                AbstractGameAction.AttackEffect.SLASH_HORIZONTAL};
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
        public static PlayerClass THE_TIME_EATER;
        @SpireEnum(name = "THE_TIME_EATER")
        public static AbstractCard.CardColor TIME_EATER_PURPLE;
        @SpireEnum(name = "THE_TIME_EATER")
        @SuppressWarnings("unused")
        public static CardLibrary.LibraryType LIBRARY_COLOR;
    }
}
