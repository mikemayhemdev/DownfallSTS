package automaton;

import automaton.cards.Defend;
import automaton.cards.OpenFire;
import automaton.cards.Peashooter;
import automaton.cards.Strike;
import automaton.relics.BronzeBoon;
import basemod.abstracts.CustomPlayer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.screens.CharSelectInfo;

import java.util.ArrayList;

import static automaton.AutomatonMod.*;

public class AutomatonChar extends CustomPlayer {
    public static final String ID = makeID("theAutomaton");
    public static final CharacterStrings characterStrings = CardCrawlGame.languagePack.getCharacterString(ID);
    private static final String[] orbTextures = {
            "bronzeResources/images/char/mainChar/orb/layer1.png",
            "bronzeResources/images/char/mainChar/orb/layer2.png",
            "bronzeResources/images/char/mainChar/orb/layer3.png",
            "bronzeResources/images/char/mainChar/orb/layer4.png",
            "bronzeResources/images/char/mainChar/orb/layer5.png",
            "bronzeResources/images/char/mainChar/orb/layer6.png",
            "bronzeResources/images/char/mainChar/orb/layer1d.png",
            "bronzeResources/images/char/mainChar/orb/layer2d.png",
            "bronzeResources/images/char/mainChar/orb/layer3d.png",
            "bronzeResources/images/char/mainChar/orb/layer4d.png",
            "bronzeResources/images/char/mainChar/orb/layer5d.png",};
    private static final String[] NAMES = characterStrings.NAMES;
    private static final String[] TEXT = characterStrings.TEXT;

    public float renderscale = 1.0F;

    private String atlasURL = "bronzeResources/images/char/mainChar/champ.atlas";
    private String jsonURL = "bronzeResources/images/char/mainChar/champ.json";

    public AutomatonChar(String name, PlayerClass setClass) {
        super(name, setClass, orbTextures, "bronzeResources/images/char/mainChar/orb/vfx.png", (String)null, (String)null);
        initializeClass(null,
                SHOULDER1,
                SHOULDER2,
                CORPSE,
                getLoadout(), -15.0F, -30.0F, 450.0F, 450.0F, new EnergyManager(3));

        this.reloadAnimation();

        this.drawY = this.drawY + 25F * Settings.scale;
        dialogX = (drawX + 0.0F * Settings.scale);
        dialogY = (drawY + 240.0F * Settings.scale);

    }

    public void reloadAnimation() {

        this.loadAnimation(atlasURL, this.jsonURL, renderscale);
        this.state.setAnimation(0, "Idle", true);


    }


    @Override
    public Texture getCustomModeCharacterButtonImage() {
        return ImageMaster.loadImage(AutomatonMod.getModID() + "Resources/images/charSelect/leaderboard.png");
    }


    @Override
    public CharSelectInfo getLoadout() {
        return new CharSelectInfo(NAMES[0], TEXT[0],
                70, 70, 0, 101, 5, this, getStartingRelics(),
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
        for (int i = 0; i < 4; i++) {
            retVal.add(Strike.ID);
        }
        for (int i = 0; i < 4; i++) {
            retVal.add(Defend.ID);
        }
        retVal.add(Peashooter.ID);
        retVal.add(OpenFire.ID);
        return retVal;
    }

    public ArrayList<String> getStartingRelics() {
        ArrayList<String> retVal = new ArrayList<>();
        retVal.add(BronzeBoon.ID);
        return retVal;
    }

    @Override
    public void doCharSelectScreenSelectEffect() {
        //TODO: Change
        if (MathUtils.randomBoolean()) {
            CardCrawlGame.sound.play("VO_CHAMP_3A", 0.1F);
        } else {
            CardCrawlGame.sound.play("VO_CHAMP_3B", 0.1F);
        }
        CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.LOW, ScreenShake.ShakeDur.SHORT,
                false);
    }

    @Override
    public String getCustomModeCharacterButtonSoundKey() {

        //TODO: Change
        if (MathUtils.randomBoolean()) {
            return "VO_CHAMP_3A";
        } else {
            return "VO_CHAMP_3B";
        }
    }

    @Override
    public int getAscensionMaxHPLoss() {
        return 7;
    }

    @Override
    public AbstractCard.CardColor getCardColor() {
        return Enums.BRONZE_AUTOMATON;
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
        return new OpenFire();
    }

    @Override
    public String getTitle(PlayerClass playerClass) {
        return NAMES[1];
    }

    @Override
    public AbstractPlayer newInstance() {
        return new AutomatonChar(name, chosenClass);
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
                AbstractGameAction.AttackEffect.SLASH_HEAVY,
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
        public static PlayerClass THE_AUTOMATON;
        @SpireEnum(name = "THE_BRONZE_AUTOMATON")
        public static AbstractCard.CardColor BRONZE_AUTOMATON;
        @SpireEnum(name = "THE_BRONZE_AUTOMATON")
        @SuppressWarnings("unused")
        public static CardLibrary.LibraryType LIBRARY_COLOR;
    }
}
