
package timeeater;

import automaton.cards.AbstractBronzeCard;
import automaton.util.AutoVar;
import basemod.BaseMod;
import basemod.ReflectionHacks;
import basemod.abstracts.CustomUnlockBundle;
import basemod.interfaces.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import downfall.util.CardIgnore;
import javassist.CtClass;
import javassist.Modifier;
import javassist.NotFoundException;
import org.clapper.util.classutil.*;
import timeeater.cards.alternateDimension.*;
import timeeater.relics.Watch;
import timeeater.suspend.SuspendHelper;
import timeeater.util.CardFilter;

import java.io.File;
import java.lang.reflect.Array;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;

@SuppressWarnings({"ConstantConditions", "unused", "WeakerAccess"})
@SpireInitializer
public class TimeEaterMod implements
        EditCardsSubscriber,
        EditRelicsSubscriber,
        // EditStringsSubscriber,
        //EditKeywordsSubscriber,
        EditCharactersSubscriber,
        PostInitializeSubscriber,
        SetUnlocksSubscriber,
        OnStartBattleSubscriber {
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
        TimeEaterMod timeEaterMod = new TimeEaterMod();
    }

    public static String makeID(String idText) {
        return getModID() + ":" + idText;
    }

    private static void autoAddCards()
            throws URISyntaxException, IllegalAccessException, InstantiationException, NotFoundException, ClassNotFoundException {
        ClassFinder finder = new ClassFinder();
        URL url = TimeEaterMod.class.getProtectionDomain().getCodeSource().getLocation();
        finder.add(new File(url.toURI()));

        ClassFilter filter =
                new AndClassFilter(
                        new NotClassFilter(new InterfaceOnlyClassFilter()),
                        new NotClassFilter(new AbstractClassFilter()),
                        new ClassModifiersClassFilter(Modifier.PUBLIC),
                        new CardFilter()
                );
        Collection<ClassInfo> foundClasses = new ArrayList<>();
        finder.findClasses(foundClasses, filter);

        for (ClassInfo classInfo : foundClasses) {
            CtClass cls = Loader.getClassPool().get(classInfo.getClassName());
            if (cls.hasAnnotation(CardIgnore.class)) {
                continue;
            }
            boolean isCard = false;
            CtClass superCls = cls;
            while (superCls != null) {
                superCls = superCls.getSuperclass();
                if (superCls == null) {
                    break;
                }
                if (superCls.getName().equals(AbstractCard.class.getName())) {
                    isCard = true;
                    break;
                }
            }
            if (!isCard) {
                continue;
            }
            System.out.println(classInfo.getClassName());
            AbstractCard card = (AbstractCard) Loader.getClassPool().getClassLoader().loadClass(cls.getName()).newInstance();
            BaseMod.addCard(card);

        }
    }

    @Override
    public void receiveEditCharacters() {
        BaseMod.addCharacter(new TimeEaterChar("The Time Eater", TimeEaterChar.Enums.THE_TIME_EATER), CHARSELECT_BUTTON, CHARSELECT_PORTRAIT, TimeEaterChar.Enums.THE_TIME_EATER);
    }

    @Override
    public void receiveEditRelics() {
        BaseMod.addRelicToCustomPool(new Watch(), TimeEaterChar.Enums.MAGENTA);
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


    public static ArrayList<String> dimensionCards = new ArrayList<>();

    public static void initializeDimensionCardStrings(){

        dimensionCards.add(ArsenalGear.ID);
        dimensionCards.add(ConjureBarrage.ID);
        dimensionCards.add(DarkRitual.ID);
        dimensionCards.add(Inferno.ID);
        dimensionCards.add(Knighthood.ID);
        dimensionCards.add(LethalShot.ID);
        dimensionCards.add(Minniegun.ID);
        dimensionCards.add(PackRat.ID);
        dimensionCards.add(ScorchedEarth.ID);
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

    @Override
    public void receiveOnBattleStart(AbstractRoom abstractRoom) {
        SuspendHelper.atCombatStart();
    }
}