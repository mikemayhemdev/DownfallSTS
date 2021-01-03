package timeEater;

import automaton.AutomatonChar;
import automaton.events.*;
import automaton.potions.BuildAFunctionPotion;
import automaton.potions.BurnAndBuffPotion;
import automaton.potions.CleanCodePotion;
import automaton.potions.FreeFunctionsPotion;
import basemod.BaseMod;
import basemod.abstracts.CustomUnlockBundle;
import basemod.eventUtil.AddEventParams;
import basemod.eventUtil.EventUtils;
import basemod.interfaces.*;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.dungeons.Exordium;
import com.megacrit.cardcrawl.dungeons.TheCity;
import com.megacrit.cardcrawl.events.city.BackToBasics;
import com.megacrit.cardcrawl.events.shrines.AccursedBlacksmith;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import downfall.util.CardIgnore;
import javassist.CtClass;
import javassist.Modifier;
import javassist.NotFoundException;
import org.clapper.util.classutil.*;
import timeEater.relics.OldWatch;
import timeEater.util.CardFilter;

import java.io.File;
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
        OnStartBattleSubscriber,
        PostBattleSubscriber {
    public static final String SHOULDER1 = "timeResources/images/char/mainChar/shoulder.png";
    public static final String SHOULDER2 = "timeResources/images/char/mainChar/shoulderR.png";
    public static final String CORPSE = "timeResources/images/char/mainChar/corpse.png";
    public static final String CARD_ENERGY_S = "timeResources/images/512/card_time_orb.png";
    public static final String TEXT_ENERGY = "timeResources/images/512/card_small_orb.png";
    private static final String ATTACK_S_ART = "timeResources/images/512/bg_attack_time.png";
    private static final String SKILL_S_ART = "timeResources/images/512/bg_skill_time.png";
    private static final String POWER_S_ART = "timeResources/images/512/bg_power_time.png";
    private static final String ATTACK_L_ART = "timeResources/images/1024/bg_attack_time.png";
    private static final String SKILL_L_ART = "timeResources/images/1024/bg_skill_time.png";
    private static final String POWER_L_ART = "timeResources/images/1024/bg_power_time.png";
    private static final String CARD_ENERGY_L = "timeResources/images/1024/card_time_orb.png";
    private static final String CHARSELECT_BUTTON = "timeResources/images/charSelect/charButton.png";
    private static final String CHARSELECT_PORTRAIT = "timeResources/images/charSelect/charBG.png";

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

        BaseMod.addColor(TimeEaterChar.Enums.TIME_EATER_PURPLE, placeholderColor, placeholderColor, placeholderColor,
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
        TimeEaterMod automatonMod = new TimeEaterMod();
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
        BaseMod.addCharacter(new TimeEaterChar("The TimeEater", TimeEaterChar.Enums.THE_TIME_EATER), CHARSELECT_BUTTON, CHARSELECT_PORTRAIT, TimeEaterChar.Enums.THE_TIME_EATER);
    }

    @Override
    public void receiveEditRelics() {
        BaseMod.addRelicToCustomPool(new OldWatch(), TimeEaterChar.Enums.TIME_EATER_PURPLE);
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

        BaseMod.addPotion(BuildAFunctionPotion.class, Color.FIREBRICK, Color.GRAY, Color.TAN, BuildAFunctionPotion.POTION_ID, AutomatonChar.Enums.THE_AUTOMATON);
        BaseMod.addPotion(BurnAndBuffPotion.class, Color.RED, Color.GREEN, Color.CLEAR, BurnAndBuffPotion.POTION_ID);
        BaseMod.addPotion(CleanCodePotion.class, Color.CORAL, Color.PURPLE, Color.MAROON, CleanCodePotion.POTION_ID, AutomatonChar.Enums.THE_AUTOMATON);
        BaseMod.addPotion(FreeFunctionsPotion.class, Color.BLACK, Color.PURPLE, Color.GRAY, FreeFunctionsPotion.POTION_ID, AutomatonChar.Enums.THE_AUTOMATON);
    }

    public void receivePostInitialize() {
        addPotions();

        BaseMod.addEvent(new AddEventParams.Builder(ShapeFactory.ID, ShapeFactory.class) //Event ID//
                //Event Character//
                .playerClass(AutomatonChar.Enums.THE_AUTOMATON)
                .dungeonID(Exordium.ID)
                .eventType(EventUtils.EventType.NORMAL)
                .create());

        BaseMod.addEvent(new AddEventParams.Builder(AncientFactory.ID, AncientFactory.class) //Event ID//
                //Event Character//
                .playerClass(AutomatonChar.Enums.THE_AUTOMATON)
                .dungeonID(TheCity.ID)
                .eventType(EventUtils.EventType.NORMAL)
                .create());

        BaseMod.addEvent(new AddEventParams.Builder(BackToBasicsAutomaton.ID, BackToBasicsAutomaton.class) //Event ID//
                //Event Character//
                .playerClass(AutomatonChar.Enums.THE_AUTOMATON)
                //Existing Event to Override//
                .overrideEvent(BackToBasics.ID)
                //Event Type//
                .eventType(EventUtils.EventType.FULL_REPLACE)
                .create());

        BaseMod.addEvent(new AddEventParams.Builder(CrystalForgeAutomaton.ID, CrystalForgeAutomaton.class) //Event ID//
                //Event Character//
                .playerClass(AutomatonChar.Enums.THE_AUTOMATON)
                .create());

        BaseMod.addEvent(new AddEventParams.Builder(AccursedBlacksmithAutomaton.ID, AccursedBlacksmithAutomaton.class) //Event ID//
                //Event Character//
                .playerClass(AutomatonChar.Enums.THE_AUTOMATON)
                //Existing Event to Override//
                .overrideEvent(AccursedBlacksmith.ID)
                //Event Type//
                .eventType(EventUtils.EventType.FULL_REPLACE)
                .create());
    }


    @Override
    public void receiveSetUnlocks() {
        //TODO: This
    }


    @Override
    public void receiveOnBattleStart(AbstractRoom abstractRoom) {
        if (AbstractDungeon.player instanceof TimeEaterChar) {
            ClockHelper.active = true;
        }
    }

    @Override
    public void receivePostBattle(AbstractRoom abstractRoom) {
        ClockHelper.active = false;
    }
}