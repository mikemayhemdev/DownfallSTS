
package timeeater;

import automaton.cards.Terminator;
import automaton.cards.*;
import automaton.events.*;
import automaton.potions.BuildAFunctionPotion;
import automaton.potions.BurnAndBuffPotion;
import automaton.potions.CleanCodePotion;
import automaton.potions.FreeFunctionsPotion;
import automaton.relics.*;
import automaton.util.AutoVar;
import basemod.AutoAdd;
import basemod.BaseMod;
import basemod.ReflectionHacks;
import basemod.abstracts.CustomUnlockBundle;
import basemod.eventUtil.AddEventParams;
import basemod.eventUtil.EventUtils;
import basemod.helpers.RelicType;
import basemod.interfaces.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.widepotions.WidePotionsMod;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.Exordium;
import com.megacrit.cardcrawl.dungeons.TheCity;
import com.megacrit.cardcrawl.events.city.BackToBasics;
import com.megacrit.cardcrawl.events.shrines.AccursedBlacksmith;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import downfall.downfallMod;
import guardian.patches.BottledStasisPatch;
import javassist.NotFoundException;
import timeeater.cards.AbstractTimeEaterCard;

import java.net.URISyntaxException;

@SuppressWarnings({"ConstantConditions", "unused", "WeakerAccess"})
@SpireInitializer
public class TimeEaterMod implements
        EditCardsSubscriber,
        EditRelicsSubscriber,
        // EditStringsSubscriber,
        //EditKeywordsSubscriber,
        EditCharactersSubscriber,
        PostInitializeSubscriber,
        SetUnlocksSubscriber {
    public static final String SHOULDER1 = "bronzeResources/images/char/mainChar/shoulder.png";
    public static final String SHOULDER2 = "bronzeResources/images/char/mainChar/shoulderR.png";
    public static final String CORPSE = "bronzeResources/images/char/mainChar/corpse.png";
    public static final String CARD_ENERGY_S = "bronzeResources/images/512/card_bronze_orb.png";
    public static final String TEXT_ENERGY = "bronzeResources/images/512/card_small_orb.png";
    private static final String ATTACK_S_ART = "bronzeResources/images/512/bg_attack_bronze.png";
    private static final String SKILL_S_ART = "bronzeResources/images/512/bg_skill_bronze.png";
    private static final String POWER_S_ART = "bronzeResources/images/512/bg_power_bronze.png";
    private static final String ATTACK_L_ART = "bronzeResources/images/1024/bg_attack_bronze.png";
    private static final String SKILL_L_ART = "bronzeResources/images/1024/bg_skill_bronze.png";
    private static final String POWER_L_ART = "bronzeResources/images/1024/bg_power_bronze.png";
    private static final String CARD_ENERGY_L = "bronzeResources/images/1024/card_bronze_orb.png";
    private static final String CHARSELECT_BUTTON = "bronzeResources/images/charSelect/charButton.png";
    private static final String CHARSELECT_PORTRAIT = "bronzeResources/images/charSelect/charBG.png";

    public static Color placeholderColor = new Color(214F / 255F, 202F / 255F, 158F / 255F, 1);
    public static Color potionLabColor = new Color(214F / 255F, 202F / 255F, 158F / 255F, 1);
    private static String modID = "time";
    private CustomUnlockBundle unlocks0;
    private CustomUnlockBundle unlocks1;
    private CustomUnlockBundle unlocks2;
    private CustomUnlockBundle unlocks3;
    private CustomUnlockBundle unlocks4;

    public TimeEaterMod() {
        BaseMod.subscribe(this);

        modID = "time";

        BaseMod.addColor(TimeEaterChar.Enums.MAGENTA, placeholderColor, placeholderColor, placeholderColor,
                placeholderColor, placeholderColor, placeholderColor, placeholderColor,
                ATTACK_S_ART, SKILL_S_ART, POWER_S_ART, CARD_ENERGY_S,
                ATTACK_L_ART, SKILL_L_ART, POWER_L_ART,
                CARD_ENERGY_L, TEXT_ENERGY);


    }



    public static String makeBetaCardPath(String resourcePath) {
        return "bronzeResources/images/cards/joke/" + resourcePath;
    }

    public static void loadJokeCardImage(AbstractCard card, String img) {
        if (card instanceof AbstractBronzeCard) {
            ((AbstractBronzeCard) card).betaArtPath = img;
        }
        Texture cardTexture;
        cardTexture = ImageMaster.loadImage(img);
        cardTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        int tw = cardTexture.getWidth();
        int th = cardTexture.getHeight();
        TextureAtlas.AtlasRegion cardImg = new TextureAtlas.AtlasRegion(cardTexture, 0, 0, tw, th);
        ReflectionHacks.setPrivate(card, AbstractCard.class, "jokePortrait", cardImg);
    }


    public static String makeCardPath(String resourcePath) {
        return getModID() + "Resources/images/cards/" + resourcePath;
    }

    public static String makeUIPath(String resourcePath) {
        return getModID() + "Resources/images/ui/" + resourcePath;
    }

    public static String makeRelicPath(String resourcePath) {
        return getModID() + "Resources/images/relics/" + resourcePath;
    }

    public static String makeRelicOutlinePath(String resourcePath) {
        return getModID() + "Resources/images/relics/outline/" + resourcePath;
    }

    public static String makePowerPath(String resourcePath) {
        return getModID() + "Resources/images/powers/" + resourcePath;
    }

    public static String makeImagePath(String resourcePath) {
        return modID + "Resources/images/" + resourcePath;
    }

    public static String getModID() {
        return modID;
    }

    public static void initialize() {
     //   TimeEaterMod timeEaterMod = new TimeEaterMod();
    }

    public static String makeID(String idText) {
        return getModID() + ":" + idText;
    }

    private static void autoAddCards()
            throws URISyntaxException, IllegalAccessException, InstantiationException, NotFoundException, ClassNotFoundException {
        new AutoAdd(modID)
                .packageFilter(AbstractTimeEaterCard.class)
                .setDefaultSeen(false)
                .cards();
    }

    @Override
    public void receiveEditCharacters() {
        BaseMod.addCharacter(new TimeEaterChar("The Time Eater", TimeEaterChar.Enums.THE_TIME_EATER), CHARSELECT_BUTTON, CHARSELECT_PORTRAIT, TimeEaterChar.Enums.THE_TIME_EATER);
    }

    @Override
    public void receiveEditRelics() {

    }


    @Override
    public void receiveEditCards() {
        BaseMod.addDynamicVariable(new AutoVar());
        try {
            autoAddCards();
        } catch (URISyntaxException | IllegalAccessException | InstantiationException | NotFoundException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        /*
        BaseMod.addCard(new Daze());
        BaseMod.addCard(new Ignite());
        BaseMod.addCard(new GrievousWound());
        BaseMod.addCard(new IntoTheVoid());
        BaseMod.addCard(new UsefulSlime());
        BaseMod.addCard(new UnknownStatus());
        */
    }

    public void addPotions() {

    }

    public void receivePostInitialize() {
        addPotions();

    }


    @Override
    public void receiveSetUnlocks() {
        /*
        downfallMod.registerUnlockSuite(
                Constructor.ID,
                Separator.ID,
                Terminator.ID,

                Refactor.ID,
                InfiniteBeams.ID,
                InfiniteLoop.ID,

                Hardcode.ID,
                Library.ID,
                TinkerersToolbox.ID,

                ElectromagneticCoil.ID,
                Timepiece.ID,
                Mallet.ID,

                BronzeIdol.ID,
                DecasWashers.ID,
                DonusWashers.ID,

                TimeEaterChar.Enums.THE_TIME_EATER
        );

         */
    }


}