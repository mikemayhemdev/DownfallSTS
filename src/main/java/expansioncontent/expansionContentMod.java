package expansioncontent;

/*

This package should contain all content additions that get added to the entire game, not just
Evil Mode.  For example: Colorless Cards, Global Events,
Global Relics (not attached to character-specific unlocks),
Daily/Custom Run modifiers.

 */

import basemod.BaseMod;
import basemod.helpers.RelicType;
import basemod.interfaces.EditCardsSubscriber;
import basemod.interfaces.EditRelicsSubscriber;
import basemod.interfaces.PostUpdateSubscriber;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import downfall.ui.campfire.WheelSpinButton;
import downfall.util.CardIgnore;
import expansioncontent.patches.CardColorEnumPatch;
import expansioncontent.relics.StudyCardRelic;
import expansioncontent.util.CardFilter;
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
public class expansionContentMod implements
        EditCardsSubscriber,
        EditRelicsSubscriber,
        //EditStringsSubscriber,
        //EditKeywordsSubscriber,
        PostUpdateSubscriber {

    @SpireEnum
    public static AbstractCard.CardTags STUDY_HEXAGHOST;
    @SpireEnum
    public static AbstractCard.CardTags STUDY_AWAKENEDONE;
    @SpireEnum
    public static AbstractCard.CardTags STUDY_TIMEEATER;
    @SpireEnum
    public static AbstractCard.CardTags STUDY_CHAMP;
    @SpireEnum
    public static AbstractCard.CardTags STUDY_COLLECTOR;
    @SpireEnum
    public static AbstractCard.CardTags STUDY_SHAPES;
    @SpireEnum
    public static AbstractCard.CardTags STUDY_GUARDIAN;
    @SpireEnum
    public static AbstractCard.CardTags STUDY_AUTOMATON;
    @SpireEnum
    public static AbstractCard.CardTags STUDY_SLIMEBOSS;
    @SpireEnum
    public static AbstractCard.CardTags STUDY;
    public static boolean teleportToWheelTime = false;
    private static String modID;

    public static Color BOSS_CARD_COLOR = new Color(0.443F, 0.231F, 0.286F, 1);

    public expansionContentMod() {
        BaseMod.subscribe(this);

        modID = "expansioncontent";

        BaseMod.addColor(CardColorEnumPatch.CardColorPatch.BOSS,
                BOSS_CARD_COLOR, BOSS_CARD_COLOR, BOSS_CARD_COLOR, BOSS_CARD_COLOR, BOSS_CARD_COLOR, BOSS_CARD_COLOR, BOSS_CARD_COLOR,
                "champResources/images/512/bg_attack_colorless.png", "champResources/images/512/bg_skill_colorless.png",
                "champResources/images/512/bg_power_colorless.png", "champResources/images/512/card_champ_orb.png",
                "champResources/images/1024/bg_attack_colorless.png", "champResources/images/1024/bg_skill_colorless.png",
                "champResources/images/1024/bg_power_colorless.png","champResources/images/1024/card_champ_orb.png");
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
        expansionContentMod expansionContentMod = new expansionContentMod();
    }

    public static String makeID(String idText) {
        return getModID() + ":" + idText;
    }

    private static void autoAddCards()
            throws URISyntaxException, IllegalAccessException, InstantiationException, NotFoundException, ClassNotFoundException {
        ClassFinder finder = new ClassFinder();
        URL url = expansionContentMod.class.getProtectionDomain().getCodeSource().getLocation();
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
            UnlockTracker.unlockCard(card.cardID);
        }
    }

    @Override
    public void receivePostUpdate() {
        if (teleportToWheelTime) {
            WheelSpinButton.doStuff();
            teleportToWheelTime = false;
        }
    }

    @Override
    public void receiveEditRelics() {

        BaseMod.addRelic(new StudyCardRelic(), RelicType.SHARED);
    }

    @Override
    public void receiveEditCards() {
        try {
            autoAddCards();
        } catch (URISyntaxException | IllegalAccessException | InstantiationException | NotFoundException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /*
    @Override
    public void receiveEditStrings() {
        BaseMod.loadCustomStringsFile(CardStrings.class, getModID() + "Resources/localization/eng/Cardstrings.json");
        BaseMod.loadCustomStringsFile(RelicStrings.class, getModID() + "Resources/localization/eng/Relicstrings.json");
        BaseMod.loadCustomStringsFile(PowerStrings.class, getModID() + "Resources/localization/eng/Powerstrings.json");
    }
    */


    public void atb(AbstractGameAction q) {
        AbstractDungeon.actionManager.addToBottom(q);
    }


}
