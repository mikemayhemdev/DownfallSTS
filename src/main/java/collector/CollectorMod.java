package collector;

import automaton.util.CardFilter;
import basemod.BaseMod;
import basemod.abstracts.CustomUnlockBundle;
import basemod.interfaces.*;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import downfall.util.CardIgnore;
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
public class CollectorMod implements
        EditCardsSubscriber,
        EditRelicsSubscriber,
        // EditStringsSubscriber,
        //EditKeywordsSubscriber,
        EditCharactersSubscriber,
        PostInitializeSubscriber,
        SetUnlocksSubscriber,
        OnStartBattleSubscriber,
        PostBattleSubscriber,
        StartGameSubscriber,
        PostDrawSubscriber {
    public static final String SHOULDER1 = "collectorResources/images/char/mainChar/shoulder.png";
    public static final String SHOULDER2 = "collectorResources/images/char/mainChar/shoulderR.png";
    public static final String CORPSE = "collectorResources/images/char/mainChar/corpse.png";
    public static final String CARD_ENERGY_S = "collectorResources/images/512/card_bronze_orb.png";
    public static final String TEXT_ENERGY = "collectorResources/images/512/card_small_orb.png";
    private static final String ATTACK_S_ART = "collectorResources/images/512/bg_attack_bronze.png";
    private static final String SKILL_S_ART = "collectorResources/images/512/bg_skill_bronze.png";
    private static final String POWER_S_ART = "collectorResources/images/512/bg_power_bronze.png";
    private static final String ATTACK_L_ART = "collectorResources/images/1024/bg_attack_bronze.png";
    private static final String SKILL_L_ART = "collectorResources/images/1024/bg_skill_bronze.png";
    private static final String POWER_L_ART = "collectorResources/images/1024/bg_power_bronze.png";
    private static final String CARD_ENERGY_L = "collectorResources/images/1024/card_bronze_orb.png";
    private static final String CHARSELECT_BUTTON = "collectorResources/images/charSelect/charButton.png";
    private static final String CHARSELECT_PORTRAIT = "collectorResources/images/charSelect/charBG.png";

    public static Color placeholderColor = new Color(214F / 255F, 202F / 255F, 158F / 255F, 1);
    public static Color potionLabColor = new Color(214F / 255F, 202F / 255F, 158F / 255F, 1);
    private static String modID = "collector";
    private CustomUnlockBundle unlocks0; // TODO: Set this up
    private CustomUnlockBundle unlocks1;
    private CustomUnlockBundle unlocks2;
    private CustomUnlockBundle unlocks3;
    private CustomUnlockBundle unlocks4;

    public CollectorMod() {
        BaseMod.subscribe(this);

        modID = "collector";

        BaseMod.addColor(CollectorChar.Enums.COLLECTOR, placeholderColor, placeholderColor, placeholderColor,
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
        CollectorMod automatonMod = new CollectorMod();
    }

    public static String makeID(String idText) {
        return getModID() + ":" + idText;
    }

    private static void autoAddCards()
            throws URISyntaxException, IllegalAccessException, InstantiationException, NotFoundException, ClassNotFoundException {
        ClassFinder finder = new ClassFinder();
        URL url = CollectorMod.class.getProtectionDomain().getCodeSource().getLocation();
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
        BaseMod.addCharacter(new CollectorChar("The Collector", CollectorChar.Enums.THE_COLLECTOR), CHARSELECT_BUTTON, CHARSELECT_PORTRAIT, CollectorChar.Enums.THE_COLLECTOR);
    }

    @Override
    public void receiveEditRelics() {
    }


    @Override
    public void receiveEditCards() {
    }

    public void addPotions() {
    }

    @Override
    public void receiveSetUnlocks() {
        //TODO: Set this up

    }

    public void receivePostInitialize() {
        addPotions();
        //BaseMod.addEvent(new AddEventParams.Builder(AccursedBlacksmithAutomaton.ID, AccursedBlacksmithAutomaton.class) //Event ID//
                //Event Character//
               // .playerClass(AutomatonChar.Enums.THE_AUTOMATON)
                //Existing Event to Override//
               // .overrideEvent(AccursedBlacksmith.ID)
                //Event Type//
               // .eventType(EventUtils.EventType.FULL_REPLACE)
                // .create());
    }

    @Override
    public void receiveOnBattleStart(AbstractRoom abstractRoom) { ;
    }

    @Override
    public void receivePostBattle(AbstractRoom abstractRoom) {
    }

    @Override
    public void receiveStartGame() {
    }

    public static CardGroup getRareCards() {
        CardGroup retVal = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);

        for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
            if (c.rarity == AbstractCard.CardRarity.RARE) {
                retVal.group.add(c);
            }
        }

        return retVal;
    }

    @Override
    public void receivePostDraw(AbstractCard abstractCard) {

    }
}