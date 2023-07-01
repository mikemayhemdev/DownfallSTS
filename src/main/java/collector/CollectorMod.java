package collector;

import basemod.BaseMod;
import basemod.abstracts.CustomSavable;
import basemod.abstracts.CustomUnlockBundle;
import basemod.helpers.CardModifierManager;
import basemod.interfaces.*;
import collector.cardmods.CollectedCardMod;
import collector.patches.CollectiblesPatches.CollectibleCardColorEnumPatch;
import collector.patches.ExtraDeckButtonPatches.TopPanelExtraDeck;
import collector.relics.EmeraldTorch;
import collector.ui.CombatCollectionPileButton;
import collector.util.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.helpers.CardLibrary;
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
        EditCharactersSubscriber,
        PostInitializeSubscriber,
        SetUnlocksSubscriber,
        OnStartBattleSubscriber,
        PostBattleSubscriber,
        StartGameSubscriber,
        PostDungeonUpdateSubscriber, PostRenderSubscriber {
    public static final String SHOULDER1 = "collectorResources/images/char/mainChar/shoulder.png";
    public static final String SHOULDER2 = "collectorResources/images/char/mainChar/shoulderR.png";
    public static final String CORPSE = "collectorResources/images/char/mainChar/corpse.png";
    public static final String CARD_ENERGY_S = "collectorResources/images/512/card_collector_orb.png";
    public static final String TEXT_ENERGY = "collectorResources/images/512/card_small_orb.png";
    private static final String ATTACK_S_ART = "collectorResources/images/512/bg_attack_collector.png";
    private static final String SKILL_S_ART = "collectorResources/images/512/bg_skill_collector.png";
    private static final String POWER_S_ART = "collectorResources/images/512/bg_power_collector.png";
    private static final String ATTACK_L_ART = "collectorResources/images/1024/bg_attack_collector.png";
    private static final String SKILL_L_ART = "collectorResources/images/1024/bg_skill_collector.png";
    private static final String POWER_L_ART = "collectorResources/images/1024/bg_power_collector.png";
    private static final String CARD_ENERGY_L = "collectorResources/images/1024/card_collector_orb.png";
    private static final String CHARSELECT_BUTTON = "collectorResources/images/charSelect/charButton.png";
    private static final String CHARSELECT_PORTRAIT = "collectorResources/images/charSelect/charBG.png";

    public static Color characterColor = CardHelper.getColor(13, 158, 131);
    public static Color potionLabColor = CardHelper.getColor(113, 158, 131);
    private static final String modID = "collector";
    private CustomUnlockBundle unlocks0; // TODO: Set this up
    private CustomUnlockBundle unlocks1;
    private CustomUnlockBundle unlocks2;
    private CustomUnlockBundle unlocks3;
    private CustomUnlockBundle unlocks4;
    public static Color COLLECTIBLE_CARD_COLOR = CardHelper.getColor(13, 158, 153);

    public CollectorMod() {
        BaseMod.subscribe(this);

        BaseMod.addColor(CollectorChar.Enums.COLLECTOR, characterColor, characterColor, characterColor,
                characterColor, characterColor, characterColor, characterColor,
                ATTACK_S_ART, SKILL_S_ART, POWER_S_ART, CARD_ENERGY_S,
                ATTACK_L_ART, SKILL_L_ART, POWER_L_ART,
                CARD_ENERGY_L, TEXT_ENERGY);

        BaseMod.addColor(CollectibleCardColorEnumPatch.CardColorPatch.COLLECTIBLE,
                COLLECTIBLE_CARD_COLOR, COLLECTIBLE_CARD_COLOR, COLLECTIBLE_CARD_COLOR, COLLECTIBLE_CARD_COLOR, COLLECTIBLE_CARD_COLOR, COLLECTIBLE_CARD_COLOR, COLLECTIBLE_CARD_COLOR,
                "collectorResources/images/512/bg_attack_collection.png", "collectorResources/images/512/bg_skill_collection.png",
                "collectorResources/images/512/bg_power_collection.png", CARD_ENERGY_S,
                "collectorResources/images/1024/bg_attack_collection.png", "collectorResources/images/1024/bg_skill_collection.png",
                "collectorResources/images/1024/bg_power_collection.png", CARD_ENERGY_L, TEXT_ENERGY);
    }


    public static String makePath(String resourcePath) {
        return modID + "Resources/" + resourcePath;
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
        CollectorMod collectorMod = new CollectorMod();
    }

    public static String makeID(String idText) {
        return getModID() + ":" + idText;
    }

    @Override
    public void receiveEditCharacters() {
        BaseMod.addCharacter(new CollectorChar("The Collector", CollectorChar.Enums.THE_COLLECTOR), CHARSELECT_BUTTON, CHARSELECT_PORTRAIT, CollectorChar.Enums.THE_COLLECTOR);
    }

    @Override
    public void receiveEditRelics() {
        BaseMod.addRelicToCustomPool(new EmeraldTorch(), CollectorChar.Enums.COLLECTOR);
    }

    public void addPotions() {
        //TODO: Set this up
    }

    @Override
    public void receiveSetUnlocks() {
        //TODO: Set this up
    }

    public void receivePostInitialize() {
        addPotions();
        initializeSavedData();
        BaseMod.addTopPanelItem(new TopPanelExtraDeck());
    }

    @Override
    public void receivePostBattle(AbstractRoom abstractRoom) {
        CollectorCollection.atBattleEnd();
        NewReserves.resetReserves();
    }

    @Override
    public void receiveOnBattleStart(AbstractRoom abstractRoom) {
        CollectorCollection.atBattleStart();
        NewReserves.resetReserves();
    }

    @Override
    public void receiveEditCards() {
        BaseMod.addDynamicVariable(new CollectorSecondMagic());
        try {
            autoAddCards();
        } catch (URISyntaxException | IllegalAccessException | InstantiationException | NotFoundException |
                 ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
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
                        (classInfo, classFinder) -> classInfo.getClassName().startsWith("collector.cards")
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

    private static CombatCollectionPileButton combatCollectionPileButton;

    public static void renderCombatUiElements(SpriteBatch sb) {
        if (Wiz.isInCombat() && AbstractDungeon.player.chosenClass.equals(CollectorChar.Enums.THE_COLLECTOR)) {
            if (combatCollectionPileButton != null) {
                combatCollectionPileButton.setX(AbstractDungeon.overlayMenu.combatDeckPanel.current_x);
                combatCollectionPileButton.render(sb);
            }
        }
    }

    @Override
    public void receivePostDungeonUpdate() {
        if (combatCollectionPileButton != null) {
            combatCollectionPileButton.update();
        }
    }

    @Override
    public void receiveStartGame() {
        if (!CardCrawlGame.loadingSave) {
            CollectorCollection.init();
        }
        combatCollectionPileButton = new CombatCollectionPileButton();
        NewReserves.resetReserves();
        if (AbstractDungeon.player instanceof CollectorChar) {
            ((CollectorChar) AbstractDungeon.player).torchHead = new RenderOnlyTorchHead();
        }
    }

    private void initializeSavedData() {
        BaseMod.addSaveField("CollectorCollection", new CustomSavable<ArrayList<String>>() {
            @Override
            public ArrayList<String> onSave() {
                ArrayList<String> results = new ArrayList<>();
                for (AbstractCard q : CollectorCollection.collection.group) {
                    results.add(q.cardID);
                }
                System.out.println("Collector Saving Collection - cards: ");
                results.stream().forEach(q -> System.out.println(q));
                return results;
            }

            @Override
            public void onLoad(ArrayList<String> strings) {
                for (String s : strings) {
                    System.out.println("Collector Loading Collection Card: " + s);
                    AbstractCard found = CardLibrary.getCopy(s);
                    CardModifierManager.addModifier(found, new CollectedCardMod());
                    if (CollectorCollection.collection == null) {
                        CollectorCollection.init();
                    }
                    CollectorCollection.collection.addToBottom(found);
                }
            }
        });
    }

    //Due to reward scrolling's orthographic camera and render order of rewards, the card needs to be rendered outside of the render method
    public static CollectibleCardReward hoverRewardWorkaround;

    @Override
    public void receivePostRender(SpriteBatch sb) {
        if (hoverRewardWorkaround != null) {
            hoverRewardWorkaround.renderCardOnHover(sb);
            hoverRewardWorkaround = null;
        }
    }
}