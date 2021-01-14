package charbosses.bosses;

import charbosses.BossMechanicDisplayPanel;
import charbosses.relics.AbstractCharbossRelic;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import downfall.downfallMod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

public abstract class AbstractBossDeckArchetype {
    public static final Logger logger = LogManager.getLogger(downfallMod.class.getName());

    public static MonsterStrings bossMechanicString = CardCrawlGame.languagePack.getMonsterStrings("downfall:BossMechanics");

    public String bossMechanicName;
    public String bossMechanicDesc;

    public int maxHPModifier;
    public int actNum;

    /*

    /// WEIGHT CONSTANTS ///

    private static int commonCardWeight = 6;
    private static int uncommonCardWeight = 3;
    private static int rareCardWeight = 1;

    private static int commonRelicWeight = 6;
    private static int uncommonRelicWeight = 3;
    private static int rareRelicWeight = 1;
    private static int shopRelicWeight = 1;

    /// END WEIGHT CONSTANTS///

    /// TUNING CONSTANTS ///

    public int[] cardRemovalsPerAct = new int[]{1, 1, 2};
    public int[] cardUpgradesPerAct = new int[]{1, 1, 2};
    public int[] synergyCardAcquisitionsPerAct = new int[]{0, 1, 2};
    public int[] globalCardAcquisitionsPerAct = new int[]{2, 1, 0};
    public int[] globalEventPoolAcquisitionsPerAct = new int[]{1, 1, 1};
    public int[] globalRelicAcquisitionsPerAct = new int[]{0, 0, 1};

    /// END TUNING CONSTANTS ///

    public String ID;

    private ArrayList<AbstractCharbossRelic> globalRelicPool;
    private ArrayList<AbstractCharbossRelic> globalEventPoolAct1;
    private ArrayList<AbstractCharbossRelic> globalEventPoolAct2;
    private ArrayList<AbstractCharbossRelic> globalEventPoolAct3;
    private ArrayList<AbstractCharbossRelic> globalEventPool;
    private ArrayList<AbstractCharbossRelic> energyRelicPool;
    private ArrayList<AbstractCharbossRelic> bossNonEnergyRelicPool;

    public int safeIndex = 0;

    public ArrayList<AbstractBossCard> cards;
    protected AbstractCharbossRelic starterRelic;
    private ArrayList<AbstractBossCard> allCards;
    private ArrayList<AbstractBossCard> curseCards;
    private ArrayList<AbstractBossCard> synergyCards;
    public AbstractBossCard[] signatureCardPerAct;
    private ArrayList<AbstractBossCard> classGlobalCards;
    private ArrayList<AbstractBossCard> starterCards;
    private String loggerClassName;
    private String loggerArchetypeName;
    private AbstractCharbossRelic[] signatureRelicPerAct;
    private ArrayList<String> blacklistedRelics;
    private ArrayList<String> blacklistedCards;
    */

    private AbstractCharBoss currentBoss;

    /*
    public boolean upgradeAllPowers = false;
    public boolean upgradeAllSkills = false;
    public boolean upgradeAllAttacks = false;
    */

    public void addedPreBattle() {
        initializeBossPanel();
    }

    public abstract void initializeBonusRelic();

    public boolean looped = false;
    public int turn = 0;

    public ArrayList<AbstractCard> getThisTurnCards() {
        return new ArrayList<>();
    }

    public void addToList(ArrayList<AbstractCard> c, AbstractCard q, boolean upgraded) {
        if (upgraded) q.upgrade();
        c.add(q);
    }

    public void initializeBossPanel() {
        if (bossMechanicDesc != null) {
            BossMechanicDisplayPanel.mechanicName = bossMechanicName;
            BossMechanicDisplayPanel.mechanicDesc = bossMechanicDesc;
        }
    }

    public void addToList(ArrayList<AbstractCard> c, AbstractCard q) {
        addToList(c, q, false);
    }

    private String ID;

    public AbstractBossDeckArchetype(String id, String loggerClassName, String loggerArchetypeName) {

        this.ID = id;
        if (AbstractDungeon.actNum != 4)
            AbstractDungeon.lastCombatMetricKey = ID;

        /*
        this.ID = id;
        this.allCards = new ArrayList<AbstractBossCard>();
        this.starterCards = new ArrayList<AbstractBossCard>();
        this.classGlobalCards = new ArrayList<AbstractBossCard>();
        this.synergyCards = new ArrayList<AbstractBossCard>();
        this.signatureCardPerAct = new AbstractBossCard[3];
        this.signatureRelicPerAct = new AbstractCharbossRelic[3];
        this.globalRelicPool = new ArrayList<AbstractCharbossRelic>();
        this.loggerClassName = loggerClassName;
        this.loggerArchetypeName = loggerArchetypeName;
        this.curseCards = new ArrayList<AbstractBossCard>();
        this.blacklistedCards = new ArrayList<String>();
        this.blacklistedRelics = new ArrayList<String>();
        this.globalEventPool = new ArrayList<AbstractCharbossRelic>();
        this.globalEventPoolAct1 = new ArrayList<AbstractCharbossRelic>();
        this.globalEventPoolAct2 = new ArrayList<AbstractCharbossRelic>();
        this.globalEventPoolAct3 = new ArrayList<AbstractCharbossRelic>();
        this.energyRelicPool = new ArrayList<AbstractCharbossRelic>();
        this.bossNonEnergyRelicPool = new ArrayList<AbstractCharbossRelic>();
        this.cards = new ArrayList<AbstractBossCard>();
        */


    }

    /*
    private void initializeGlobalEventRelics() {
        //Global Events

        //No Note for Yourself, doesn't make sense ehre
        this.globalEventPool.add(new CBR_BonfireSpirits());
        this.globalEventPool.add(new CBR_DesignerInSpire());
        this.globalEventPool.add(new CBR_DivineFountain());
        this.globalEventPool.add(new CBR_Duplicator());
        this.globalEventPool.add(new CBR_FaceTrader());
        this.globalEventPool.add(new CBR_GoldenShrine());
        //No Lab, bosses don't use potions
        this.globalEventPool.add(new CBR_MatchAndKeep());
        this.globalEventPool.add(new CBR_OminousForge());
        this.globalEventPool.add(new CBR_Purifier());
        //No Woman in Blue, bosses don't use potions
        this.globalEventPool.add(new CBR_Transmogrifier());
        this.globalEventPool.add(new CBR_UpgradeShrine());
        this.globalEventPool.add(new CBR_WeMeetAgain());
        this.globalEventPool.add(new CBR_WheelOfChange());

        //Act 1 Events
        this.globalEventPoolAct1.add(new CBR_BigFish());
        //No Dead Adventurer, too generic
        this.globalEventPoolAct1.add(new CBR_GoldenIdolEvent());
        this.globalEventPoolAct1.add(new CBR_Mushroom());
        this.globalEventPoolAct1.add(new CBR_LivingWall());
        this.globalEventPoolAct1.add(new CBR_ScrapOoze());
        this.globalEventPoolAct1.add(new CBR_ShiningLight());
        this.globalEventPoolAct1.add(new CBR_Cleric());
        this.globalEventPoolAct1.add(new CBR_Serpent());
        //No World of Goop, just refers to gold
        this.globalEventPoolAct1.add(new CBR_WingStatue());

        //Act 2 Events
        this.globalEventPoolAct2.add(new CBR_AncientWriting());
        this.globalEventPoolAct2.add(new CBR_Augmenter(0));
        this.globalEventPoolAct2.add(new CBR_Bandits());
        //No Council of Ghosts - Apparition is too strong in context of a boss
        //No Cursed Book - Nilry's Codex is probably bad for the boss, Necronomicon too powerful, so it'd have to be Enchridon every time
        //No Forgotten Altar, Bloody Altar doesn't do much
        //No Knowing Skull until a suite of colorless cards is made (maybe never)
        this.globalEventPoolAct2.add(new CBR_OldBeggar());
        this.globalEventPoolAct2.add(new CBR_PleadingVagrant());
        this.globalEventPoolAct2.add(new CBR_Colosseum());
        //No Joust - only refers to gold
        this.globalEventPoolAct2.add(new CBR_Library());
        this.globalEventPoolAct2.add(new CBR_Mausoleum());
        this.globalEventPoolAct2.add(new CBR_Nest());
        this.globalEventPoolAct2.add(new CBR_Vampires());

        //Act 3 Events
        this.globalEventPoolAct3.add(new CBR_Falling());
        //Mind Bloom
        //No Secret Portal, doesn't make sense for a boss
        //No Sensory Stone until colorless cards (maybe never)
        //Sphere
        //Moai Head
        //Tomb of Lord Red Mask
        //Winding Halls
        this.globalEventPoolAct3.add(new CBR_DivineFountain());  //Intentionally made an Act 3 only event
    }

    public void addToDeck(AbstractBossCard c) {
        addToDeck(c, false);
    }

    public void addToDeck(AbstractBossCard c, boolean upgraded) {
        if (upgraded) c.upgrade();
        AbstractCharBoss.boss.masterDeck.addToTop(c.makeStatEquivalentCopy());
    }

*/
    public void addRelic(AbstractCharbossRelic r) {
        r.instantObtain(AbstractCharBoss.boss);

    }
    /*

    private void initializeRelics() {

        //// COMMONS ////

        this.globalRelicPool.add(new CBR_Anchor());
        this.globalRelicPool.add(new CBR_ArtOfWar());
        this.globalRelicPool.add(new CBR_BagOfMarbles());
        this.globalRelicPool.add(new CBR_BagOfPreparation());
        this.globalRelicPool.add(new CBR_BirdFacedUrn());
        this.globalRelicPool.add(new CBR_Boot());
        this.globalRelicPool.add(new CBR_CaptainsWheel());
        this.globalRelicPool.add(new CBR_CentennialPuzzle());
        this.globalRelicPool.add(new CBR_DreamCatcher());
        this.globalRelicPool.add(new CBR_Lantern());
        this.globalRelicPool.add(new CBR_Orichalcum());
        this.globalRelicPool.add(new CBR_Omamori());
        this.globalRelicPool.add(new CBR_OrangePellets());  ///Overwritten from original rarity
        this.globalRelicPool.add(new CBR_Strawberry());
        this.globalRelicPool.add(new CBR_WarPaint());
        this.globalRelicPool.add(new CBR_Whetstone());
        this.globalRelicPool.add(new CBR_BlueCandle());
        this.globalRelicPool.add(new CBR_Abacus());   ///Overwritten from original rarity
        this.globalRelicPool.add(new CBR_CentennialPuzzle());
        this.globalRelicPool.add(new CBR_DuvuDoll());   ///Overwritten from original rarity
        this.globalRelicPool.add(new CBR_Pear());
        this.globalRelicPool.add(new CBR_SmoothStone());
        // IRONCLAD - Magic Flower


        //// UNCOMMONS ////

        this.globalRelicPool.add(new CBR_HappyFlower());   ///Overwritten from original rarity
        this.globalRelicPool.add(new CBR_Akabeko());   ///Overwritten from original rarity
        this.globalRelicPool.add(new CBR_ArtOfWar());
        this.globalRelicPool.add(new CBR_BlueCandle());
        this.globalRelicPool.add(new CBR_ClockworkSouvenir());   ///Overwritten from original rarity
        this.globalRelicPool.add(new CBR_HornCleat());
        this.globalRelicPool.add(new CBR_LeesWaffle());   ///Overwritten from original rarity
        this.globalRelicPool.add(new CBR_LetterOpener());
        this.globalRelicPool.add(new CBR_Mango());   ///Overwritten from original rarity
        this.globalRelicPool.add(new CBR_Matroyshka());
        this.globalRelicPool.add(new CBR_MedicalKit());   ///Overwritten from original rarity
        this.globalRelicPool.add(new CBR_OrnamentalFan());
        this.globalRelicPool.add(new CBR_Orrery());   ///Overwritten from original rarity
        this.globalRelicPool.add(new CBR_ThreadAndNeedle());   ///Overwritten from original rarity
        this.globalRelicPool.add(new CBR_Vajra());   ///Overwritten from original rarity
        // IRONCLAD - Red Skull

        //// RARE ////

        this.globalRelicPool.add(new CBR_DollysMirror());   ///Overwritten from original rarity
        this.globalRelicPool.add(new CBR_FossilizedHelix());
        this.globalRelicPool.add(new CBR_Girya(0));
        this.globalRelicPool.add(new CBR_Ginger());
        this.globalRelicPool.add(new CBR_HandDrill());   ///Overwritten from original rarity
        this.globalRelicPool.add(new CBR_IceCream());
        this.globalRelicPool.add(new CBR_IncenseBurner());
        this.globalRelicPool.add(new CBR_Kunai());   ///Overwritten from original rarity
        this.globalRelicPool.add(new CBR_MercuryHourglass());
        this.globalRelicPool.add(new CBR_PeacePipe());
        this.globalRelicPool.add(new CBR_PenNib());   ///Overwritten from original rarity
        this.globalRelicPool.add(new CBR_Shovel());
        this.globalRelicPool.add(new CBR_Torii());
        this.globalRelicPool.add(new CBR_TungstenRod());
        this.globalRelicPool.add(new CBR_ToxicEgg());
        this.globalRelicPool.add(new CBR_MoltenEgg());
        this.globalRelicPool.add(new CBR_FrozenEgg());

        //////////


        this.energyRelicPool.add(new CBR_CursedKey());
        this.energyRelicPool.add(new CBR_PhilosopherStone());
        this.energyRelicPool.add(new CBR_Ectoplasm());
        this.energyRelicPool.add(new CBR_FusionHammer());
        this.energyRelicPool.add(new CBR_CoffeeDripper());
        this.energyRelicPool.add(new CBR_RunicDome());
        ////IRONCLAD - Mark of Pain

        this.bossNonEnergyRelicPool.add(new CBR_CallingBell());
        this.bossNonEnergyRelicPool.add(new CBR_EmptyCage());
        this.bossNonEnergyRelicPool.add(new CBR_BlackStar());
        this.bossNonEnergyRelicPool.add(new CBR_SneckoEye());
        this.bossNonEnergyRelicPool.add(new CBR_TinyHouse());
        // this.bossNonEnergyRelicPool.add(new CBR_LizardTail());   ///Overwritten from original rarity
        this.bossNonEnergyRelicPool.add(new CBR_BronzeScales());   ///Overwritten from original rarity
        this.bossNonEnergyRelicPool.add(new CBR_Pocketwatch());   ///Overwritten from original rarity
        this.bossNonEnergyRelicPool.add(new CBR_Shuriken());   ///Overwritten from original rarity
        this.bossNonEnergyRelicPool.add(new CBR_Calipers());
        this.bossNonEnergyRelicPool.add(new CBR_SelfFormingClay());   ///Overwritten from original rarity

    }

    private static AbstractBossCard getRandomCardFromSource(ArrayList<AbstractBossCard> source) {
        return (AbstractBossCard) source.get(AbstractDungeon.monsterRng.random(source.size() - 1)).makeCopy();
    }

    protected void initializeCurses() {

        this.curseCards.add(new EnInjury());
        this.curseCards.add(new EnDecay());
        this.curseCards.add(new EnDoubt());
        this.curseCards.add(new EnRegret());
        this.curseCards.add(new EnWrithe());
        this.curseCards.add(new EnShame());
        this.curseCards.add(new EnPain());
    }
    */

    public void initialize() {
        //Overwritten in each Archetype Base
    }


    /*
    protected void blacklistCard(String id) {
        this.blacklistedCards.add(id);
    }

    protected void blacklistRelic(String id) {
        this.blacklistedRelics.add(id);
    }

    public String addRandomCurse(AbstractCharBoss boss, String loggerSource) {

        Collections.shuffle(this.curseCards);

        if (boss.hasRelic("Omamori")) {
            //SlimeboundMod.logger.info("detected boss has Omamori");
            CBR_Omamori oma = (CBR_Omamori) boss.getRelic("Omamori");
            if (oma.counter > 0) {
                logger.info(loggerSource + " tried to add a " + this.curseCards.get(0).name + ", but Omamori blocked it.");
                oma.use(this.curseCards.get(0).name);
                return "";
            }

        }
        logger.info(loggerSource + " added 1 " + this.curseCards.get(0).name + ".");
        boss.masterDeck.addToTop(this.curseCards.get(0).makeCopy());
        return this.curseCards.get(0).name;

    }


    protected void addCardToList(AbstractBossCard c, CardBenefitType type) {
        switch (type) {
            case SIGNATUREACT1:
                this.signatureCardPerAct[0] = c;
                break;
            case SIGNATUREACT2:
                this.signatureCardPerAct[1] = c;
                break;
            case SIGNATUREACT3:
                this.signatureCardPerAct[2] = c;
                break;
            case SUPPORTING:
                this.synergyCards.add(c);
                break;
            case GLOBAL:
                this.classGlobalCards.add(c);
                break;
            default:
        }
    }


    protected void addRelicToList(AbstractCharbossRelic r, CardBenefitType type) {
        switch (type) {
            case SIGNATUREACT1:
                signatureRelicPerAct[0] = r;
                break;
            case SIGNATUREACT2:
                signatureRelicPerAct[1] = r;
                break;
            case SIGNATUREACT3:
                signatureRelicPerAct[2] = r;
                break;
            case ENERGY:
                this.energyRelicPool.add(r);
                break;
            case BOSSNONENERGY:
                this.bossNonEnergyRelicPool.add(r);
                break;
            case GLOBAL:
                this.globalRelicPool.add(r);
                break;
            default:
        }
    }


    protected void addToStarterDeck(AbstractBossCard c) {
        this.starterCards.add(c);
    }

    private ArrayList<AbstractBossCard> sortCardListToRarity(ArrayList<AbstractBossCard> cards) {
        ArrayList<AbstractBossCard> sortedCards = new ArrayList<AbstractBossCard>();


        boolean[] validRarities = new boolean[3];

        outer:
        for (AbstractBossCard c : cards) {
            if (c.rarity == CardRarity.COMMON) {
                validRarities[0] = true;
                break outer;
            }
        }
        outer:
        for (AbstractBossCard c : cards) {
            if (c.rarity == CardRarity.UNCOMMON) {
                validRarities[1] = true;
                break outer;
            }
        }
        outer:
        for (AbstractBossCard c : cards) {
            if (c.rarity == CardRarity.RARE) {
                validRarities[2] = true;
                break outer;
            }
        }

        int maxRange = 0;
        int[] rarityRange = new int[6];
        if (validRarities[0]) {
            rarityRange[0] = maxRange;
            maxRange += commonCardWeight;
            rarityRange[1] = maxRange;
        }
        if (validRarities[1]) {
            rarityRange[2] = maxRange;
            maxRange += uncommonCardWeight;
            rarityRange[3] = maxRange;
        }
        if (validRarities[2]) {
            rarityRange[4] = maxRange;
            maxRange += rareCardWeight;
            rarityRange[5] = maxRange;
        }

        int rarityRoll = AbstractDungeon.cardRng.random(0, maxRange);
        CardRarity rarity;
        if (rarityRoll >= rarityRange[0] && rarityRoll <= rarityRange[1] && validRarities[0]) {
            rarity = CardRarity.COMMON;
        } else if (rarityRoll >= rarityRange[2] && rarityRoll <= rarityRange[3] && validRarities[1]) {
            rarity = CardRarity.UNCOMMON;
        } else if (rarityRoll >= rarityRange[4] && rarityRoll <= rarityRange[5] && validRarities[2]) {
            rarity = CardRarity.RARE;
        } else {
            //If no rarities were valid, mark it as Special, which will return a single Shiv /Madness/ThisIsNoLongerUsedAtAll
            rarity = CardRarity.SPECIAL;
        }

        if (rarity == CardRarity.SPECIAL) {
            logger.info("WARNING: No valid cards were available!  Shiv is added.");
            sortedCards.add(new EnShiv());
        } else {
            for (AbstractBossCard c : cards) {
                if (c.rarity == rarity) {
                    sortedCards.add(c);
                }
            }
        }

        return sortedCards;
    }

    private ArrayList<AbstractCharbossRelic> sortRelicListToRarity(ArrayList<AbstractCharbossRelic> relics) {
        ArrayList<AbstractCharbossRelic> sortedRelics = new ArrayList<AbstractCharbossRelic>();

        boolean[] validRarities = new boolean[4];

        outer:
        for (AbstractCharbossRelic r : relics) {
            if (r.tier == AbstractRelic.RelicTier.COMMON) {
                validRarities[0] = true;
                break outer;
            }
        }
        outer:
        for (AbstractCharbossRelic r : relics) {
            if (r.tier == AbstractRelic.RelicTier.UNCOMMON) {
                validRarities[1] = true;
                break outer;
            }
        }
        outer:
        for (AbstractCharbossRelic r : relics) {
            if (r.tier == AbstractRelic.RelicTier.RARE) {
                validRarities[2] = true;
                break outer;
            }
        }
        outer:
        for (AbstractCharbossRelic r : relics) {
            if (r.tier == AbstractRelic.RelicTier.SHOP) {
                validRarities[3] = true;
                break outer;
            }
        }

        int maxRange = 0;
        int[] rarityRange = new int[8];
        if (validRarities[0]) {
            rarityRange[0] = maxRange;
            maxRange += commonRelicWeight;
            rarityRange[1] = maxRange;
        }
        if (validRarities[1]) {
            rarityRange[2] = maxRange;
            maxRange += uncommonRelicWeight;
            rarityRange[3] = maxRange;
        }
        if (validRarities[2]) {
            rarityRange[4] = maxRange;
            maxRange += rareRelicWeight;
            rarityRange[5] = maxRange;
        }
        if (validRarities[3]) {
            rarityRange[6] = maxRange;
            maxRange += shopRelicWeight;
            rarityRange[7] = maxRange;
        }

        int rarityRoll = AbstractDungeon.cardRng.random(0, maxRange);
        AbstractRelic.RelicTier rarity;
        if (rarityRoll >= rarityRange[0] && rarityRoll <= rarityRange[1] && validRarities[0]) {
            rarity = AbstractRelic.RelicTier.COMMON;
        } else if (rarityRoll >= rarityRange[2] && rarityRoll <= rarityRange[3] && validRarities[1]) {
            rarity = AbstractRelic.RelicTier.UNCOMMON;
        } else if (rarityRoll >= rarityRange[4] && rarityRoll <= rarityRange[5] && validRarities[2]) {
            rarity = AbstractRelic.RelicTier.RARE;
        } else if (rarityRoll >= rarityRange[6] && rarityRoll <= rarityRange[7] && validRarities[2]) {
            rarity = AbstractRelic.RelicTier.SHOP;
        } else {
            //If no rarities were valid, mark it as Special, which will return a single Circlet
            rarity = AbstractRelic.RelicTier.SPECIAL;
        }

        if (rarity == AbstractRelic.RelicTier.SPECIAL) {
            sortedRelics.add(new CBR_Circlet());
            logger.info("WARNING: No valid relics were available!  Circlet is added.");

        } else {
            for (AbstractCharbossRelic r : relics) {
                if (r.tier == rarity) {
                    sortedRelics.add(r);
                }
            }
        }

        return sortedRelics;
    }

    private AbstractBossCard getStrikeOrDefend() {
        for (AbstractBossCard c : this.starterCards) {
            if (c.hasTag(AbstractCard.CardTags.STARTER_DEFEND) || c.hasTag(AbstractCard.CardTags.STARTER_STRIKE)) {
                return c;
            }
        }
        return null;
    }

    private AbstractBossCard getCardToUpgrade(ArrayList<AbstractBossCard> cards) {
        Collections.shuffle(cards);
        for (AbstractBossCard c : cards) {
            if (!c.upgraded) {
                return c;
            }
        }
        return null;
    }

    public void removeRelicsFromList(ArrayList<AbstractCharbossRelic> list) {
        for (String s : this.blacklistedRelics) {
            ArrayList<AbstractCharbossRelic> relicsToRemove = new ArrayList<>();
            for (AbstractCharbossRelic r : list) {
                if (r.relicId.equals(s)) {
                    relicsToRemove.add(r);
                }
            }
            if (relicsToRemove.size() > 0) {
                for (AbstractCharbossRelic r : relicsToRemove) {
                    if (r.relicId.equals(s)) {
                        list.remove(r);
                    }
                }
            }
        }
    }

    public void simulateBuild(AbstractCharBoss boss) {
        this.currentBoss = boss;
        initializeCurses();

        initializeGlobalEventRelics();
        initializeRelics();


        logger.info("Initializing Boss : " + loggerArchetypeName + " " + loggerClassName);
        //Initialize Starter Deck
        if (this.starterCards.size() > 0) {
            for (AbstractBossCard c : this.starterCards) {
                cards.add(c);
                logger.info("Adding card to Starter Deck: " + c.name);
            }
        }
        //Initialize Neow Relic
        AbstractRelic neowRelic = new CBR_NeowsBlessing();
        ((CBR_NeowsBlessing) neowRelic).instantObtain(boss);

        //Initialize Starter Relic
        starterRelic.instantObtain(boss);

        //Remove Blacklisted cards from the global pool
        if (this.blacklistedCards.size() > 0) {
            for (String s : this.blacklistedCards) {
                ArrayList<AbstractBossCard> cardsToRemove = new ArrayList<>();
                for (AbstractBossCard c : classGlobalCards) {
                    if (c.cardID.equals(s)) {
                        cardsToRemove.add(c);
                    }
                }
                if (cardsToRemove.size() > 0) {
                    for (AbstractBossCard c : cardsToRemove) {
                        if (c.cardID.equals(s)) {
                            classGlobalCards.remove(c);
                        }
                    }
                }
            }
        }

        //Remove Blacklisted Relics from the global pool
        if (this.blacklistedRelics.size() > 0) {
            removeRelicsFromList(this.globalRelicPool);
            removeRelicsFromList(this.globalEventPool);
            removeRelicsFromList(this.globalEventPoolAct1);
            removeRelicsFromList(this.globalEventPoolAct2);
            removeRelicsFromList(this.globalEventPoolAct3);
            removeRelicsFromList(this.energyRelicPool);
        }


        logger.info("Boss's potential Synergy Cards:");
        for (AbstractBossCard c : this.synergyCards) {
            logger.info(c.name);
        }

        logger.info("Boss's potential Class Global Cards:");
        for (AbstractBossCard c : this.classGlobalCards) {
            logger.info(c.name);
        }


        //Simulate acquired/removed/upgraded cards per Act
        for (int actIndex = 0; actIndex < AbstractDungeon.actNum; actIndex++) {
            safeIndex = Math.min(actIndex, 2); //just in case for the future, where actIndex might go past 2.

            logger.info("Simulating run results from Act " + (actIndex + 1) + " out of " + AbstractDungeon.actNum);

            //Initialize Energy relic if this is an act 3 boss
            if (actIndex == 2 && this.energyRelicPool.size() > 0) {
                logger.info("In Act " + (actIndex + 1) + ", Boss gained this Boss Energy Relic:");
                AbstractCharbossRelic randomRelic = this.energyRelicPool.get(AbstractDungeon.cardRng.random(0, this.energyRelicPool.size() - 1));
                randomRelic.instantObtain(boss);
                this.energyRelicPool.remove(randomRelic);
                logger.info(randomRelic.name);
            }

            //Initialize Boss non-energy relic if this is an act 3 boss
            if (actIndex == 1 && this.bossNonEnergyRelicPool.size() > 0) {
                logger.info("In Act " + (actIndex + 1) + ", Boss gained this Boss Non-Energy Relic:");
                AbstractCharbossRelic randomRelic = this.bossNonEnergyRelicPool.get(AbstractDungeon.cardRng.random(0, this.bossNonEnergyRelicPool.size() - 1));
                randomRelic.instantObtain(boss);
                this.bossNonEnergyRelicPool.remove(randomRelic);
                logger.info(randomRelic.name);
            }

            //Remove any Signature relics out of the Global pool if they are present there
            for (int i2 = 0; i2 < 3; i2++) {
                if (this.signatureRelicPerAct[i2] != null) {
                    AbstractCharbossRelic relicToRemove = null;
                    for (AbstractCharbossRelic r : this.globalRelicPool) {
                        if (r.relicId.equals(this.signatureRelicPerAct[i2].relicId)) {
                            relicToRemove = r;
                        }
                    }
                    if (relicToRemove != null) this.globalRelicPool.remove(relicToRemove);
                }
            }

            //Add Signature Relic if one is declared
            if (boss == null) logger.info("Boss is returning NULL!");
            if (actIndex < 3) {
                logger.info("In Act " + (actIndex + 1) + ", Boss gained these Signature Relics:");
                if (this.signatureRelicPerAct[actIndex] == null) {
                    logger.info("Signature Relic is returning Null for this Act. Choosing a global instead.");
                    addRandomGlobalRelic(actIndex, boss, cards);
                } else {
                    this.signatureRelicPerAct[actIndex].instantObtain(boss);
                    logger.info(this.signatureRelicPerAct[actIndex].name);
                    if (actIndex == 0 && signatureRelicPerAct[actIndex].tier == AbstractRelic.RelicTier.BOSS)
                        logger.info("WARNING!  Act 1 Signature Relic is a Boss Relic, which is not attainable normally.");
                }
            } else {
                logger.info("The Run is too long, so in act " + (actIndex + 1) + ", Boss obtained another Global Relic:");

                addRandomGlobalRelic(actIndex, boss, cards);
            }
            AbstractBossDeckArchetype.logger.info("Boss's current max HP is " + AbstractCharBoss.boss.maxHealth);
            logger.info("Boss's potential Class Global Relics:");
            for (AbstractCharbossRelic r : this.globalRelicPool) {
                logger.info(r.name);
            }

            //Add Global Relics based on the act and the number of declared simulated relics per act

            if (globalRelicAcquisitionsPerAct[Math.min(actIndex, 2)] > 0) {
                logger.info("In Act " + (actIndex + 1) + ", Boss gained these Global Relics:");
                for (int i = 0; i < globalRelicAcquisitionsPerAct[actIndex]; i++) {
                    addRandomGlobalRelic(actIndex, boss, cards);
                }

            }


            ArrayList<AbstractCharbossRelic> validEventRelics = new ArrayList<AbstractCharbossRelic>();

            if (this.globalEventPool.size() > 0) {
                validEventRelics.addAll(this.globalEventPool);
            }
            switch (actIndex % 3) {
                case 0:
                    if (this.globalEventPoolAct1.size() > 0) {
                        validEventRelics.addAll(this.globalEventPoolAct1);
                    }
                    break;

                case 1:
                    logger.info(this.globalEventPoolAct2.size());
                    if (this.globalEventPoolAct2.size() > 0) {
                        validEventRelics.addAll(this.globalEventPoolAct2);
                    }
                    break;

                case 2:
                    if (this.globalEventPoolAct3.size() > 0) {
                        validEventRelics.addAll(this.globalEventPoolAct3);
                    }
                    break;

            }

            AbstractBossDeckArchetype.logger.info("Boss's current max HP is " + AbstractCharBoss.boss.maxHealth);
            logger.info("Boss's potential Act " + (actIndex + 1) + " Event Relics:");
            for (AbstractCharbossRelic r : validEventRelics) {
                logger.info(r.name);
            }

            //Add Event-simulation Relics based on the act and the number of declared simulated Events per act
            logger.info("In Act " + (actIndex + 1) + ", Boss gained these Event Relics:");


            if (globalEventPoolAcquisitionsPerAct[safeIndex] > 0) {
                if (validEventRelics.size() > 0) {
                    for (int i2 = 0; i2 < globalEventPoolAcquisitionsPerAct[safeIndex]; i2++) {
                        Collections.shuffle(validEventRelics);
                        AbstractCharbossRelic randomRelic = validEventRelics.get(0);
                        logger.info(randomRelic.name);
                        randomRelic.instantObtain(boss);
                        switch (actIndex % 3) {
                            case 0:
                                this.globalEventPoolAct1.remove(randomRelic);
                            case 1:
                                this.globalEventPoolAct2.remove(randomRelic);
                            case 2:
                                this.globalEventPoolAct3.remove(randomRelic);
                        }
                        this.globalEventPool.remove(randomRelic);
                    }
                }
            }

            //Add Signature card, if one is declared for this Act
            logger.info("In Act " + (actIndex + 1) + ", Boss gained these Signature cards:");
            if (this.signatureCardPerAct[actIndex % 3] != null) {
                eggCheck(this.signatureCardPerAct[actIndex % 3]);
                cards.add(this.signatureCardPerAct[actIndex % 3]);
                logger.info(this.signatureCardPerAct[actIndex % 3].name);
            }


            //Add Synergy cards to the deck based on the act and the number of declared simulated Card Acquisitions per act
            logger.info("In Act " + (actIndex + 1) + ", Boss gained these Synergy cards:");
            if (this.synergyCards.size() > 0) {
                if (synergyCardAcquisitionsPerAct[safeIndex] > 0) {
                    for (int i2 = 0; i2 < synergyCardAcquisitionsPerAct[safeIndex]; i2++) {
                        addRandomSynergyCard("Initialization");
                    }
                }
            }

            //Add Class Global cards to the deck based on the act and the number of declared simulated Card Acquisitions per act
            logger.info("In Act " + (actIndex + 1) + ", Boss gained these Global Class cards:");
            if (this.classGlobalCards.size() > 0) {
                if (globalCardAcquisitionsPerAct[safeIndex] > 0) {
                    for (int i2 = 0; i2 < globalCardAcquisitionsPerAct[safeIndex]; i2++) {
                        addRandomGlobalClassCard("Initialization");
                    }
                }
            }


            //Remove Strikes and Defends based on the act and the number of declared simulated Card Removals per act
            logger.info("In Act " + (actIndex + 1) + ", Boss removed these cards from their deck:");
            if (cardRemovalsPerAct[actIndex] > 0) {
                for (int i2 = 0; i2 < cardRemovalsPerAct[actIndex]; i2++) {
                    AbstractBossCard cut = getStrikeOrDefend();
                    removeBasicCard("Initialization");
                }
            }

            //Upgrade cards at random based on the Act and the number of declared simulated Card Upgrads per act
            logger.info("In Act " + (actIndex + 1) + ", Boss upgraded these cards:");
            if (cardUpgradesPerAct[actIndex] > 0) {
                for (int i2 = 0; i2 < cardUpgradesPerAct[actIndex]; i2++) {
                    upgradeRandomCard("Initializzation");
                }
            }

            AbstractBossDeckArchetype.logger.info("Boss's current max HP is " + AbstractCharBoss.boss.maxHealth);
            logger.info("Finished simulating run results from Act " + (actIndex + 1) + " out of " + AbstractDungeon.actNum);
        }

        logger.info("Boss's final decklist:");
        for (AbstractBossCard c : cards) {
            logger.info(c.name);
            boss.masterDeck.addToTop(c);
        }

        logger.info("Boss's final relic list:");
        for (AbstractCharbossRelic r : boss.relics) {
            logger.info(r.name);
        }

    }

    public void eggCheck(AbstractBossCard c) {
        if (c.type == AbstractCard.CardType.ATTACK && upgradeAllAttacks) {
            c.upgrade();
            currentBoss.getRelic("Molten Egg 2").onTrigger();
        } else if (c.type == AbstractCard.CardType.SKILL && upgradeAllSkills) {
            c.upgrade();
            currentBoss.getRelic("Toxic Egg 2").onTrigger();
        } else if (c.type == AbstractCard.CardType.POWER && upgradeAllPowers) {
            c.upgrade();
            currentBoss.getRelic("Frozen Egg 2").onTrigger();
        }
    }

    public String removeBasicCard(String debugName) {
        AbstractBossCard cut = getStrikeOrDefend();
        if (cut != null) {
            logger.info(debugName + " removed a " + cut.name);
            cards.remove(cut);
            return cut.name;
        }
        return "";
    }

    public String upgradeRandomCard(String debugName) {
        AbstractBossCard up = getCardToUpgrade(cards);
        if (up != null) {
            logger.info(debugName + " upgraded a " + up.name);
            String storedName = up.name;
            up.upgrade();
            return storedName;
        }
        return "";
    }


    public String addRandomGlobalRelic(int actIndex, AbstractCharBoss boss, String loggerName, ArrayList<AbstractBossCard> cards) {
        logger.info("Global relic has been requested by " + loggerName + ", global pool size = " + this.globalRelicPool.size());
        if (this.globalRelicPool.size() > 0) {

            ArrayList<AbstractCharbossRelic> sortedRelics = sortRelicListToRarity(this.globalRelicPool);
            int random = AbstractDungeon.cardRng.random(0, sortedRelics.size() - 1);
            AbstractCharbossRelic randomRelic = sortedRelics.get(random);
            randomRelic.instantObtain(boss);
            this.globalRelicPool.remove(randomRelic);
            if (loggerName.equals("")) {
                logger.info(randomRelic.name);
            } else {
                logger.info(loggerName + " added " + randomRelic.name + ".");
            }
            return randomRelic.name;
        }
        return "";
    }

    public String addRandomGlobalRelicOfRarity(AbstractCharBoss boss, String loggerName, AbstractRelic.RelicTier tier) {
        logger.info("Global relic has been requested by " + loggerName + ", global pool size = " + this.globalRelicPool.size());
        if (this.globalRelicPool.size() > 0) {

            ArrayList<AbstractCharbossRelic> sortedRelics = sortRelicListToRarity(this.globalRelicPool);
            ArrayList<AbstractCharbossRelic> invalid = sortRelicListToRarity(this.globalRelicPool);
            for (AbstractCharbossRelic r : sortedRelics) {
                if (r.tier != tier) invalid.add(r);
            }
            for (AbstractCharbossRelic r : invalid) {
                sortedRelics.remove(r);
            }
            int random = AbstractDungeon.cardRng.random(0, sortedRelics.size() - 1);
            AbstractCharbossRelic randomRelic = sortedRelics.get(random);
            randomRelic.instantObtain(boss);
            this.globalRelicPool.remove(randomRelic);
            if (loggerName.equals("")) {
                logger.info(randomRelic.name);
            } else {
                logger.info(loggerName + " added " + randomRelic.name + ".");
            }
            return randomRelic.name;
        }
        return "";
    }

    public String addRandomGlobalRelic(int actIndex, AbstractCharBoss boss, ArrayList<AbstractBossCard> cards) {
        String name = addRandomGlobalRelic(actIndex, boss, "", cards);
        return name;
    }

    public void addRandomGlobalRelic(AbstractCharBoss boss, ArrayList<AbstractBossCard> cards) {
        addRandomGlobalRelic(0, boss, "", cards);

    }

    public void addRandomGlobalRelic(AbstractCharBoss boss, ArrayList<AbstractBossCard> cards, AbstractRelic.RelicTier tier) {
        addRandomGlobalRelic(0, boss, "", cards);

    }

    public void addSpecificRelic(AbstractCharbossRelic relic, AbstractCharBoss boss, String loggerName, ArrayList<AbstractBossCard> cards) {

        relic.instantObtain(boss);
        this.globalRelicPool.remove(relic);
        if (loggerName.equals("")) {
            logger.info(relic.name);
        } else {
            logger.info(loggerName + " added " + relic.name + ".");
        }
    }

    public String addRandomGlobalClassCard(String debugName) {
        if (this.classGlobalCards.size() > 0) {
            ArrayList<AbstractBossCard> sortedCards = sortCardListToRarity(this.classGlobalCards);
            int random = AbstractDungeon.cardRng.random(0, sortedCards.size() - 1);
            AbstractBossCard randomCard = sortedCards.get(random);
            eggCheck(randomCard);
            cards.add((AbstractBossCard) randomCard.makeCopy());
            logger.info(debugName + " added " + randomCard.name + ".");
            return randomCard.name;
        } else {

            logger.info("ERROR: Global class card was requested, but none remain in the list.  Adding no card.");
            return "";
        }

    }

    public void addSpecificCard(String debugName, AbstractBossCard card) {
        eggCheck(card);
        cards.add((AbstractBossCard) card.makeCopy());
        logger.info(debugName + " added " + card.name + ".");
    }

    public String addRandomSynergyCard(String debugName) {
        if (this.synergyCards.size() > 0) {
            ArrayList<AbstractBossCard> sortedCards = sortCardListToRarity(this.synergyCards);
            int random = AbstractDungeon.cardRng.random(0, sortedCards.size() - 1);
            AbstractBossCard randomCard = sortedCards.get(random);
            eggCheck(randomCard);
            cards.add((AbstractBossCard) randomCard.makeCopy());
            logger.info(debugName + " added " + randomCard.name + ".");
            return randomCard.name;
        } else {
            logger.info("No synergy cards to add.  Adding a class card instead.");
            addRandomGlobalClassCard(debugName);
            return "";
        }
    }


    public AbstractBossCard getRandomCard() {
        return getRandomCardFromSource(this.allCards);
    }

    public AbstractBossCard getRandomCard(ArrayList<AbstractBossCard> cards) {
        return this.getRandomCard(cards, this.allCards);
    }

    public AbstractBossCard getRandomCard(ArrayList<AbstractBossCard> cards, ArrayList<AbstractBossCard> source) {
        AbstractBossCard c = getRandomCardFromSource(source);
        if (c.limit <= cards.size()) {
            int count = 0;
            for (AbstractBossCard actIndex : cards) {
                if (actIndex.cardID.equals(c.cardID)) {
                    count++;
                }
            }
            if (count >= c.limit) {
                return this.getRandomCard(cards, source);
            } else {
                return c;
            }
        } else {
            return c;
        }
    }

    public AbstractBossCard getRandomCard(ArrayList<AbstractBossCard> cards, ArrayList<AbstractBossCard> source, CardRarity rarity) {
        return this.getRandomCard(cards, source, rarity, false);
    }

    public AbstractBossCard getRandomCard(ArrayList<AbstractBossCard> cards, CardRarity rarity) {
        return this.getRandomCard(cards, this.allCards, rarity, false);
    }

    public AbstractBossCard getRandomCard(ArrayList<AbstractBossCard> cards, CardRarity rarity, boolean blacklist) {
        return this.getRandomCard(cards, this.allCards, rarity, blacklist);
    }

    public AbstractBossCard getRandomCard(ArrayList<AbstractBossCard> cards, ArrayList<AbstractBossCard> source, CardRarity rarity, boolean blacklist) {
        AbstractBossCard c = getRandomCardFromSource(source);
        if ((c.rarity == rarity) == blacklist) {
            return this.getRandomCard(cards, source, rarity, blacklist);
        }
        if (c.limit <= cards.size()) {
            int count = 0;
            for (AbstractBossCard actIndex : cards) {
                if (actIndex.cardID.equals(c.cardID)) {
                    count++;
                }
            }
            if (count >= c.limit) {
                return this.getRandomCard(cards, source);
            } else {
                return c;
            }
        } else {
            return c;
        }
    }

    public enum CardBenefitType {
        SIGNATUREACT1,
        SIGNATUREACT2,
        SIGNATUREACT3,
        SUPPORTING,
        ENERGY,
        BOSSNONENERGY,
        GLOBAL;

        CardBenefitType() {
        }
    }
    */
}
