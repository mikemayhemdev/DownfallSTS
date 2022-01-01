package collector;

import automaton.util.CardFilter;
import basemod.BaseMod;
import basemod.abstracts.CustomUnlockBundle;
import basemod.interfaces.*;
import collector.Relics.EmeraldTorch;
import collector.Vars.DuoBlock;
import collector.Vars.DuoDamage;
import collector.Vars.DuoMagic;
import collector.actions.FreezeAggroAction;
import collector.cards.Collectibles.*;
import collector.cards.CollectorCards.Attacks.*;
import collector.cards.CollectorCards.Powers.Miser;
import collector.cards.CollectorCards.Powers.Omen;
import collector.cards.CollectorCards.Powers.SelfDestruct;
import collector.cards.CollectorCards.Skills.*;
import collector.patches.CollectibleCardColorEnumPatch;
import collector.patches.ExtraDeckButtonPatches.TopPanelExtraDeck;
import collector.patches.TorchHeadPatches.MonsterIntentPatch;
import collector.patches.TorchHeadPatches.MonsterPowerPatch;
import collector.patches.TorchHeadPatches.MonsterTargetPatch;
import collector.patches.TorchHeadPatches.StanceChangeParticlePatch;
import collector.powers.SoulMark;
import collector.powers.Suffering;
import collector.util.CollectorSecondDamage;
import collector.util.TargetMarker;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import downfall.util.CardIgnore;
import javassist.CtClass;
import javassist.Modifier;
import javassist.NotFoundException;
import org.clapper.util.classutil.*;
import slimebound.powers.SlimedPower;
import theHexaghost.powers.BurnPower;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

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
        PostDrawSubscriber, OnPowersModifiedSubscriber, PostEnergyRechargeSubscriber  {
    public static final String SHOULDER1 = "collectorResources/images/char/mainChar/shoulder.png";
    public static final String SHOULDER2 = "collectorResources/images/char/mainChar/shoulderR.png";
    public static final String CORPSE = "collectorResources/images/char/mainChar/corpse.png";
    public static final String CARD_ENERGY_S = "collectorResources/images/512/card_collector_orb.png";
    public static final String TEXT_ENERGY = "collectorResources/images/512/card_small_orb.png";
    private static final String ATTACK_S_ART = "collectorResources/images/512/bg_attack_.png";
    private static final String SKILL_S_ART = "collectorResources/images/512/bg_skill_.png";
    private static final String POWER_S_ART = "collectorResources/images/512/bg_power_.png";
    private static final String ATTACK_L_ART = "collectorResources/images/1024/bg_attack_.png";
    private static final String SKILL_L_ART = "collectorResources/images/1024/bg_skill_.png";
    private static final String POWER_L_ART = "collectorResources/images/1024/bg_power_.png";
    private static final String CARD_ENERGY_L = "collectorResources/images/1024/card_collector_orb.png";
    private static final String CHARSELECT_BUTTON = "collectorResources/images/charSelect/charButton.png";
    private static final String CHARSELECT_PORTRAIT = "collectorResources/images/charSelect/charBG.png";

    public static Color characterColor = CardHelper.getColor(13, 158, 131);
    public static Color potionLabColor = CardHelper.getColor(113, 158, 131);
    private static String modID = "collector";
    private CustomUnlockBundle unlocks0; // TODO: Set this up
    private CustomUnlockBundle unlocks1;
    private CustomUnlockBundle unlocks2;
    private CustomUnlockBundle unlocks3;
    private CustomUnlockBundle unlocks4;
    public static ArrayList<String> Afflictions = new ArrayList<>();
    public static ArrayList<String> Boons = new ArrayList<>();
    public static HashMap<String, AbstractCard> cardsList;
    public static Color COLLECTIBLE_CARD_COLOR = CardHelper.getColor(13, 158, 153);
    public static int CollectorAggro = 0;
    public static int TorchAggro = 0;
    public static TargetMarker targetMarker;
    public CollectorMod() {
        BaseMod.subscribe(this);

        modID = "collector";

        BaseMod.addColor(CollectorChar.Enums.COLLECTOR, characterColor, characterColor, characterColor,
                characterColor, characterColor, characterColor, characterColor,
                ATTACK_S_ART, SKILL_S_ART, POWER_S_ART, CARD_ENERGY_S,
                ATTACK_L_ART, SKILL_L_ART, POWER_L_ART,
                CARD_ENERGY_L, TEXT_ENERGY);

        BaseMod.addColor(CollectibleCardColorEnumPatch.CardColorPatch.COLLECTIBLE,
                COLLECTIBLE_CARD_COLOR, COLLECTIBLE_CARD_COLOR, COLLECTIBLE_CARD_COLOR, COLLECTIBLE_CARD_COLOR, COLLECTIBLE_CARD_COLOR, COLLECTIBLE_CARD_COLOR, COLLECTIBLE_CARD_COLOR,
                "collectorResources/images/512/bg_attack_colorless.png", "collectorResources/images/512/bg_skill_colorless.png",
                "collectorResources/images/512/bg_power_colorless.png", CARD_ENERGY_S,
                "collectorResources/images/1024/bg_attack_colorless.png", "collectorResources/images/1024/bg_skill_colorless.png",
                "collectorResources/images/1024/bg_power_colorless.png",CARD_ENERGY_L,TEXT_ENERGY);
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
        BaseMod.addRelicToCustomPool(new EmeraldTorch(),CollectorChar.Enums.COLLECTOR);
    }

    public static void ApplyRandomAffliciton(AbstractCreature target, boolean upgraded) {
        String AfflictionftoApply = Afflictions.get((AbstractDungeon.cardRandomRng.random(Afflictions.size() - 1)));
        if (!upgraded) {
            if (AfflictionftoApply.equals(CollectorMod.Afflictions.get(0))) {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(target, target, new WeakPower(target, 1, false)));
            } else if (AfflictionftoApply.equals(CollectorMod.Afflictions.get(1))) {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(target, target, new VulnerablePower(target, 1, false)));
            } else if (AfflictionftoApply.equals(CollectorMod.Afflictions.get(2))) {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(target, target, new PoisonPower(target, target, 3)));
            } else if (AfflictionftoApply.equals(CollectorMod.Afflictions.get(3))) {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(target, target, new SlimedPower(target, target, 4)));
            } else if (AfflictionftoApply.equals(CollectorMod.Afflictions.get(4))) {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(target, target, new BurnPower(target, 8)));
            } else if (AfflictionftoApply.equals(CollectorMod.Afflictions.get(5))) {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(target, target, new Suffering(2,target)));
            } else if (AfflictionftoApply.equals(CollectorMod.Afflictions.get(6))) {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(target, target, new SoulMark(2,target)));
            }
        } else {
            if (AfflictionftoApply.equals(CollectorMod.Afflictions.get(0))) {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(target, target, new WeakPower(target, 2, false)));
            } else if (AfflictionftoApply.equals(CollectorMod.Afflictions.get(1))) {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(target, target, new VulnerablePower(target, 2, false)));
            } else if (AfflictionftoApply.equals(CollectorMod.Afflictions.get(2))) {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(target, target, new PoisonPower(target, target, 4)));
            } else if (AfflictionftoApply.equals(CollectorMod.Afflictions.get(3))) {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(target, target, new SlimedPower(target, target, 6)));
            } else if (AfflictionftoApply.equals(CollectorMod.Afflictions.get(4))) {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(target, target, new BurnPower(target, 11)));
            } else if (AfflictionftoApply.equals(CollectorMod.Afflictions.get(5))) {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(target, target, new Suffering(4,target)));
            }
        }
    }



    public static boolean AfflictionMatch(String stringtocompare) {
        if (stringtocompare.equals(Afflictions.get(0)) || stringtocompare.equals(Afflictions.get(1)) ||
                stringtocompare.equals(Afflictions.get(2)) || stringtocompare.equals(Afflictions.get(3)) ||
                stringtocompare.equals(Afflictions.get(4)) || stringtocompare.equals(Afflictions.get(5))
                || stringtocompare.equals(Afflictions.get(6))) {
            return true;
        }
        return false;
    }

    public void ApplyRandomBoon(AbstractCreature target) {
    }

    @Override
    public void receiveEditCards() {
        BaseMod.addDynamicVariable(new CollectorSecondDamage());
        BaseMod.addDynamicVariable(new DuoDamage());
        BaseMod.addDynamicVariable(new DuoBlock());
        BaseMod.addDynamicVariable(new DuoMagic());

        BaseMod.addCard(new Strike());
        BaseMod.addCard(new Defend());
        BaseMod.addCard(new Contemplate());
        BaseMod.addCard(new SoulStitch());
        BaseMod.addCard(new SoulHarvest());
        BaseMod.addCard(new Bargain());
        BaseMod.addCard(new Consign());
        BaseMod.addCard(new CullingBlow());
        BaseMod.addCard(new DarkSuffusion());
        BaseMod.addCard(new Forgery());
        BaseMod.addCard(new Fever());
        BaseMod.addCard(new SoulForge());
        BaseMod.addCard(new DancingLight());
        BaseMod.addCard(new Bonfire());
        BaseMod.addCard(new Combustibles());
        BaseMod.addCard(new Fireball());
        BaseMod.addCard(new Hellfire());
        BaseMod.addCard(new FlameLash());
        BaseMod.addCard(new FelsteelAegis());
        BaseMod.addCard(new SelfDestruct());
        BaseMod.addCard(new Miser());
        BaseMod.addCard(new Omen());
        BaseMod.addCard(new Wrack());
        BaseMod.addCard(new TagTeam());
        BaseMod.addCard(new Mindflare());
        BaseMod.addCard(new ScorchingRay());
        BaseMod.addCard(new HighStakes());
        BaseMod.addCard(new FingerofDeath());
        BaseMod.addCard(new PainSiphon());
        BaseMod.addCard(new StakingAClaim());

        BaseMod.addCard(new LuckyWick());
        BaseMod.addCard(new CrookedStaff());
        BaseMod.addCard(new CrudeShield());
        BaseMod.addCard(new CultistFeather());
        BaseMod.addCard(new CurledHorns());
        BaseMod.addCard(new DefectCore());
        BaseMod.addCard(new GremlinPoker());
        BaseMod.addCard(new IroncladMask());
        BaseMod.addCard(new JarWormTooth());
        BaseMod.addCard(new LagavullinShell());
        BaseMod.addCard(new LouseSegment());
        BaseMod.addCard(new NobsBoneClub());
        BaseMod.addCard(new SentryCore());
        BaseMod.addCard(new SilentTrophy());
        BaseMod.addCard(new SlimeSample());
        BaseMod.addCard(new VialofOoze());
        BaseMod.addCard(new ViciousClaws());
        BaseMod.addCard(new WatchersStaff());

        UnlockTracker.unlockCard(Strike.ID);
        UnlockTracker.unlockCard(Defend.ID);
        UnlockTracker.unlockCard(Contemplate.ID);
        UnlockTracker.unlockCard(SoulStitch.ID);
        UnlockTracker.unlockCard(SoulHarvest.ID);
        UnlockTracker.unlockCard(Bargain.ID);
        UnlockTracker.unlockCard(DancingLight.ID);
        UnlockTracker.unlockCard(Consign.ID);
        UnlockTracker.unlockCard(CullingBlow.ID);
        UnlockTracker.unlockCard(DarkSuffusion.ID);
        UnlockTracker.unlockCard(Combustibles.ID);
        UnlockTracker.unlockCard(Forgery.ID);
        UnlockTracker.unlockCard(Fever.ID);
        UnlockTracker.unlockCard(Fireball.ID);
        UnlockTracker.unlockCard(Hellfire.ID);
        UnlockTracker.unlockCard(FelsteelAegis.ID);
        UnlockTracker.unlockCard(Bonfire.ID);
        UnlockTracker.unlockCard(SoulForge.ID);
        UnlockTracker.unlockCard(FlameLash.ID);
        UnlockTracker.unlockCard(Miser.ID);
        UnlockTracker.unlockCard(SelfDestruct.ID);
        UnlockTracker.unlockCard(Omen.ID);
        UnlockTracker.unlockCard(Wrack.ID);
        UnlockTracker.unlockCard(TagTeam.ID);
        UnlockTracker.unlockCard(Mindflare.ID);
        UnlockTracker.unlockCard(ScorchingRay.ID);
        UnlockTracker.unlockCard(HighStakes.ID);
        UnlockTracker.unlockCard(FingerofDeath.ID);
        UnlockTracker.unlockCard(PainSiphon.ID);
        UnlockTracker.unlockCard(StakingAClaim.ID);

        UnlockTracker.unlockCard(LuckyWick.ID);
        UnlockTracker.unlockCard(CrookedStaff.ID);
        UnlockTracker.unlockCard(CrudeShield.ID);
        UnlockTracker.unlockCard(CultistFeather.ID);
        UnlockTracker.unlockCard(CurledHorns.ID);
        UnlockTracker.unlockCard(DefectCore.ID);
        UnlockTracker.unlockCard(GremlinPoker.ID);
        UnlockTracker.unlockCard(IroncladMask.ID);
        UnlockTracker.unlockCard(JarWormTooth.ID);
        UnlockTracker.unlockCard(LagavullinShell.ID);
        UnlockTracker.unlockCard(LouseSegment.ID);
        UnlockTracker.unlockCard(NobsBoneClub.ID);
        UnlockTracker.unlockCard(SentryCore.ID);
        UnlockTracker.unlockCard(SilentTrophy.ID);
        UnlockTracker.unlockCard(SlimeSample.ID);
        UnlockTracker.unlockCard(VialofOoze.ID);
        UnlockTracker.unlockCard(ViciousClaws.ID);
        UnlockTracker.unlockCard(WatchersStaff.ID);
    }

    public void addPotions() {
    }

    @Override
    public void receiveSetUnlocks() {
        //TODO: Set this up

    }

    public void receivePostInitialize() {
        addPotions();
        targetMarker = new TargetMarker();
        //BaseMod.addEvent(new AddEventParams.Builder(AccursedBlacksmithAutomaton.ID, AccursedBlacksmithAutomaton.class) //Event ID//
        //Event Character//
        // .playerClass(AutomatonChar.Enums.THE_AUTOMATON)
        //Existing Event to Override//
        // .overrideEvent(AccursedBlacksmith.ID)
        //Event Type//
        // .eventType(EventUtils.EventType.FULL_REPLACE)
        // .create());

        BaseMod.addTopPanelItem(new TopPanelExtraDeck());
    }

    @Override
    public void receivePostBattle(AbstractRoom abstractRoom) {
        CollectorCollection.atBattleEnd();
    }

    @Override
    public void receiveStartGame() {
        Afflictions.add(WeakPower.POWER_ID);
        Afflictions.add(VulnerablePower.POWER_ID);
        Afflictions.add(PoisonPower.POWER_ID);
        Afflictions.add(SlimedPower.POWER_ID);
        Afflictions.add(BurnPower.POWER_ID);
        Afflictions.add(Suffering.POWER_ID);
        Afflictions.add(SoulMark.POWER_ID);

        Boons.add(StrengthPower.POWER_ID);
        Boons.add(DexterityPower.POWER_ID);
        Boons.add(ArtifactPower.POWER_ID);
        Boons.add(PlatedArmorPower.POWER_ID);
        Boons.add(VigorPower.POWER_ID);
        Boons.add("Block");

        CollectorCollection.init();
        //CollectorChar.TorchHeadHelper = new TorchHeadHelper();
        //System.out.println("Torch Friend!");
    }
    @Override
    public void receivePowersModified() {
        if (AbstractDungeon.currMapNode != null &&
                AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT &&
                AbstractDungeon.player instanceof CollectorChar
        ) {
            ArrayList<PowerTip> tips = new ArrayList<>();
            TorchChar d = ((CollectorChar) AbstractDungeon.player).torch;
            if (d.isDead) {
                tips.add(new PowerTip(TorchChar.characterStrings.NAMES[0], TorchChar.characterStrings.TEXT[0]));
            } else {
                for (AbstractPower p : d.powers) {
                    if (p.region48 != null) {
                        tips.add(new PowerTip(p.name, p.description, p.region48));
                    } else {
                        tips.add(new PowerTip(p.name, p.description, p.img));
                    }
                }
            }
        }
    }

    @Override
    public void receiveOnBattleStart(AbstractRoom abstractRoom) {
        CollectorCollection.atBattleStart();
        StanceChangeParticlePatch.ParticleGeneratorRenderPatch.prevPlayer = null;
        StanceChangeParticlePatch.WrathParticleRenderPatch.prevPlayer = null;
        MonsterIntentPatch.prevPlayer = null;
        MonsterPowerPatch.prevPlayer = null;
        MonsterTargetPatch.prevPlayer = null;

        TorchAggro = 0;
        CollectorAggro = 0;

        FreezeAggroAction.frozen = false;

        receivePostEnergyRecharge();
    }
    @Override
    public void receivePostEnergyRecharge() {
        CollectorChar.frontChangedThisTurn = false;
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