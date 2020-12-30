package collector;

import automaton.util.CardFilter;
import basemod.BaseMod;
import basemod.abstracts.CustomUnlockBundle;
import basemod.interfaces.*;
import charbosses.bosses.Defect.CharBossDefect;
import charbosses.bosses.Ironclad.CharBossIronclad;
import charbosses.bosses.Silent.CharBossSilent;
import charbosses.bosses.Watcher.CharBossWatcher;
import collector.cards.Collectibles.*;
import collector.cards.*;
import collector.powers.SoulSnare;
import collector.util.CollectionReward;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.exordium.*;
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

    public static Color placeholderColor = new Color(29, 108, 161, 1);
    public static Color potionLabColor = new Color(29, 108, 161, 1);
    private static String modID = "collector";
    private CustomUnlockBundle unlocks0; // TODO: Set this up
    private CustomUnlockBundle unlocks1;
    private CustomUnlockBundle unlocks2;
    private CustomUnlockBundle unlocks3;
    private CustomUnlockBundle unlocks4;
    public static ArrayList<String> Afflictions = new ArrayList<>();
    public static ArrayList<String> Boons = new ArrayList<>();
    public static HashMap<String, AbstractCard> cardsList;

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
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(target, target, new SoulSnare(2)));
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
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(target, target, new SoulSnare(4)));
            }
        }
    }

    public static void GetCollectible(AbstractMonster collectedMonster) {
        if (cardsList.containsKey(collectedMonster.id)) {
            AbstractCard NewCollectible = cardsList.get(collectedMonster.id).makeStatEquivalentCopy();
            CollectionReward.CollectPool.add(NewCollectible);
        } else CollectionReward.CollectPool.add(new LuckyWick());
    }
    public static void PerpetualEffect(AbstractCard card) {
        CollectorCollection.BattleCollection.addToBottom(card);
        AbstractDungeon.player.discardPile.removeCard(card);
    }
    public static boolean AfflicitonMatch(String stringtocompare) {
        if (stringtocompare.equals(Afflictions.get(0)) || stringtocompare.equals(Afflictions.get(1)) ||
                stringtocompare.equals(Afflictions.get(2)) || stringtocompare.equals(Afflictions.get(3)) ||
                stringtocompare.equals(Afflictions.get(4)) || stringtocompare.equals(Afflictions.get(5))) {
            return true;
        }
        return false;
    }

    public void ApplyRandomBoon(AbstractCreature target) {
    }

    @Override
    public void receiveEditCards() {
        BaseMod.addCard(new Strike());
        BaseMod.addCard(new Defend());
        BaseMod.addCard(new Contemplate());
        BaseMod.addCard(new SoulStitch());
        BaseMod.addCard(new SoulHarvest());

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
    public void receiveOnBattleStart(AbstractRoom abstractRoom) {
    }

    @Override
    public void receivePostBattle(AbstractRoom abstractRoom) {
    }

    @Override
    public void receiveStartGame() {
        CollectorCollection.Collection = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        CollectorCollection.Collection.addToBottom(new LuckyWick());
        CollectorCollection.Collection.addToBottom(new LuckyWick());
        CollectorCollection.Collection.addToBottom(new LuckyWick());
        CollectorCollection.Collection.addToBottom(new LuckyWick());
        Afflictions.add(WeakPower.POWER_ID);
        Afflictions.add(VulnerablePower.POWER_ID);
        Afflictions.add(PoisonPower.POWER_ID);
        Afflictions.add(SlimedPower.POWER_ID);
        Afflictions.add(BurnPower.POWER_ID);
        Afflictions.add(SoulSnare.POWER_ID);

        Boons.add(StrengthPower.POWER_ID);
        Boons.add(DexterityPower.POWER_ID);
        Boons.add(ArtifactPower.POWER_ID);
        Boons.add(PlatedArmorPower.POWER_ID);
        Boons.add(VigorPower.POWER_ID);
        Boons.add("Block");
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

    void init() {
        cardsList.put(GremlinWizard.ID, new CrookedStaff());
        cardsList.put(GremlinWarrior.ID, new CrudeShield());
        cardsList.put(Cultist.ID, new CultistFeather());
        cardsList.put(GremlinFat.ID, new CurledHorns());
        cardsList.put(CharBossDefect.ID, new DefectCore());
        cardsList.put(GremlinThief.ID, new GremlinPoker());
        cardsList.put(CharBossIronclad.ID, new IroncladMask());
        cardsList.put(JawWorm.ID, new JarWormTooth());
        cardsList.put(Lagavulin.ID, new LagavullinShell());
        cardsList.put(LouseNormal.ID, new LouseSegment());
        cardsList.put(LouseDefensive.ID, new LouseSegment());
        cardsList.put(GremlinNob.ID, new NobsBoneClub());
        cardsList.put(Sentry.ID, new SentryCore());
        cardsList.put(CharBossSilent.ID, new SilentTrophy());
        cardsList.put(AcidSlime_L.ID, new SlimeSample());
        cardsList.put(AcidSlime_M.ID, new SlimeSample());
        cardsList.put(AcidSlime_S.ID, new SlimeSample());
        cardsList.put(SpikeSlime_L.ID, new VialofOoze());
        cardsList.put(SpikeSlime_M.ID, new VialofOoze());
        cardsList.put(SpikeSlime_S.ID, new VialofOoze());
        cardsList.put(GremlinTsundere.ID, new ViciousClaws());
        cardsList.put(CharBossWatcher.ID, new WatchersStaff());
    }
}