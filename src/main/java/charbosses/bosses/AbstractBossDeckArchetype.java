package charbosses.bosses;

import java.util.ArrayList;
import java.util.Collections;

import charbosses.cards.AbstractBossCard;
import charbosses.cards.colorless.EnShiv;
import charbosses.cards.curses.*;
import charbosses.cards.red.EnPerfectedStrike;
import charbosses.relics.*;
import charbosses.relics.EventRelics.CBR_Vampires;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;
import com.megacrit.cardcrawl.cards.colorless.Madness;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.Circlet;
import evilWithin.EvilWithinMod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import slimebound.SlimeboundMod;

public abstract class AbstractBossDeckArchetype {
	public static final Logger logger = LogManager.getLogger(EvilWithinMod.class.getName());

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

	private int[] cardRemovalsPerAct = new int [] {1,1,1};
	private int[] cardUpgradesPerAct = new int [] {2,2,2};
	private int[] synergyCardAcquisitionsPerAct = new int [] {1,2,2};
	private int[] globalCardAcquisitionsPerAct = new int [] {2,1,1};
	private int[] globalEventPoolAcquisitionsPerAct = new int [] {1,1,1};
	private int[] globalRelicAcquisitionsPerAct = new int [] {2,2,2};

	/// END TUNING CONSTANTS ///

	private static void initializeGlobalEventRelics(){
		//Global Events

		//Act 1 Events

		//Act 2 Events
		globalEventPoolAct2.add(new CBR_Vampires());

		//Act 3 Events
	}

	private static void initializeRelics(){
		globalRelicPool.add(new CBR_Anchor());
		globalRelicPool.add(new CBR_BirdFacedUrn());
		globalRelicPool.add(new CBR_Boot());
		globalRelicPool.add(new CBR_BronzeScales());
		globalRelicPool.add(new CBR_CaptainsWheel());
		globalRelicPool.add(new CBR_DuvuDoll());
		globalRelicPool.add(new CBR_Girya());
		globalRelicPool.add(new CBR_HandDrill());
		globalRelicPool.add(new CBR_HappyFlower());
		globalRelicPool.add(new CBR_IceCream());
		globalRelicPool.add(new CBR_IncenseBurner());
		globalRelicPool.add(new CBR_Kunai());
		globalRelicPool.add(new CBR_LetterOpener());
		globalRelicPool.add(new CBR_LizardTail());
		globalRelicPool.add(new CBR_Mango());
		globalRelicPool.add(new CBR_MercuryHourglass());
		globalRelicPool.add(new CBR_Orichalcum());
		globalRelicPool.add(new CBR_SelfFormingClay());
		globalRelicPool.add(new CBR_Shuriken());
		globalRelicPool.add(new CBR_SmoothStone());
		globalRelicPool.add(new CBR_Torii());
		globalRelicPool.add(new CBR_TungstenRod());
		globalRelicPool.add(new CBR_Vajra());
		globalRelicPool.add(new CBR_WarPaint());
		globalRelicPool.add(new CBR_Whetstone());
	}

	protected void initializeCurses(){

		this.curseCards.add(new EnInjury());
		this.curseCards.add(new EnDecay());
		this.curseCards.add(new EnDoubt());
		this.curseCards.add(new EnRegret());
		this.curseCards.add(new EnWrithe());
		this.curseCards.add(new EnShame());
	}

	public String ID;
	private ArrayList<AbstractBossCard> allCards;

	private ArrayList<AbstractBossCard> curseCards;

	private ArrayList<AbstractBossCard> synergyCards;

	private AbstractBossCard[] signatureCardPerAct;

	private ArrayList<AbstractBossCard> classGlobalCards;

	private ArrayList<AbstractBossCard> starterCards;

	protected AbstractCharbossRelic starterRelic;


	private String loggerClassName;
	private String loggerArchetypeName;

	private AbstractCharbossRelic[] signatureRelicPerAct;
	private static ArrayList<AbstractCharbossRelic> globalRelicPool;


	private static ArrayList<AbstractCharbossRelic> globalEventPoolAct1;
	private static ArrayList<AbstractCharbossRelic> globalEventPoolAct2;
	private static ArrayList<AbstractCharbossRelic> globalEventPoolAct3;
	private static ArrayList<AbstractCharbossRelic> globalEventPool;


	public ArrayList<AbstractBossCard> cards;

	private ArrayList<String> blacklistedRelics;
	private ArrayList<String> blacklistedCards;

	public AbstractBossDeckArchetype(String id, String loggerClassName, String loggerArchetypeName) {
		this.ID = id;
		this.allCards = new ArrayList<AbstractBossCard>();
		this.starterCards = new ArrayList<AbstractBossCard>();
		this.classGlobalCards = new ArrayList<AbstractBossCard>();
		this.synergyCards = new ArrayList<AbstractBossCard>();
		this.signatureCardPerAct = new AbstractBossCard[3];
		this.signatureRelicPerAct = new AbstractCharbossRelic[3];
		globalRelicPool = new ArrayList<AbstractCharbossRelic>();
		this.loggerClassName = loggerClassName;
		this.loggerArchetypeName = loggerArchetypeName;
		this.curseCards = new ArrayList<AbstractBossCard>();
		this.blacklistedCards = new ArrayList<String>();
		this.blacklistedRelics = new ArrayList<String>();
		globalEventPool = new ArrayList<AbstractCharbossRelic>();
		globalEventPoolAct1 = new ArrayList<AbstractCharbossRelic>();
		globalEventPoolAct2 = new ArrayList<AbstractCharbossRelic>();
		globalEventPoolAct3 = new ArrayList<AbstractCharbossRelic>();
		this.cards = new ArrayList<AbstractBossCard>();


	}

	public void initialize(){
		//Overwritten in each Archetype Base
	}


	
	protected void blacklistCard(String id){
		this.blacklistedCards.add(id);
	}
	protected void blacklistRelic(String id){
		this.blacklistedRelics.add(id);
	}

	public void addRandomCurse(AbstractCharBoss boss, String loggerSource){
		Collections.shuffle(this.curseCards);
		AbstractBossDeckArchetype.logger.info(loggerSource + " added 1 " + this.curseCards.get(0).name + ".");
		boss.masterDeck.addToTop(this.curseCards.get(0).makeCopy());
	}

	protected void addCardToList(AbstractBossCard c, CardBenefitType type){
		switch (type)
		{
			case SIGNATUREACT1: this.signatureCardPerAct[0] = c;  break;
			case SIGNATUREACT2: this.signatureCardPerAct[1] = c;  break;
			case SIGNATUREACT3: this.signatureCardPerAct[2] = c;  break;
			case SUPPORTING: this.synergyCards.add(c);  break;
			case GLOBAL: this.classGlobalCards.add(c);  break;
			default:
		}
	}

	protected void addRelicToList(AbstractCharbossRelic r, CardBenefitType type){
		switch (type)
		{
			case SIGNATUREACT1: signatureRelicPerAct[0] = r;
			case SIGNATUREACT2: signatureRelicPerAct[1] = r;
			case SIGNATUREACT3: signatureRelicPerAct[2] = r;
			default:
		}
	}

	protected void addToStarterDeck(AbstractBossCard c){
		this.starterCards.add(c);
	}

	private ArrayList<AbstractBossCard> sortCardListToRarity(ArrayList<AbstractBossCard> cards){
		ArrayList<AbstractBossCard> sortedCards = new ArrayList<AbstractBossCard>();


		boolean[] validRarities = new boolean[3];

		outer: for (AbstractBossCard c : cards){
			if (c.rarity == CardRarity.COMMON){
				validRarities[0] = true;
				break outer;
			}
		}
		outer: for (AbstractBossCard c : cards){
			if (c.rarity == CardRarity.UNCOMMON){
				validRarities[1] = true;
				break outer;
			}
		}
		outer: for (AbstractBossCard c : cards){
			if (c.rarity == CardRarity.RARE){
				validRarities[2] = true;
				break outer;
			}
		}

		int maxRange = 0;
		int[] rarityRange = new int [6];
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
		if (rarityRoll >= rarityRange[0] && rarityRoll <= rarityRange[1] && validRarities[0]){
			rarity = CardRarity.COMMON;
		} else if (rarityRoll >= rarityRange[2] && rarityRoll <= rarityRange[3] && validRarities[1]){
			rarity = CardRarity.UNCOMMON;
		} else if (rarityRoll >= rarityRange[4] && rarityRoll <= rarityRange[5] && validRarities[2]) {
			rarity = CardRarity.RARE;
		} else {
			//If no rarities were valid, mark it as Special, which will return a single Shiv (TODO replace this with Madness)
			rarity = CardRarity.SPECIAL;
		}

		if (rarity == CardRarity.SPECIAL){
			logger.info("WARNING: No valid cards were available!  Shiv is added.");
			sortedCards.add(new EnShiv());
		} else {
			for (AbstractBossCard c : cards){
				if (c.rarity == rarity){
					sortedCards.add(c);
				}
			}
		}

		return sortedCards;
	}

	private ArrayList<AbstractCharbossRelic> sortRelicListToRarity(ArrayList<AbstractCharbossRelic> relics){
		ArrayList<AbstractCharbossRelic> sortedRelics = new ArrayList<AbstractCharbossRelic>();

		boolean[] validRarities = new boolean[4];

		outer: for (AbstractCharbossRelic r : relics){
			if (r.tier == AbstractRelic.RelicTier.COMMON){
				validRarities[0] = true;
				break outer;
			}
		}
		outer: for (AbstractCharbossRelic r : relics){
			if (r.tier == AbstractRelic.RelicTier.UNCOMMON){
				validRarities[1] = true;
				break outer;
			}
		}
		outer: for (AbstractCharbossRelic r : relics){
			if (r.tier == AbstractRelic.RelicTier.RARE){
				validRarities[2] = true;
				break outer;
			}
		}
		outer: for (AbstractCharbossRelic r : relics){
			if (r.tier == AbstractRelic.RelicTier.SHOP){
				validRarities[3] = true;
				break outer;
			}
		}
		
		int maxRange = 0;
		int[] rarityRange = new int [8];
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
		if (rarityRoll >= rarityRange[0] && rarityRoll <= rarityRange[1] && validRarities[0]){
			rarity = AbstractRelic.RelicTier.COMMON;
		} else if (rarityRoll >= rarityRange[2] && rarityRoll <= rarityRange[3] && validRarities[1]){
			rarity = AbstractRelic.RelicTier.UNCOMMON;
		} else if (rarityRoll >= rarityRange[4] && rarityRoll <= rarityRange[5] && validRarities[2]) {
			rarity = AbstractRelic.RelicTier.RARE;
		} else if (rarityRoll >= rarityRange[6] && rarityRoll <= rarityRange[7] && validRarities[2]) {
			rarity = AbstractRelic.RelicTier.SHOP;
		} else {
			//If no rarities were valid, mark it as Special, which will return a single Circlet
			rarity = AbstractRelic.RelicTier.SPECIAL;
		}

		if (rarity == AbstractRelic.RelicTier.SPECIAL){
			sortedRelics.add(new CBR_Circlet());
			logger.info("WARNING: No valid relics were available!  Circlet is added.");

		} else {
			for (AbstractCharbossRelic r : relics){
				if (r.tier == rarity){
					sortedRelics.add(r);
				}
			}
		}

		return sortedRelics;
	}

	private AbstractBossCard getStrikeOrDefend(){
		for (AbstractBossCard c : this.starterCards){
			if (c.hasTag(AbstractCard.CardTags.STARTER_DEFEND) || c.hasTag(AbstractCard.CardTags.STARTER_STRIKE)){
				return c;
			}
		}
		return null;
	}

	private AbstractBossCard getCardToUpgrade(ArrayList<AbstractBossCard> cards){
		Collections.shuffle(cards);
		for (AbstractBossCard c : cards){
			if (!c.upgraded){
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
		initializeCurses();

		initializeGlobalEventRelics();
		initializeRelics();

		//Remove any Signature relics out of the Global pool if they are present there
		for (int i2 = 0; i2 < 3; i2++) {
			if (this.signatureRelicPerAct[i2] != null){
				AbstractCharbossRelic relicToRemove = null;
				for (AbstractCharbossRelic r: globalRelicPool){
					if (r.relicId.equals(this.signatureRelicPerAct[i2].relicId)) {
						relicToRemove = r;
					}
				}
				if (relicToRemove != null) globalRelicPool.remove(relicToRemove);
			}
		}


		logger.info("Initializing Boss : " + loggerArchetypeName + " " + loggerClassName);
		//Initialize Starter Deck
		if (this.starterCards.size() > 0){
			for (AbstractBossCard c : this.starterCards){
				cards.add(c);
				logger.info("Adding card to Starter Deck: " + c.name);
			}
		}

		//Initialize Starter Relic
		starterRelic.instantObtain(boss);

		//Remove Blacklisted cards from the global pool
		if (this.blacklistedCards.size() > 0){
			for (String s : this.blacklistedCards){
				ArrayList<AbstractBossCard> cardsToRemove = new ArrayList<>();
				for (AbstractBossCard c : classGlobalCards){
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
		if (this.blacklistedRelics.size() > 0){
			removeRelicsFromList(globalRelicPool);
			removeRelicsFromList(globalEventPool);
			removeRelicsFromList(globalEventPoolAct1);
			removeRelicsFromList(globalEventPoolAct2);
			removeRelicsFromList(globalEventPoolAct3);
		}

		logger.info("Boss's potential Synergy Cards:");
		for (AbstractBossCard c : this.synergyCards){
			logger.info(c.name);
		}

		logger.info("Boss's potential Class Global Cards:");
		for (AbstractBossCard c : this.classGlobalCards){
			logger.info(c.name);
		}



		//Simulate acquired/removed/upgraded cards per Act
		for (int actIndex = 0; actIndex < AbstractDungeon.actNum; actIndex++) {
			int safeIndex = Math.min(actIndex, 2); //just in case for the future, where actIndex might go past 2.

			logger.info("Simulating run results from Act " + (actIndex + 1) + " out of " + AbstractDungeon.actNum);

			//Add Signature card, if one is declared for this Act
			logger.info("In Act " + (actIndex + 1) + ", Boss gained these Signature cards:");
			if (this.signatureCardPerAct[actIndex % 3] != null)
			{
				cards.add(this.signatureCardPerAct[actIndex % 3]);
				logger.info(this.signatureCardPerAct[actIndex % 3].name);
			}


			//Add Synergy cards to the deck based on the act and the number of declared simulated Card Acquisitions per act
			logger.info("In Act " + (actIndex + 1) + ", Boss gained these Synergy cards:");
			if (this.synergyCards.size() > 0) {
				if (synergyCardAcquisitionsPerAct[safeIndex] > 0) {
					for (int i2 = 0; i2 < synergyCardAcquisitionsPerAct[safeIndex]; i2++) {
						ArrayList<AbstractBossCard> sortedCards = sortCardListToRarity(this.synergyCards);
						int random = AbstractDungeon.cardRng.random(0, sortedCards.size() - 1);
						AbstractBossCard randomCard = sortedCards.get(random);
						cards.add((AbstractBossCard)randomCard.makeCopy());
						logger.info(randomCard.name);
					}
				}
			}

			//Add Class Global cards to the deck based on the act and the number of declared simulated Card Acquisitions per act
			logger.info("In Act " + (actIndex + 1) + ", Boss gained these Global Class cards:");
			if (this.classGlobalCards.size() > 0) {
				if (globalCardAcquisitionsPerAct[safeIndex] > 0) {
					for (int i2 = 0; i2 < globalCardAcquisitionsPerAct[safeIndex]; i2++) {
						ArrayList<AbstractBossCard> sortedCards = sortCardListToRarity(this.classGlobalCards);
						int random = AbstractDungeon.cardRng.random(0, sortedCards.size() - 1);
						AbstractBossCard randomCard = sortedCards.get(random);
						cards.add((AbstractBossCard)randomCard.makeCopy());
						logger.info(randomCard.name);
					}
				}
			}

			//Add Signature Relic if one is declared
			if (boss == null) logger.info("Boss is returning NULL!");
			if (actIndex < 3)
			{
				logger.info("In Act " + (actIndex + 1) + ", Boss gained these Signature Relics:");
				if (this.signatureRelicPerAct[actIndex] == null) logger.info("Relic is returning NULL!");
				if (this.signatureRelicPerAct[actIndex] != null) {
					this.signatureRelicPerAct[actIndex].instantObtain(boss);
					logger.info(this.signatureRelicPerAct[actIndex].name);
					if (actIndex == 0 && signatureRelicPerAct[actIndex].tier == AbstractRelic.RelicTier.BOSS) logger.info("WARNING!  Act 1 Signature Relic is a Boss Relic, which is not attainable normally.");
				}
			}
			else
			{
				logger.info("The Run is too long, so in act " + (actIndex + 1) + ", Boss obtained another Global Relic:");
				addRandomGlobalRelic(actIndex, boss, cards);
			}

			logger.info("Boss's potential Class Global Relics:");
			for (AbstractCharbossRelic r : globalRelicPool){
				logger.info(r.name);
			}

			//Add Global Relics based on the act and the number of declared simulated relics per act
			logger.info("In Act " + (actIndex + 1) + ", Boss gained these Global Relics:");
			addRandomGlobalRelic(actIndex,boss,cards);


			ArrayList<AbstractCharbossRelic> validEventRelics = new ArrayList<AbstractCharbossRelic>();

			if (globalEventPool.size() > 0) {
				validEventRelics.addAll(globalEventPool);
			}
			switch (actIndex % 3)
			{
				case 0:
					if (globalEventPoolAct1.size() > 0) {
						validEventRelics.addAll(globalEventPoolAct1);
					}
					break;

				case 1:
					logger.info(globalEventPoolAct2.size());
					if (globalEventPoolAct2.size() > 0) {
						validEventRelics.addAll(globalEventPoolAct2);
					}
					break;

				case 2:
					if (globalEventPoolAct3.size() > 0) {
						validEventRelics.addAll(globalEventPoolAct3);
					}
					break;

			}


			logger.info("Boss's potential " + (actIndex + 1) + " Event Relics:");
			for (AbstractCharbossRelic r : validEventRelics){
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
						randomRelic.modifyCardsOnCollect(cards);
						switch (actIndex % 3) {
							case 0:
								globalEventPoolAct1.remove(randomRelic);
							case 1:
								globalEventPoolAct2.remove(randomRelic);
							case 2:
								globalEventPoolAct3.remove(randomRelic);
						}
						globalEventPool.remove(randomRelic);
					}
				}
			}

			//Remove Strikes and Defends based on the act and the number of declared simulated Card Removals per act
			logger.info("In Act " + (actIndex + 1) + ", Boss removed these cards from their deck:");
			if (cardRemovalsPerAct[actIndex] > 0) {
				for (int i2 = 0; i2 < cardRemovalsPerAct[actIndex]; i2++) {
					AbstractBossCard cut = getStrikeOrDefend();
					if (cut != null) {
						logger.info(cut.name);
						cards.remove(cut);
					}
				}
			}

			//Upgrade cards at random based on the Act and the number of declared simulated Card Upgrads per act
			logger.info("In Act " + (actIndex + 1) + ", Boss upgraded these cards:");
			if (cardUpgradesPerAct[actIndex] > 0) {
				for (int i2 = 0; i2 < cardUpgradesPerAct[actIndex]; i2++) {
					AbstractBossCard up = getCardToUpgrade(cards);
					if (up != null) {
						logger.info(up.name);
						up.upgrade();
					}
				}
			}

			logger.info("Finished simulating run results from Act " + (actIndex + 1) + " out of " + AbstractDungeon.actNum);
		}

		logger.info("Boss's final decklist:");
		for (AbstractBossCard c : cards){
			logger.info(c.name);
			boss.masterDeck.addToTop(c);
		}

		logger.info("Boss's final relic list:");
		for (AbstractCharbossRelic r : boss.relics){
			logger.info(r.name);
		}
		
	}

	public void addRandomGlobalRelic(int actIndex, AbstractCharBoss boss, String loggerName, ArrayList<AbstractBossCard> cards){
		if (globalRelicAcquisitionsPerAct[actIndex] > 0) {
			if (globalRelicPool.size() > 0) {
				for (int i2 = 0; i2 < globalRelicAcquisitionsPerAct[actIndex]; i2++) {
					ArrayList<AbstractCharbossRelic> sortedRelics = sortRelicListToRarity(globalRelicPool);
					int random = AbstractDungeon.cardRng.random(0, sortedRelics.size() - 1);
					AbstractCharbossRelic randomRelic = sortedRelics.get(random);
					randomRelic.instantObtain(boss);
					randomRelic.modifyCardsOnCollect(cards);
					globalRelicPool.remove(randomRelic);
					if (loggerName.equals("")) {
						logger.info(randomRelic.name);
					} else {
						logger.info(loggerName + "added " + randomRelic.name + ".");
					}
				}
			}
		}
	}

	public void addRandomGlobalRelic(int actIndex, AbstractCharBoss boss, ArrayList<AbstractBossCard> cards) {
		addRandomGlobalRelic(actIndex,boss,"", cards);

	}

	public void addRandomGlobalRelic(int actIndex, AbstractCharBoss boss, String loggerName) {
		addRandomGlobalRelic(actIndex,boss,loggerName, this.cards);

	}

	public AbstractBossCard getRandomCard() {
		return getRandomCardFromSource(this.allCards);
	}
	private static AbstractBossCard getRandomCardFromSource(ArrayList<AbstractBossCard> source) {
		return (AbstractBossCard)source.get(AbstractDungeon.monsterRng.random(source.size() - 1)).makeCopy();
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
					count ++;
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
					count ++;
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
		GLOBAL;

		CardBenefitType() {
		}
	}
}
