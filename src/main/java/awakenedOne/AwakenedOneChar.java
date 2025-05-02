package awakenedOne;

import automaton.cards.Goto;
import awakenedOne.cards.Defend;
import awakenedOne.cards.Hymn;
import awakenedOne.cards.Strike;
import awakenedOne.cards.TalonRake;
import awakenedOne.relics.RippedDoll;
import basemod.abstracts.CustomPlayer;
import collector.util.Wiz;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.mod.stslib.patches.core.AbstractCreature.TempHPField;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import reskinContent.patches.CharacterSelectScreenPatches;

import java.util.ArrayList;

import static awakenedOne.AwakenedOneMod.makeID;
import static awakenedOne.AwakenedOneMod.placeholderColor;

public class AwakenedOneChar extends CustomPlayer {
    public static final String ID = makeID("awakenedOne");
    public static final CharacterStrings characterStrings = CardCrawlGame.languagePack.getCharacterString(ID);
    private static final String[] orbTextures = {
            "awakenedResources/images/mainChar/orb/layer1.png",
            "awakenedResources/images/mainChar/orb/layer2.png",
            "awakenedResources/images/mainChar/orb/layer3.png",
            "awakenedResources/images/mainChar/orb/layer4.png",
            "awakenedResources/images/mainChar/orb/layer5.png",
            "awakenedResources/images/mainChar/orb/layer6.png",
            "awakenedResources/images/mainChar/orb/layer1d.png",
            "awakenedResources/images/mainChar/orb/layer2d.png",
            "awakenedResources/images/mainChar/orb/layer3d.png",
            "awakenedResources/images/mainChar/orb/layer4d.png",
            "awakenedResources/images/mainChar/orb/layer5d.png",};
    private static final String[] NAMES = characterStrings.NAMES;
    private static final String[] TEXT = characterStrings.TEXT;

    public float renderscale = 1.2F;

    private final String atlasURL = "awakenedResources/images/mainChar/awakened.atlas";
    private final String jsonURL = "awakenedResources/images/mainChar/awakened.json";

    public AwakenedOneChar(String name, PlayerClass setClass) {
        super(name, setClass, orbTextures, "awakenedResources/images/mainChar/orb/vfx.png", null, (String) null);
        initializeClass(null,
                CharacterSelectScreenPatches.characters[5].skins[CharacterSelectScreenPatches.characters[5].reskinCount].SHOULDER1,
                CharacterSelectScreenPatches.characters[5].skins[CharacterSelectScreenPatches.characters[5].reskinCount].SHOULDER2,
                CharacterSelectScreenPatches.characters[5].skins[CharacterSelectScreenPatches.characters[5].reskinCount].CORPSE,
                getLoadout(), 0.0F, -30.0F, 270.0F, 310.0F, new EnergyManager(3));

        this.reloadAnimation();

        flipHorizontal = true;
    }

    @Override
    public void combatUpdate() {
        super.combatUpdate();
        if (flipHorizontal == false) {
            flipHorizontal = true;
        }
    }

    public void reloadAnimation() {
        this.loadAnimation(
                "images/monsters/theForest/awakenedOne/skeleton.atlas", "images/monsters/theForest/awakenedOne/skeleton.json", 1.4F);
        this.state.setAnimation(0, "Idle_1", true);
        flipHorizontal = true;
    }


    @Override
    public Texture getCustomModeCharacterButtonImage() {
        return ImageMaster.loadImage(AwakenedOneMod.getModID() + "Resources/images/charSelect/leaderboard.png");
    }


    @Override
    public CharSelectInfo getLoadout() {
        return new CharSelectInfo(NAMES[0], TEXT[0],
                73, 73, 0, 99, 5, this, getStartingRelics(),
                getStartingDeck(), false);
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
        retVal.add(TalonRake.ID);
        retVal.add(Hymn.ID);
        return retVal;
    }

    public ArrayList<String> getStartingRelics() {
        ArrayList<String> retVal = new ArrayList<>();
        retVal.add(RippedDoll.ID);
        return retVal;
    }

    @Override
    public void doCharSelectScreenSelectEffect() {
        if (MathUtils.randomBoolean()) {
            CardCrawlGame.sound.play("VO_AWAKENEDONE_2", 0.1F);// 72
        } else {
            CardCrawlGame.sound.play("VO_AWAKENEDONE_1", 0.1F);// 72
        }
        CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.LOW, ScreenShake.ShakeDur.SHORT, false);
    }

    @Override
    public String getCustomModeCharacterButtonSoundKey() {
        if (MathUtils.randomBoolean()) {
            return "VO_CULTIST_1A";
        } else {
            return "VO_CULTIST_1B";
        }
    }

    @Override
    public int getAscensionMaxHPLoss() {
        return 7;
    }

    @Override
    public AbstractCard.CardColor getCardColor() {
        return Enums.AWAKENED_BLUE;
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
        return new TalonRake();
    }

    @Override
    public String getTitle(PlayerClass playerClass) {
        return NAMES[1];
    }

    @Override
    public AbstractPlayer newInstance() {
        return new AwakenedOneChar(name, chosenClass);
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
        public static PlayerClass AWAKENED_ONE;
        @SpireEnum(name = "AWAKENED_BLUE")
        public static AbstractCard.CardColor AWAKENED_BLUE;
        @SpireEnum(name = "AWAKENED_BLUE")
        @SuppressWarnings("unused")
        public static CardLibrary.LibraryType LIBRARY_COLOR;
    }
}
