package automaton;

import automaton.cards.Separator;
import automaton.cards.*;
import automaton.cards.goodstatus.*;
import automaton.events.*;
import automaton.potions.BuildAFunctionPotion;
import automaton.potions.BurnAndBuffPotion;
import automaton.potions.CleanCodePotion;
import automaton.potions.FreeFunctionsPotion;
import automaton.relics.*;
import automaton.util.AutoVar;
import automaton.util.CardFilter;
import basemod.BaseMod;
import basemod.ReflectionHacks;
import basemod.abstracts.CustomUnlockBundle;
import basemod.eventUtil.AddEventParams;
import basemod.eventUtil.EventUtils;
import basemod.helpers.CardModifierManager;
import basemod.helpers.RelicType;
import basemod.interfaces.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.widepotions.WidePotionsMod;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.status.*;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.dungeons.Exordium;
import com.megacrit.cardcrawl.dungeons.TheCity;
import com.megacrit.cardcrawl.events.city.BackToBasics;
import com.megacrit.cardcrawl.events.shrines.AccursedBlacksmith;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import downfall.cardmods.EtherealMod;
import downfall.cardmods.ExhaustMod;
import downfall.downfallMod;
import downfall.util.CardIgnore;
import guardian.patches.BottledStasisPatch;
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
        SetUnlocksSubscriber,
        OnStartBattleSubscriber,
        PostBattleSubscriber,
        StartGameSubscriber,
        PostDrawSubscriber {
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
    private static String modID = "bronze";
    private CustomUnlockBundle unlocks0;
    private CustomUnlockBundle unlocks1;
    private CustomUnlockBundle unlocks2;
    private CustomUnlockBundle unlocks3;
    private CustomUnlockBundle unlocks4;

    @SpireEnum
    public static AbstractCard.CardTags ENCODES;
    @SpireEnum
    public static AbstractCard.CardTags BAD_COMPILE;
    @SpireEnum
    public static AbstractCard.CardTags MODIFIES_OUTPUT;
    @SpireEnum
    public static AbstractCard.CardTags GOOD_STATUS;
    @SpireEnum
    public static AbstractCard.CardTags NO_TEXT;
    @SpireEnum
    public static AbstractCard.CardTags ADDS_NO_CARDTEXT;
    @SpireEnum
    public static AbstractCard.CardTags SPECIAL_COMPILE_TEXT;

    public AutomatonMod() {
        BaseMod.subscribe(this);

        modID = "bronze";

        BaseMod.addColor(AutomatonChar.Enums.BRONZE_AUTOMATON, placeholderColor, placeholderColor, placeholderColor,
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

        BaseMod.addRelicToCustomPool(new BronzeCore(), AutomatonChar.Enums.BRONZE_AUTOMATON);
        BaseMod.addRelicToCustomPool(new CableSpool(), AutomatonChar.Enums.BRONZE_AUTOMATON);
        BaseMod.addRelic(new DecasWashers(), RelicType.SHARED);
        BaseMod.addRelic(new DonusWashers(), RelicType.SHARED);
        BaseMod.addRelicToCustomPool(new ElectromagneticCoil(), AutomatonChar.Enums.BRONZE_AUTOMATON);
        BaseMod.addRelicToCustomPool(new ProtectiveGoggles(), AutomatonChar.Enums.BRONZE_AUTOMATON);
        BaseMod.addRelicToCustomPool(new Mallet(), AutomatonChar.Enums.BRONZE_AUTOMATON);
        BaseMod.addRelicToCustomPool(new PlatinumCore(), AutomatonChar.Enums.BRONZE_AUTOMATON);
        BaseMod.addRelic(new MakeshiftBattery(), RelicType.SHARED);
        BaseMod.addRelic(new BronzeIdol(), RelicType.SHARED);
        BaseMod.addRelicToCustomPool(new SilverBullet(), AutomatonChar.Enums.BRONZE_AUTOMATON);
        BaseMod.addRelicToCustomPool(new BottledCode(), AutomatonChar.Enums.BRONZE_AUTOMATON);
        BaseMod.registerBottleRelic(BottledStasisPatch.inBottledCode, new BottledCode());
        BaseMod.addRelicToCustomPool(new Timepiece(), AutomatonChar.Enums.BRONZE_AUTOMATON);
        BaseMod.addRelic(new AnalyticalCore(), RelicType.SHARED);
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

        BaseMod.addPotion(BuildAFunctionPotion.class, Color.FIREBRICK, Color.GRAY, Color.TAN, BuildAFunctionPotion.POTION_ID, AutomatonChar.Enums.THE_AUTOMATON);
        BaseMod.addPotion(BurnAndBuffPotion.class, Color.RED, Color.GREEN, Color.CLEAR, BurnAndBuffPotion.POTION_ID);
        BaseMod.addPotion(CleanCodePotion.class, Color.CORAL, Color.PURPLE, Color.MAROON, CleanCodePotion.POTION_ID, AutomatonChar.Enums.THE_AUTOMATON);
        BaseMod.addPotion(FreeFunctionsPotion.class, Color.BLACK, Color.PURPLE, Color.GRAY, FreeFunctionsPotion.POTION_ID, AutomatonChar.Enums.THE_AUTOMATON);

        if (Loader.isModLoaded("widepotions")) {
            WidePotionsMod.whitelistSimplePotion(BuildAFunctionPotion.POTION_ID);
            WidePotionsMod.whitelistSimplePotion(BurnAndBuffPotion.POTION_ID);
            WidePotionsMod.whitelistSimplePotion(CleanCodePotion.POTION_ID);
            WidePotionsMod.whitelistSimplePotion(FreeFunctionsPotion.POTION_ID);
        }
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

                AutomatonChar.Enums.THE_AUTOMATON
        );
    }


    @Override
    public void receiveOnBattleStart(AbstractRoom abstractRoom) {
        FunctionHelper.init();
    }

    @Override
    public void receivePostBattle(AbstractRoom abstractRoom) {
        FunctionHelper.doStuff = false;
        FunctionHelper.functionsCompiledThisCombat = 0;
        if (FunctionHelper.held != null) {
            FunctionHelper.held.clear();
        }
    }

    public static CompileDisplayPanel compileDisplayPanel;

    @Override
    public void receiveStartGame() {
        EasyInfoDisplayPanel.specialDisplays.clear();
        compileDisplayPanel = new CompileDisplayPanel();
        EasyInfoDisplayPanel.specialDisplays.add(compileDisplayPanel);
       // EasyInfoDisplayPanel.specialDisplays.add(new BossMechanicDisplayPanel());
        if (FunctionHelper.held != null) {
            FunctionHelper.doStuff = false;
            FunctionHelper.held.clear();
            FunctionHelper.genPreview();
        }
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
        if (abstractCard.type == AbstractCard.CardType.STATUS) {
            if (AbstractDungeon.player.hasRelic(BronzeIdol.ID)) {
                if (!abstractCard.hasTag(GOOD_STATUS)) {

                    AbstractCard newStatus = getGoodStatus(abstractCard);

                    int index = AbstractDungeon.player.hand.group.indexOf(abstractCard);
                    AbstractDungeon.player.hand.group.set(index, newStatus);

                    AbstractDungeon.player.hand.refreshHandLayout();
                }
            }
        }
    }

    public static AbstractCard getGoodStatus(AbstractCard ogStatus) {
        AbstractCard newStatus = null;
        if (ogStatus instanceof Dazed) {
            newStatus = new Daze();
        } else if (ogStatus instanceof Burn) {
            newStatus = new Ignite();
        } else if (ogStatus instanceof VoidCard) {
            newStatus = new IntoTheVoid();
        } else if (ogStatus instanceof Slimed) {
            newStatus = new UsefulSlime();
        } else if (ogStatus instanceof Wound) {
            newStatus = new GrievousWound();
        } else {
            newStatus = new UnknownStatus();
            if (ogStatus.isEthereal) CardModifierManager.addModifier(newStatus, new EtherealMod());
            if (ogStatus.exhaust) CardModifierManager.addModifier(newStatus, new ExhaustMod());
        }
        if (ogStatus.upgraded) newStatus.upgrade();
        return newStatus;
    }
}