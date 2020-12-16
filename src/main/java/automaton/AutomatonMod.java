package automaton;

import automaton.relics.BronzeBoon;
import automaton.util.CardFilter;
import automaton.util.CardIgnore;
import basemod.BaseMod;
import basemod.abstracts.CustomUnlockBundle;
import basemod.interfaces.*;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import javassist.CtClass;
import javassist.Modifier;
import javassist.NotFoundException;
import org.clapper.util.classutil.*;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;

@SuppressWarnings({"ConstantConditions", "unused", "WeakerAccess"})
@SpireInitializer
public class AutomatonMod implements
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
    public static final String CARD_ENERGY_S = "bronzeResources/images/512/card_champ_orb.png";
    public static final String TEXT_ENERGY = "bronzeResources/images/512/card_small_orb.png";
    private static final String ATTACK_S_ART = "bronzeResources/images/512/bg_attack_colorless.png";
    private static final String SKILL_S_ART = "bronzeResources/images/512/bg_skill_colorless.png";
    private static final String POWER_S_ART = "bronzeResources/images/512/bg_power_colorless.png";
    private static final String ATTACK_L_ART = "bronzeResources/images/1024/bg_attack_colorless.png";
    private static final String SKILL_L_ART = "bronzeResources/images/1024/bg_skill_colorless.png";
    private static final String POWER_L_ART = "bronzeResources/images/1024/bg_power_colorless.png";
    private static final String CARD_ENERGY_L = "bronzeResources/images/1024/card_champ_orb.png";
    private static final String CHARSELECT_BUTTON = "bronzeResources/images/charSelect/charButton.png";
    private static final String CHARSELECT_PORTRAIT = "bronzeResources/images/charSelect/charBG.png";

    public static Color placeholderColor = new Color(150F / 255F, 50F / 255F, 200F / 255F, 1); // TODO: CHANGE
    public static Color potionLabColor = new Color(250F / 255F, 100F / 255F, 200F / 255F, 1); // TODO: CHANGE
    private static String modID = "bronze";
    private CustomUnlockBundle unlocks0; // TODO: Figure this out
    private CustomUnlockBundle unlocks1;
    private CustomUnlockBundle unlocks2;
    private CustomUnlockBundle unlocks3;
    private CustomUnlockBundle unlocks4;

    public AutomatonMod() {
        BaseMod.subscribe(this);

        modID = "bronze";

        BaseMod.addColor(AutomatonChar.Enums.BRONZE_AUTOMATON, placeholderColor, placeholderColor, placeholderColor,
                placeholderColor, placeholderColor, placeholderColor, placeholderColor,
                ATTACK_S_ART, SKILL_S_ART, POWER_S_ART, CARD_ENERGY_S,
                ATTACK_L_ART, SKILL_L_ART, POWER_L_ART,
                CARD_ENERGY_L, TEXT_ENERGY);


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

    public static String getModID() {
        return modID;
    }

    public static void initialize() {
        AutomatonMod automatonMod = new AutomatonMod();
    }

    public static String makeID(String idText) {
        return getModID() + ":" + idText;
    }

    private static void autoAddCards()
            throws URISyntaxException, IllegalAccessException, InstantiationException, NotFoundException, ClassNotFoundException {
        ClassFinder finder = new ClassFinder();
        URL url = AutomatonMod.class.getProtectionDomain().getCodeSource().getLocation();
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
        BaseMod.addCharacter(new AutomatonChar("The Automaton", AutomatonChar.Enums.THE_AUTOMATON), CHARSELECT_BUTTON, CHARSELECT_PORTRAIT, AutomatonChar.Enums.THE_AUTOMATON);
    }

    @Override
    public void receiveEditRelics() {
        //TODO: This
        BaseMod.addRelicToCustomPool(new BronzeBoon(), AutomatonChar.Enums.BRONZE_AUTOMATON);
    }


    @Override
    public void receiveEditCards() {
        try {
            autoAddCards();
        } catch (URISyntaxException | IllegalAccessException | InstantiationException | NotFoundException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void addPotions() {
        //TODO: This
    }

    @Override
    public void receiveSetUnlocks() {
        //TODO: This

    }

    public void receivePostInitialize() {
        addPotions();
        //TODO: This
    }
}
