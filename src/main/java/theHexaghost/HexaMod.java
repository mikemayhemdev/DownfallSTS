package theHexaghost;

import basemod.BaseMod;
import basemod.ReflectionHacks;
import basemod.interfaces.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.mod.stslib.Keyword;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.scenes.TheBottomScene;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.vfx.scene.InteractableTorchEffect;
import javassist.CtClass;
import javassist.Modifier;
import javassist.NotFoundException;
import org.clapper.util.classutil.*;
import theHexaghost.relics.*;
import theHexaghost.util.CardFilter;
import theHexaghost.util.CardIgnore;
import theHexaghost.util.CardNoSeen;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;

@SuppressWarnings({"ConstantConditions", "unused", "WeakerAccess"})
@SpireInitializer
public class HexaMod implements
        EditCardsSubscriber,
        EditRelicsSubscriber,
        EditStringsSubscriber,
        EditKeywordsSubscriber,
        EditCharactersSubscriber,
        OnStartBattleSubscriber,
        PostBattleSubscriber,
        PreRoomRenderSubscriber,
        PostDeathSubscriber {
    public static final String SHOULDER1 = "hexamodResources/images/char/mainChar/shoulder.png";
    public static final String SHOULDER2 = "hexamodResources/images/char/mainChar/shoulder2.png";
    public static final String CORPSE = "hexamodResources/images/char/mainChar/corpse.png";
    private static final String ATTACK_S_ART = "hexamodResources/images/512/bg_attack_hexaghost.png";
    private static final String SKILL_S_ART = "hexamodResources/images/512/bg_skill_hexaghost.png";
    private static final String POWER_S_ART = "hexamodResources/images/512/bg_power_hexaghost.png";
    public static final String CARD_ENERGY_S = "hexamodResources/images/512/card_hexaghost_orb.png";
    public static final String TEXT_ENERGY = "hexamodResources/images/512/card_small_orb.png";
    private static final String ATTACK_L_ART = "hexamodResources/images/1024/bg_attack_hexaghost.png";
    private static final String SKILL_L_ART = "hexamodResources/images/1024/bg_skill_hexaghost.png";
    private static final String POWER_L_ART = "hexamodResources/images/1024/bg_power_hexaghost.png";
    private static final String CARD_ENERGY_L = "hexamodResources/images/1024/card_hexaghost_orb.png";
    private static final String CHARSELECT_BUTTON = "hexamodResources/images/charSelect/charButton.png";
    private static final String CHARSELECT_PORTRAIT = "hexamodResources/images/charSelect/charBG.png";
    private static String modID;

    public static boolean renderFlames = false;

    public static boolean unsealed = false;

    public static Color placeholderColor = new Color(0.25F, 1, 0.1F, 1);

    public HexaMod() {
        BaseMod.subscribe(this);

        modID = "hexamod";

        BaseMod.addColor(TheHexaghost.Enums.GHOST_GREEN, placeholderColor, placeholderColor, placeholderColor,
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
        HexaMod hexaMod = new HexaMod();
    }

    public static String makeID(String idText) {
        return getModID() + ":" + idText;
    }

    @Override
    public void receiveEditCharacters() {
        BaseMod.addCharacter(new TheHexaghost("the Hexaghost", TheHexaghost.Enums.THE_SPIRIT),
                CHARSELECT_BUTTON, CHARSELECT_PORTRAIT, TheHexaghost.Enums.THE_SPIRIT);
    }

    @Override
    public void receiveEditRelics() {
        BaseMod.addRelicToCustomPool(new SpiritBrand(), TheHexaghost.Enums.GHOST_GREEN);
        BaseMod.addRelicToCustomPool(new InflammatoryLetter(), TheHexaghost.Enums.GHOST_GREEN);
        BaseMod.addRelicToCustomPool(new IceCube(), TheHexaghost.Enums.GHOST_GREEN);
        BaseMod.addRelicToCustomPool(new JarOfFuel(), TheHexaghost.Enums.GHOST_GREEN);
        BaseMod.addRelicToCustomPool(new MatchstickCase(), TheHexaghost.Enums.GHOST_GREEN);
        BaseMod.addRelicToCustomPool(new RecyclingMachine(), TheHexaghost.Enums.GHOST_GREEN);
        BaseMod.addRelicToCustomPool(new SoulConsumer(), TheHexaghost.Enums.GHOST_GREEN);
        BaseMod.addRelicToCustomPool(new SoulOfChaos(), TheHexaghost.Enums.GHOST_GREEN);
        BaseMod.addRelicToCustomPool(new TheBrokenSeal(), TheHexaghost.Enums.GHOST_GREEN);
        BaseMod.addRelicToCustomPool(new UnbrokenSoul(), TheHexaghost.Enums.GHOST_GREEN);
    }

    @Override
    public void receiveEditCards() {
        try {
            autoAddCards();
        } catch (URISyntaxException | IllegalAccessException | InstantiationException | NotFoundException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private static void autoAddCards()
            throws URISyntaxException, IllegalAccessException, InstantiationException, NotFoundException, ClassNotFoundException {
        ClassFinder finder = new ClassFinder();
        URL url = HexaMod.class.getProtectionDomain().getCodeSource().getLocation();
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
            if (cls.hasAnnotation(CardNoSeen.class)) {
                UnlockTracker.hardUnlockOverride(card.cardID);
            } else {
                UnlockTracker.unlockCard(card.cardID);
            }
        }
    }


    @Override
    public void receiveEditStrings() {
        BaseMod.loadCustomStringsFile(CardStrings.class, getModID() + "Resources/localization/eng/Cardstrings.json");

        BaseMod.loadCustomStringsFile(RelicStrings.class, getModID() + "Resources/localization/eng/Relicstrings.json");

        BaseMod.loadCustomStringsFile(CharacterStrings.class, getModID() + "Resources/localization/eng/Charstrings.json");
    }

    @Override
    public void receiveEditKeywords() {
        Gson gson = new Gson();
        String json = Gdx.files.internal(getModID() + "Resources/localization/eng/Keywordstrings.json").readString(String.valueOf(StandardCharsets.UTF_8));
        com.evacipated.cardcrawl.mod.stslib.Keyword[] keywords = gson.fromJson(json, com.evacipated.cardcrawl.mod.stslib.Keyword[].class);

        if (keywords != null) {
            for (Keyword keyword : keywords) {
                BaseMod.addKeyword(getModID() + "", keyword.PROPER_NAME, keyword.NAMES, keyword.DESCRIPTION);
            }
        }
    }

    public void atb(AbstractGameAction q) {
        AbstractDungeon.actionManager.addToBottom(q);
    }


    @Override
    public void receiveOnBattleStart(AbstractRoom abstractRoom) {
        GhostflameHelper.init();
        if (AbstractDungeon.player instanceof TheHexaghost) {
            renderFlames = true;
            if (AbstractDungeon.scene instanceof TheBottomScene) {
                ArrayList<InteractableTorchEffect> torches = (ArrayList<InteractableTorchEffect>) ReflectionHacks.getPrivate(AbstractDungeon.scene, TheBottomScene.class, "torches");
                torches.clear();
            }
        }
    }

    @Override
    public void receivePostBattle(AbstractRoom abstractRoom) {
        renderFlames = false;
    }

    public void receivePreRoomRender(SpriteBatch sb) {
        if (renderFlames) {
            GhostflameHelper.render(sb);
        }
    }

    @Override
    public void receivePostDeath() {
        renderFlames = false;
    }
}
