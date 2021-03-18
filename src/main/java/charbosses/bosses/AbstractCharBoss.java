package charbosses.bosses;

import charbosses.BossMechanicDisplayPanel;
import charbosses.actions.common.EnemyDiscardAtEndOfTurnAction;
import charbosses.actions.common.EnemyUseCardAction;
import charbosses.actions.orb.EnemyAnimateOrbAction;
import charbosses.actions.orb.EnemyChannelAction;
import charbosses.actions.orb.EnemyEvokeOrbAction;
import charbosses.actions.orb.EnemyTriggerEndOfTurnOrbActions;
import charbosses.actions.util.CharbossDoNextCardAction;
import charbosses.actions.util.CharbossMakePlayAction;
import charbosses.actions.util.CharbossTurnstartDrawAction;
import charbosses.actions.util.DelayedActionAction;
import charbosses.bosses.Merchant.CharBossMerchant;
import charbosses.cards.AbstractBossCard;
import charbosses.cards.EnemyCardGroup;
import charbosses.cards.red.EnBodySlam;
import charbosses.core.EnemyEnergyManager;
import charbosses.orbs.AbstractEnemyOrb;
import charbosses.orbs.EnemyDark;
import charbosses.orbs.EnemyEmptyOrbSlot;
import charbosses.relics.AbstractCharbossRelic;
import charbosses.relics.CBR_LizardTail;
import charbosses.relics.CBR_Shuriken;
import charbosses.stances.AbstractEnemyStance;
import charbosses.stances.EnNeutralStance;
import charbosses.ui.EnemyEnergyPanel;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.mod.stslib.powers.StunMonsterPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.CardGroup.CardGroupType;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.RunicDome;
import com.megacrit.cardcrawl.relics.SlaversCollar;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.stances.AbstractStance;
import com.megacrit.cardcrawl.ui.buttons.PeekButton;
import com.megacrit.cardcrawl.ui.panels.energyorb.EnergyOrbInterface;
import com.megacrit.cardcrawl.vfx.ThoughtBubble;
import com.megacrit.cardcrawl.vfx.cardManip.CardDisappearEffect;
import com.megacrit.cardcrawl.vfx.combat.BlockedWordEffect;
import com.megacrit.cardcrawl.vfx.combat.DeckPoofEffect;
import com.megacrit.cardcrawl.vfx.combat.HbBlockBrokenEffect;
import com.megacrit.cardcrawl.vfx.combat.StrikeEffect;
import downfall.downfallMod;
import downfall.monsters.NeowBoss;
import slimebound.SlimeboundMod;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

public abstract class AbstractCharBoss extends AbstractMonster {

    public static AbstractCharBoss boss;
    public static boolean finishedSetup;

    public ArrayList<AbstractCharbossRelic> relics;
    public AbstractStance stance;
    public ArrayList<AbstractOrb> orbs;
    public int maxOrbs;
    public int masterMaxOrbs;
    public int masterHandSize;
    public int gameHandSize;

    public int mantraGained = 0;

    public boolean powerhouseTurn;

    public EnemyEnergyManager energy;
    public EnergyOrbInterface energyOrb;
    public EnemyEnergyPanel energyPanel;

    //public CardGroup masterDeck;
    //public CardGroup drawPile;
    public CardGroup hand;
    //public CardGroup discardPile;
    //public CardGroup exhaustPile;
    public CardGroup limbo;
    public AbstractCard cardInUse;

    public int damagedThisCombat;
    public int cardsPlayedThisTurn;
    public int attacksPlayedThisTurn;

    public AbstractPlayer.PlayerClass chosenClass;
    public AbstractBossDeckArchetype chosenArchetype = null;

    public boolean onSetupTurn = true;

    private static boolean debugLog = false;

    private static int attacksDrawnForAttackPhase = 0;
    private static int setupsDrawnForSetupPhase = 0;

    public String energyString = "[E]";

    public AbstractCharBoss(String name, String id, int maxHealth, float hb_x, float hb_y, float hb_w, float hb_h, String imgUrl, float offsetX, float offsetY, PlayerClass playerClass) {
        super(name, id, maxHealth, hb_x, hb_y, hb_w, hb_h, imgUrl, offsetX, offsetY);
        AbstractCharBoss.finishedSetup = false;
        this.drawX = (float) Settings.WIDTH * 0.75F - 150F * Settings.xScale;
        this.type = EnemyType.BOSS;
        this.chosenClass = playerClass;
        this.energyPanel = new EnemyEnergyPanel(this);
        this.hand = new EnemyCardGroup(CardGroupType.HAND, this);
        /*
        this.masterDeck = new EnemyCardGroup(CardGroupType.MASTER_DECK, this);
        this.drawPile = new EnemyCardGroup(CardGroupType.DRAW_PILE, this);
        this.discardPile = new EnemyCardGroup(CardGroupType.DISCARD_PILE, this);
        this.exhaustPile = new EnemyCardGroup(CardGroupType.EXHAUST_PILE, this);
        */
        this.limbo = new EnemyCardGroup(CardGroupType.UNSPECIFIED, this);
        this.masterHandSize = 3;
        this.gameHandSize = 3;
        this.masterMaxOrbs = this.maxOrbs = 0;
        this.stance = new EnNeutralStance();
        this.orbs = new ArrayList<AbstractOrb>();
        this.relics = new ArrayList<AbstractCharbossRelic>();
    }

    @Override
    public void init() {
        AbstractCharBoss.boss = this;
        this.setHp(this.maxHealth);
        this.energy.energyMaster = 2;
        this.generateAll();
        super.init();
        this.preBattlePrep();
        AbstractCharBoss.finishedSetup = true;
    }

    @Override
    public void getMove(int num) {
        this.setMove((byte) 0, Intent.NONE);
    }


    public void generateDeck() {
    }


    public void generateAll() {
        this.generateDeck();
        /*
        for (AbstractCard c : this.masterDeck.group) {
            ((AbstractBossCard) c).owner = this;
        }

        if (AbstractDungeon.ascensionLevel < 19) {
            masterDeck.shuffle();
        }

        ArrayList<AbstractCard> isInnateCard = new ArrayList<AbstractCard>();
        for (AbstractCard c : this.masterDeck.group) {
            if (c.isInnate) {
                isInnateCard.add(c);
            }
        }

        if (isInnateCard.size() > 0) {
            this.masterDeck.group.removeAll(isInnateCard);

            for (AbstractCard c : isInnateCard) {
                this.masterDeck.addToBottom(c);
            }
        }
        */


        if (AbstractDungeon.ascensionLevel >= 20 && CardCrawlGame.dungeon instanceof com.megacrit.cardcrawl.dungeons.TheBeyond) {
            new CBR_LizardTail().instantObtain(this);
            // new CBR_MagicFlower().instantObtain(this);
        }
        /*
        if (NeowBoss.neowboss != null) {
            switch (chosenArchetype.actNum) {
                case 1: {
                    chosenArchetype.maxHPModifier += 200;
                    break;

                }
                case 2: {
                    chosenArchetype.maxHPModifier += 100;
                    break;

                }
                case 3: {
                    chosenArchetype.maxHPModifier += 0;
                    break;
                }

            }
        }
        */
        maxHealth += chosenArchetype.maxHPModifier;
        if (AbstractDungeon.ascensionLevel >= 9) {
            maxHealth = Math.round(maxHealth * 1.2F);
        }
        currentHealth = maxHealth;
        updateHealthBar();
    }

    public void usePreBattleAction() {
        this.energy.recharge();
        for (AbstractCharbossRelic r : this.relics) {
            r.atBattleStartPreDraw();
        }
        addToBot(new DelayedActionAction(new CharbossTurnstartDrawAction()));
        for (AbstractCharbossRelic r : this.relics) {
            r.atBattleStart();
        }


        if (hasPower(MinionPower.POWER_ID)) {
            if (NeowBoss.neowboss.minion != this) {
                playMusic();
            }
        } else {
            playMusic();
        }

        chosenArchetype.addedPreBattle();
    }

    public void playMusic() {
        CardCrawlGame.music.unsilenceBGM();
        AbstractDungeon.scene.fadeOutAmbiance();

        String musicKey;
        if (AbstractDungeon.actNum == 0) musicKey = "BOSS_BOTTOM";
        else if (AbstractDungeon.actNum == 1) musicKey = "BOSS_CITY";
        else if (AbstractDungeon.actNum == 2) musicKey = "BOSS_BEYOND";
        else musicKey = "MINDBLOOM";

        AbstractDungeon.getCurrRoom().playBgmInstantly(musicKey);
    }

    @Override
    public void takeTurn() {
        attacksDrawnForAttackPhase = 0;
        setupsDrawnForSetupPhase = 0;
        this.startTurn();
        // addToBot(new CharbossSortHandAction());
        addToBot(new CharbossMakePlayAction());
//        this.makePlay();
        this.onSetupTurn = !this.onSetupTurn;
    }

    public void makePlay() {
        for (AbstractCard _c : this.hand.group) {
            AbstractBossCard c = (AbstractBossCard) _c;
            if (c.canUse(AbstractDungeon.player, this) && c.getPriority(this.hand.group) > -100) {
                //SlimeboundMod.logger.info("Enemy using card: " + c.name + " energy = " + EnemyEnergyPanel.totalCount);
                this.useCard(c, this, EnemyEnergyPanel.totalCount);
                this.addToBot(new DelayedActionAction(new CharbossDoNextCardAction()));
                return;
            }

        }

        //doing this here instead of end turn allows it to still be before player loses Block
        addToBot(new EnemyTriggerEndOfTurnOrbActions());
    }

    @Override
    public void update() {
        super.update();
        for (AbstractRelic r : this.relics) {
            r.update();
        }

        for (AbstractOrb o : this.orbs) {
            o.update();
            o.updateAnimation();
        }

        this.combatUpdate();
    }

    @Override
    public void applyEndOfTurnTriggers() {
        if (hasPower(StunMonsterPower.POWER_ID)) chosenArchetype.turn--;

        this.energy.recharge();

        for (final AbstractPower p : AbstractCharBoss.boss.powers) {
            p.onEnergyRecharge();
        }

        for (final AbstractCard c : this.hand.group) {
            c.triggerOnEndOfTurnForPlayingCard();
        }
        this.stance.onEndOfTurn();

        addToBot(new EnemyDiscardAtEndOfTurnAction());
        /*
        for (final AbstractCard c : this.drawPile.group) {
            c.resetAttributes();
        }
        for (final AbstractCard c : this.discardPile.group) {
            c.resetAttributes();
        }
        */
        for (final AbstractCard c : this.hand.group) {
            c.resetAttributes();
        }
        addToBot(new DelayedActionAction(new CharbossTurnstartDrawAction()));

    }


    public void startTurn() {
        ////SlimeboundMod.logger.info("Start Turn Triggered");
        this.cardsPlayedThisTurn = 0;
        this.attacksPlayedThisTurn = 0;
        for (AbstractCard c : hand.group) {
            ((AbstractBossCard) c).lockIntentValues = true;
        }
        this.applyStartOfTurnRelics();
        this.applyStartOfTurnPreDrawCards();
        this.applyStartOfTurnCards();
        //this.applyStartOfTurnPowers();
        this.applyStartOfTurnOrbs();


    }

    public ArrayList<AbstractCard> getThisTurnCards() {
        return chosenArchetype.getThisTurnCards();
    }

    class sortByNewPrio implements Comparator<AbstractBossCard> {
        // Used for sorting in ascending order of
        // roll number
        public int compare(AbstractBossCard a, AbstractBossCard b) {
            return a.newPrio - b.newPrio;
        }
    }


    public void endTurnStartTurn() {
        if (!AbstractDungeon.getCurrRoom().isBattleOver) {
            //addToBot(new EnemyDrawCardAction(this, this.gameHandSize, true));
            addToBot(new AbstractGameAction() {
                @Override
                public void update() {
                    isDone = true;
                    for (AbstractCard q : getThisTurnCards()) {
                        AbstractCharBoss.boss.hand.addToTop(q);
                        if (q instanceof AbstractBossCard) ((AbstractBossCard) q).bossDarken();
                        q.current_y = Settings.HEIGHT / 2F;
                        q.current_x = Settings.WIDTH;
                    }

                    ArrayList<AbstractBossCard> handAsBoss = new ArrayList<>();
                    for (AbstractCard c : AbstractCharBoss.boss.hand.group) {
                        handAsBoss.add((AbstractBossCard) c);
                    }

                    Collections.sort(handAsBoss, new sortByNewPrio());

                    ArrayList<AbstractCard> newHand = new ArrayList<>();
                    for (AbstractCard c : handAsBoss) {
                        newHand.add(c);
                        c.applyPowers();
                    }

                    AbstractCharBoss.boss.hand.group = newHand;

                    AbstractCharBoss.boss.hand.refreshHandLayout();
                }
            });
            addToBot(new WaitAction(0.2f));
            this.applyStartOfTurnPostDrawRelics();
            this.applyStartOfTurnPostDrawPowers();
            if (!AbstractDungeon.player.hasRelic(RunicDome.ID)) {
                //addToBot(new CharbossSortHandAction());
                addToBot(new AbstractGameAction() {
                    @Override
                    public void update() {
                        isDone = true;
                        int budget = energyPanel.getCurrentEnergy();
                        for (AbstractCard c : AbstractCharBoss.boss.hand.group) {
                            if (c.costForTurn <= budget && c.costForTurn != -2 && c instanceof AbstractBossCard) {
                                ((AbstractBossCard) c).createIntent();
                                ((AbstractBossCard) c).bossLighten();
                                budget -= c.costForTurn;
                                budget += ((AbstractBossCard) c).energyGeneratedIfPlayed;
                                if (budget < 0) budget = 0;
                            }
                            else if (c.costForTurn == -2 && c.type == AbstractCard.CardType.CURSE && c.color == AbstractCard.CardColor.CURSE) {
                                ((AbstractBossCard) c).bossLighten();
                            }
                        }
                        for (AbstractCard c : AbstractCharBoss.boss.hand.group) {
                            AbstractBossCard cB = (AbstractBossCard) c;
                            cB.refreshIntentHbLocation();
                        }
                    }
                });
            }

            this.cardsPlayedThisTurn = 0;
            this.attacksPlayedThisTurn = 0;
        }
    }


    public void preApplyIntentCalculations() {
        boolean hasShuriken = hasRelic(CBR_Shuriken.ID);
        int attackCount = 0;
        int artifactCount = 0;

        if (AbstractDungeon.player.hasPower(ArtifactPower.POWER_ID)) {
            artifactCount = AbstractDungeon.player.getPower(ArtifactPower.POWER_ID).amount;
        }

        //Reset all custom modifiers back to 0
        for (AbstractCard c : hand.group) {
            ((AbstractBossCard) c).manualCustomDamageModifier = 0;
            ((AbstractBossCard) c).manualCustomDamageModifierMult = 1;
        }

        for (int i = 0; i < hand.size(); i++) {
            AbstractBossCard c = (AbstractBossCard) hand.group.get(i);

            SlimeboundMod.logger.info("intent calcs: " + c.name);
            if (!c.lockIntentValues) {
                SlimeboundMod.logger.info(c.name + " not locked, calculating.");

                //Artifact Checks - calculates if any Artifact will be left
                if (c.artifactConsumedIfPlayed > 0) {
                    artifactCount -= c.artifactConsumedIfPlayed;
                }

                //Vulnerable Check - knows to check if any Artifact will be left
                if (c.vulnGeneratedIfPlayed > 0) {
                    if (artifactCount <= 0) {
                        for (int j = i + 1; j < hand.size(); j++) {
                            AbstractBossCard c2 = (AbstractBossCard) hand.group.get(j);
                            c2.manualCustomVulnModifier = true;
                        }
                    }
                }

                //Shuriken Checks for Act 1 Silent
                if (hasShuriken) {
                    SlimeboundMod.logger.info("has shuriken");
                    if (c.type == AbstractCard.CardType.ATTACK) {
                        SlimeboundMod.logger.info(c.name + " is an attack.");
                        attackCount++;
                        if (attackCount == 3) {
                            SlimeboundMod.logger.info(c.name + " is 3rd attack.");
                            for (int j = i + 1; j < hand.size(); j++) {
                                AbstractBossCard c2 = (AbstractBossCard) hand.group.get(j);
                                SlimeboundMod.logger.info(c2.name + " is gaining damage.");
                                c2.manualCustomDamageModifier += 1;
                            }
                            attackCount = 0;
                        }
                    }
                }

                //Strength check for Wish in Act 2 Watcher, Act 2 Defect's Reprogram
                if (c.strengthGeneratedIfPlayed > 0) {
                    for (int j = i + 1; j < hand.size(); j++) {
                        AbstractBossCard c2 = (AbstractBossCard) hand.group.get(j);
                        c2.manualCustomDamageModifier += c.strengthGeneratedIfPlayed;
                    }
                }

                //Block checks for Act 3 Ironclad's Body Slams
                if (c.block > 0) {
                    for (int j = i + 1; j < hand.size(); j++) {
                        AbstractBossCard c2 = (AbstractBossCard) hand.group.get(j);
                        if (c2 instanceof EnBodySlam) {
                            c2.manualCustomDamageModifier += c.block;
                        }
                    }
                }

                //Minion block checks for Act 3 Ironclad's Body Slams
                if (c instanceof EnBodySlam) {
                    if (hasPower(BarricadePower.POWER_ID)) {
                        c.manualCustomDamageModifier += 10;
                    }
                }

                //Divinity Check for Act 2 Watcher
                if (c.damageMultGeneratedIfPlayed > 1) {
                    for (int j = i + 1; j < hand.size(); j++) {
                        AbstractBossCard c2 = (AbstractBossCard) hand.group.get(j);
                        c2.manualCustomDamageModifierMult = c.damageMultGeneratedIfPlayed;
                    }
                }

                //TODO - Sadistic Nature for Act 3 Silent

            }
        }
        for (AbstractCard c : hand.group) {
            if (!((AbstractBossCard) c).bossDarkened) {
                ((AbstractBossCard) c).createIntent();
            }
        }

    }

    public void applyPowers() {
        super.applyPowers();

        preApplyIntentCalculations();

        this.hand.applyPowers();
        /*
        this.drawPile.genPreview();
        this.discardPile.genPreview();
        */
    }

    /*
    public void sortHand() {
        ArrayList<AbstractBossCard> cardsByValue = new ArrayList<AbstractBossCard>();
        ArrayList<AbstractBossCard> affordableCards = new ArrayList<AbstractBossCard>();
        ArrayList<AbstractBossCard> unaffordableCards = new ArrayList<AbstractBossCard>();
        ArrayList<AbstractCard> sortedCards = new ArrayList<AbstractCard>();
        for (AbstractCard _c : this.hand.group) {
            AbstractBossCard c = (AbstractBossCard) _c;
            if (cardsByValue.size() < 1) {
                cardsByValue.add(c);
            } else {
                boolean gotem = false;
                for (int i = 0; i < cardsByValue.size(); i++) {
                    int maxRange = 0;
                    if (boss.hasRelic(RunicDome.ID)) maxRange += 4;
                    if (cardsByValue.get(i).getPriority(this.hand.group) < c.getPriority(this.hand.group) + AbstractDungeon.aiRng.random(0, maxRange)) {
                        cardsByValue.add(i, c);
                        gotem = true;
                        break;
                    }
                }
                if (!gotem) {
                    cardsByValue.add(c);
                }
            }
        }
        int budget = energyPanel.getCurrentEnergy();
        ////SlimeboundMod.logger.info("Hand budget being calculated for the turn." + budget);
        for (int i = 0; i < cardsByValue.size(); i++) {
            AbstractBossCard c = cardsByValue.get(i);
            if (c.costForTurn <= budget && c.costForTurn != -2) {
                budget -= c.costForTurn;
                affordableCards.add(c);
            } else {
                unaffordableCards.add(c);
            }
        }
        for (int i = 0; i < affordableCards.size(); i++) {
            AbstractBossCard c = affordableCards.get(i);
            if (sortedCards.size() < 1) {
                sortedCards.add(c);
            } else {
                boolean gotem = false;
                for (int j = 0; j < sortedCards.size(); j++) {
                    if (((AbstractBossCard) sortedCards.get(j)).getPriority(this.hand.group) < c.getPriority(this.hand.group)) {
                        sortedCards.add(j, c);
                        gotem = true;
                        break;
                    }
                }
                if (!gotem) {
                    sortedCards.add(c);
                }
            }
        }
        for (AbstractBossCard c : unaffordableCards) {
            sortedCards.add(c);
        }

        budget = energyPanel.getCurrentEnergy();
        ////SlimeboundMod.logger.info("Hand budget being calculated for the turn." + budget);
        for (int i = 0; i < sortedCards.size(); i++) {
            AbstractBossCard c = (AbstractBossCard) sortedCards.get(i);
            if (c.costForTurn <= budget && c.costForTurn != -2 && c.getPriority(this.hand.group) > -100) {
                c.createIntent();
                c.bossLighten();
                budget -= c.costForTurn;
                budget += c.energyGeneratedIfPlayed;
                if (budget < 0) budget = 0;
            }

                /*
                if (c.type == AbstractCard.CardType.CURSE && boss.hasRelic("Blue Candle")) {
                    c.createIntent();
                } else if (c.type == AbstractCard.CardType.STATUS && boss.hasRelic("Medical Kit")) {
                    c.createIntent();
                } else {

        }

        this.hand.group = sortedCards;

        this.hand.refreshHandLayout();

        for (AbstractCard c : this.hand.group) {
            AbstractBossCard cB = (AbstractBossCard) c;
            cB.refreshIntentHbLocation();
        }
    }
    */

    public int getIntentDmg() {
        int totalIntentDmg = -1;
        for (AbstractCard c : this.hand.group) {
            AbstractBossCard cB = (AbstractBossCard) c;
            if (cB.intentDmg > 0 && (!cB.bossDarkened || AbstractDungeon.player.hasRelic(RunicDome.ID))) {
                if (totalIntentDmg == -1) {
                    totalIntentDmg = 0;
                }
                totalIntentDmg += cB.intentDmg;
            }
        }
        return totalIntentDmg;
    }

    public int getIntentBaseDmg() {
        return getIntentDmg();
    }

    /////////////////////////////////////////////////////////////////////////////
    ////////////[[[[[[[[PLAYER-MIMICING FUNCTIONS]]]]]]]]////////////////////////
    /////////////////////////////////////////////////////////////////////////////
    public boolean hasRelic(final String targetID) {
        for (final AbstractRelic r : this.relics) {
            if (r.relicId.equals(targetID)) {
                return true;
            }
        }
        return false;
    }

    public AbstractRelic getRelic(final String targetID) {
        for (final AbstractRelic r : this.relics) {
            if (r.relicId.equals(targetID)) {
                return r;
            }
        }
        return null;
    }

    public void gainEnergy(final int e) {
        EnemyEnergyPanel.addEnergy(e);
        this.hand.glowCheck();

    }

    public void loseEnergy(final int e) {
        EnemyEnergyPanel.useEnergy(e);
    }

    /*
    public void draw() {
        if (this.hand.size() == 10) {
            //this.createHandIsFullDialog();
            return;
        }
        CardCrawlGame.sound.playAV("CARD_DRAW_8", -0.12f, 0.25f);
        this.draw(1);
        this.onCardDrawOrDiscard();
    }
    */

    private AbstractCard findReplacementCardInDraw(ArrayList<AbstractCard> drawPile, boolean attack, boolean setup) {
        for (AbstractCard c : drawPile) {
            //Skip the top card.
            if (drawPile.get(0) != c) {
                if (attack && c.hasTag(downfallMod.CHARBOSS_ATTACK)) {
                    //if (debugLog) //SlimeboundMod.logger.info("Attack replacement was requested. Returning: " + c.name);
                    return c;
                }
                if (setup && c.hasTag(downfallMod.CHARBOSS_SETUP)) {
                    //if (debugLog) //SlimeboundMod.logger.info("Setup replacement was requested. Returning: " + c.name);
                    return c;
                }
                if (!setup && !attack && !c.hasTag(downfallMod.CHARBOSS_SETUP) && !c.hasTag(downfallMod.CHARBOSS_ATTACK)) {
                    //if (debugLog)
                    //SlimeboundMod.logger.info("Either-phase replacement was requested. Returning: " + c.name);
                    return c;
                }
            }
        }
        //if (debugLog) //SlimeboundMod.logger.info("Replacement was requested, but no card was valid. Returning null");

        return null;
    }

    private AbstractCard performCardSearch(ArrayList<AbstractCard> drawPile, DrawTypes firstPriority, DrawTypes secondPriority, DrawTypes thirdPriority) {
        AbstractCard replacementCard = null;

        if (firstPriority == secondPriority || firstPriority == thirdPriority || secondPriority == thirdPriority) {
            //SlimeboundMod.logger.info("ERROR! performCardSearch has two parameters as the same priority! These all need to be different! First priority:" + firstPriority + "Second priority: " + secondPriority + "Third priority: " + thirdPriority);

            return null;
        }

        ////SlimeboundMod.logger.info("Replacement search requested.  First priority: " + firstPriority);

        if (firstPriority == DrawTypes.Setup) {
            replacementCard = findReplacementCardInDraw(drawPile, false, true);
        } else if (firstPriority == DrawTypes.Attack) {
            replacementCard = findReplacementCardInDraw(drawPile, true, false);
        } else {
            replacementCard = findReplacementCardInDraw(drawPile, false, false);
        }

        if (replacementCard != null) {
            //SlimeboundMod.logger.info("First priority search successful. Returning " + replacementCard.name);
            return replacementCard;
        }

        //SlimeboundMod.logger.info("First priority failed.  Second priority: " + secondPriority);

        if (secondPriority == DrawTypes.Setup) {
            replacementCard = findReplacementCardInDraw(drawPile, false, true);
        } else if (secondPriority == DrawTypes.Attack) {
            replacementCard = findReplacementCardInDraw(drawPile, true, false);
        } else {
            replacementCard = findReplacementCardInDraw(drawPile, false, false);
        }

        if (replacementCard != null) {
            //SlimeboundMod.logger.info("Second priority search successful. Returning " + replacementCard.name);
            return replacementCard;
        }

        //SlimeboundMod.logger.info("Second priority failed.  Third priority: " + thirdPriority);

        if (thirdPriority == DrawTypes.Setup) {
            replacementCard = findReplacementCardInDraw(drawPile, false, true);
        } else if (thirdPriority == DrawTypes.Attack) {
            replacementCard = findReplacementCardInDraw(drawPile, true, false);
        } else {
            replacementCard = findReplacementCardInDraw(drawPile, false, false);
        }

        if (replacementCard != null) {
            //SlimeboundMod.logger.info("Third priority search successful. Returning " + replacementCard.name);
            return replacementCard;
        }

        //SlimeboundMod.logger.info("Priority search yielded no result. Returning null.");
        return null;
    }


    private AbstractCard chooseCardToDraw(ArrayList<AbstractCard> drawPile) {

        AbstractBossCard c = (AbstractBossCard) drawPile.get(0);
        AbstractCard replacementCard = null;

        if (drawPile.size() > 1) {

            //Force Draw is for mechanics like Headbutt, where a card should be drawn regardless of phase
            if (c.forceDraw) {
                //if (debugLog) //SlimeboundMod.logger.info("Force Drawing " + c.name);
                return c;
            }

            if (this.onSetupTurn) {
                if (setupsDrawnForSetupPhase < 1) {
                    //if (debugLog) //SlimeboundMod.logger.info("Attempting to draw a Setup card");
                    if (c.hasTag(downfallMod.CHARBOSS_SETUP)) {
                        //if (debugLog) //SlimeboundMod.logger.info("Top card is good. Drawing Setup Card " + c.name);
                        setupsDrawnForSetupPhase++;
                        return c;
                    } else {
                        //if (debugLog)
                        //SlimeboundMod.logger.info("Top card is not a Setup. Finding Setup->Either->Attack replacement.");
                        replacementCard = performCardSearch(drawPile, DrawTypes.Setup, DrawTypes.EitherPhase, DrawTypes.Attack);
                        if (replacementCard != null) {
                            //if (debugLog) //SlimeboundMod.logger.info("Drawing replacement: " + replacementCard.name);
                            return replacementCard;
                        } else {
                            //if (debugLog) //SlimeboundMod.logger.info("Null was returned by replacement search.");
                        }
                    }
                } else {
                    //if (debugLog)
                    //SlimeboundMod.logger.info("Setup card already drawn. Finding Either->Setup->Attack replacement.");
                    replacementCard = performCardSearch(drawPile, DrawTypes.EitherPhase, DrawTypes.Setup, DrawTypes.Attack);
                    if (replacementCard != null) {
                        //if (debugLog) //SlimeboundMod.logger.info("Drawing replacement: " + replacementCard.name);
                        return replacementCard;
                    } else {
                        //if (debugLog) //SlimeboundMod.logger.info("Null was returned by replacement search.");
                    }
                }
            }

            if (!this.onSetupTurn) {
                if (attacksDrawnForAttackPhase < 1) {
                    //if (debugLog) //SlimeboundMod.logger.info("Attempting to draw a Attack card");
                    if (c.hasTag(downfallMod.CHARBOSS_ATTACK)) {
                        //if (debugLog) //SlimeboundMod.logger.info("Top card is good. Drawing Attack Card " + c.name);
                        attacksDrawnForAttackPhase++;
                        return c;
                    } else {
                        //if (debugLog)
                        //SlimeboundMod.logger.info("Top card is not a Attack. Finding Attack->Either->Setup replacement.");
                        replacementCard = performCardSearch(drawPile, DrawTypes.Attack, DrawTypes.EitherPhase, DrawTypes.Setup);
                        if (replacementCard != null) {
                            //if (debugLog) //SlimeboundMod.logger.info("Drawing replacement: " + replacementCard.name);
                            return replacementCard;
                        } else {
                            //if (debugLog) //SlimeboundMod.logger.info("Null was returned by replacement search.");
                        }
                    }
                } else {
                    //if (debugLog)
                    //SlimeboundMod.logger.info("2 Attack cards already drawn. Finding Either->Attack->Setup replacement.");
                    replacementCard = performCardSearch(drawPile, DrawTypes.EitherPhase, DrawTypes.Attack, DrawTypes.Setup);
                    if (replacementCard != null) {
                        //if (debugLog) //SlimeboundMod.logger.info("Drawing replacement: " + replacementCard.name);
                        return replacementCard;
                    } else {
                        //if (debugLog) //SlimeboundMod.logger.info("Null was returned by replacement search.");
                    }
                }
            }


            //if (debugLog)
            //SlimeboundMod.logger.info("WARNING: All draw logic has failed.  Drawing top card as a default: " + c.name);
            return c;
        } else {
            //SlimeboundMod.logger.info("Drawing the last card in the deck, no logic used: " + c.name);
            return c;
        }

    }

    /*
    public void draw(final int numCards) {
        for (int i = 0; i < numCards; ++i) {
            if (!this.drawPile.isEmpty()) {

                //final AbstractCard c = chooseCardToDraw(this.drawPile.group);
                final AbstractCard c = this.drawPile.getTopCard();
                AbstractBossCard cB = (AbstractBossCard) c;
                cB.bossDarken();
                cB.destroyIntent();

                c.current_x = DRAW_PILE_X;
                c.current_y = DRAW_PILE_Y;
                c.setAngle(0.0f, true);
                c.lighten(false);

                c.drawScale = 0.12f;
                c.targetDrawScale = AbstractBossCard.HAND_SCALE;
                c.triggerWhenDrawn();
                this.hand.addToHand(c);
                this.drawPile.removeCard(c);
                for (final AbstractPower p : this.powers) {
                    p.onCardDraw(c);
                }
                for (final AbstractRelic r : this.relics) {
                    r.onCardDraw(c);
                }
            }
        }
    }
    */

    public void onCardDrawOrDiscard() {
        for (final AbstractPower p : this.powers) {
            p.onDrawOrDiscard();
        }
        for (final AbstractRelic r : this.relics) {
            r.onDrawOrDiscard();
        }
        if (this.hasPower("Corruption")) {
            for (final AbstractCard c : this.hand.group) {
                if (c.type == AbstractCard.CardType.SKILL && c.costForTurn != 0) {
                    c.modifyCostForCombat(-9);
                }
            }
        }
        this.hand.applyPowers();
        this.hand.glowCheck();
    }

    public void useCard(final AbstractCard c, AbstractMonster monster, final int energyOnUse) {
        if (monster == null) {
            monster = this;
        }
        if (c.type == AbstractCard.CardType.ATTACK) {
            this.attacksPlayedThisTurn++;
            this.useFastAttackAnimation();

            if (c.damage > MathUtils.random(20)) {
                this.onPlayAttackCardSound();
            }
        }
        this.cardsPlayedThisTurn++;
        c.calculateCardDamage(monster);
        if (c.cost == -1 && EnemyEnergyPanel.totalCount < energyOnUse && !c.ignoreEnergyOnUse) {
            c.energyOnUse = EnemyEnergyPanel.totalCount;
        }
        if (c.cost == -1 && c.isInAutoplay) {
            c.freeToPlayOnce = true;
        }
        c.use(AbstractDungeon.player, monster);
        addToBot(new EnemyUseCardAction(c, monster));
        if (!c.dontTriggerOnUseCard) {
            this.hand.triggerOnOtherCardPlayed(c);
        }
        this.hand.removeCard(c);
        this.cardInUse = c;
        c.target_x = (float) (Settings.WIDTH / 2);
        c.target_y = (float) (Settings.HEIGHT / 2);
        if (c.costForTurn > 0 && !c.freeToPlay() && !c.isInAutoplay && (!this.hasPower("Corruption") || c.type != AbstractCard.CardType.SKILL)) {
            this.energy.use(c.costForTurn);
        }

        AbstractBossCard cB = (AbstractBossCard) c;
        cB.showIntent = false;
    }

    public void combatUpdate() {
        if (this.cardInUse != null) {
            this.cardInUse.update();
        }
        this.energyPanel.update();
        this.limbo.update();
        /*
        this.exhaustPile.update();
        this.drawPile.update();
        this.discardPile.update();
        */
        this.hand.update();
        this.hand.updateHoverLogic();
        for (final AbstractPower p : this.powers) {
            p.updateParticles();
        }
        for (final AbstractOrb o : this.orbs) {
            o.update();
        }
        this.stance.update();
    }

    public void onPlayAttackCardSound() {
    }


    @Override
    public void damage(final DamageInfo info) {
        int damageAmount = info.output;
        boolean hadBlock = true;
        if (this.currentBlock == 0) {
            hadBlock = false;
        }
        if (damageAmount < 0) {
            damageAmount = 0;
        }
        if (damageAmount > 1 && this.hasPower(IntangiblePower.POWER_ID)) {
            damageAmount = 1;
        }
        final boolean weakenedToZero = damageAmount == 0;
        damageAmount = this.decrementBlock(info, damageAmount);
        ////SlimeboundMod.logger.info(info.owner + " pre damage about to apply relics");
        if (info.owner == this) {
            for (final AbstractRelic r : this.relics) {
                ////SlimeboundMod.logger.info(r.name + " onAttackToChange firing");
                damageAmount = r.onAttackToChangeDamage(info, damageAmount);


            }
        }
        if (info.owner == AbstractDungeon.player) {
            for (final AbstractRelic r : AbstractDungeon.player.relics) {
                damageAmount = r.onAttackToChangeDamage(info, damageAmount);
            }
        }
        if (info.owner != null) {
            for (final AbstractPower p : info.owner.powers) {
                damageAmount = p.onAttackToChangeDamage(info, damageAmount);
            }
        }
        for (final AbstractRelic r : this.relics) {
            damageAmount = r.onAttackedToChangeDamage(info, damageAmount);
        }
        for (final AbstractPower p : this.powers) {
            damageAmount = p.onAttackedToChangeDamage(info, damageAmount);
        }
        if (info.owner == this) {
            for (final AbstractRelic r : this.relics) {
                r.onAttack(info, damageAmount, this);
            }
        }
        if (info.owner == AbstractDungeon.player) {
            for (final AbstractRelic r : AbstractDungeon.player.relics) {
                r.onAttack(info, damageAmount, this);
            }
        }
        if (info.owner != null) {
            for (final AbstractPower p : info.owner.powers) {
                p.onAttack(info, damageAmount, this);
            }
            for (final AbstractPower p : this.powers) {
                damageAmount = p.onAttacked(info, damageAmount);
            }
            for (final AbstractRelic r : this.relics) {
                damageAmount = r.onAttacked(info, damageAmount);
            }
        }
        for (final AbstractRelic r : this.relics) {
            damageAmount = r.onLoseHpLast(damageAmount);
        }
        this.lastDamageTaken = Math.min(damageAmount, this.currentHealth);
        final boolean probablyInstantKill = this.currentHealth == 0;
        if (damageAmount > 0 || probablyInstantKill) {
            for (final AbstractPower p : this.powers) {
                damageAmount = p.onLoseHp(damageAmount);
            }
            for (final AbstractPower p : this.powers) {
                p.wasHPLost(info, damageAmount);
            }
            for (final AbstractRelic r : this.relics) {
                r.wasHPLost(damageAmount);
            }
            if (info.owner != null) {
                for (final AbstractPower p : info.owner.powers) {
                    p.onInflictDamage(info, damageAmount, this);
                }
            }
            if (info.owner != this) {
                this.useStaggerAnimation();
            }
            if (damageAmount > 0) {
                for (final AbstractRelic r : this.relics) {
                    r.onLoseHp(damageAmount);
                }
                if (info.owner != this) {
                    this.useStaggerAnimation();
                }
                if (damageAmount >= 99 && !CardCrawlGame.overkill) {
                    CardCrawlGame.overkill = true;
                }
                this.currentHealth -= damageAmount;
                if (!probablyInstantKill) {
                    AbstractDungeon.effectList.add(new StrikeEffect(this, this.hb.cX, this.hb.cY, damageAmount));
                }
                if (this.currentHealth < 0) {
                    this.currentHealth = 0;
                }
                this.healthBarUpdatedEvent();
                this.updateCardsOnDamage();
            }
            if (this.currentHealth <= this.maxHealth / 2.0f && !this.isBloodied) {
                this.isBloodied = true;
                for (final AbstractRelic r : this.relics) {
                    if (r != null) {
                        r.onBloodied();
                    }
                }
            }
            if (this.currentHealth < 1) {
                if (!this.hasRelic("Mark of the Bloom")) {
                    if (this.hasRelic(CBR_LizardTail.ID) && ((CBR_LizardTail) this.getRelic(CBR_LizardTail.ID)).counter == -1) {
                        this.currentHealth = 0;
                        this.getRelic(CBR_LizardTail.ID).onTrigger();
                        return;
                    }
                }
                this.die();
                if (AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
                    AbstractDungeon.actionManager.cleanCardQueue();
                    AbstractDungeon.effectList.add(new DeckPoofEffect(64.0f * Settings.scale, 64.0f * Settings.scale, true));
                    AbstractDungeon.effectList.add(new DeckPoofEffect(Settings.WIDTH - 64.0f * Settings.scale, 64.0f * Settings.scale, false));
                    AbstractDungeon.overlayMenu.hideCombatPanels();
                }
                if (this.currentBlock > 0) {
                    this.loseBlock();
                    AbstractDungeon.effectList.add(new HbBlockBrokenEffect(this.hb.cX - this.hb.width / 2.0f + AbstractMonster.BLOCK_ICON_X, this.hb.cY - this.hb.height / 2.0f + AbstractMonster.BLOCK_ICON_Y));
                }

                for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
                    if ((!m.isDead) && (!m.isDying) && m.hasPower(MinionPower.POWER_ID)) {
                        AbstractDungeon.actionManager.addToTop(new com.megacrit.cardcrawl.actions.utility.HideHealthBarAction(m));
                        AbstractDungeon.actionManager.addToTop(new com.megacrit.cardcrawl.actions.common.SuicideAction(m));
                    }
                }
            }
        } else if (!probablyInstantKill) {
            if (weakenedToZero && this.currentBlock == 0) {
                if (hadBlock) {
                    AbstractDungeon.effectList.add(new BlockedWordEffect(this, this.hb.cX, this.hb.cY, AbstractMonster.TEXT[30]));
                } else {
                    AbstractDungeon.effectList.add(new StrikeEffect(this, this.hb.cX, this.hb.cY, 0));
                }
            } else if (Settings.SHOW_DMG_BLOCK) {
                AbstractDungeon.effectList.add(new BlockedWordEffect(this, this.hb.cX, this.hb.cY, AbstractMonster.TEXT[30]));
            }
        }
    }

    @Override
    public void die() {
        if (this.currentHealth <= 0) {
            BossMechanicDisplayPanel.resetBossPanel();
            useFastShakeAnimation(5.0F);
            CardCrawlGame.screenShake.rumble(4.0F);
            if (!(this instanceof CharBossMerchant)) {
                if (hasPower(MinionPower.POWER_ID)) {
                    if (NeowBoss.neowboss.minion == null) {
                        if (Settings.FAST_MODE) {
                            this.deathTimer += 0.7F;
                        } else {
                            ++this.deathTimer;
                        }
                    }
                } else {
                    //SlimeboundMod.logger.info("Char boss On Boss Victory now playing");
                    onBossVictoryLogic();
                }
            }

        }

        if (hasPower(MinionPower.POWER_ID)) {
            if (NeowBoss.neowboss.minion == this) {
                SlimeboundMod.logger.info("Archetype act num: " + chosenArchetype.actNum);
                NeowBoss.neowboss.minion = null;
                if (chosenArchetype.actNum == 3) {
                    SlimeboundMod.logger.info("Boss instructing Neow to Self Rez");
                    NeowBoss.neowboss.switchIntentToSelfRez();
                } else {
                    SlimeboundMod.logger.info("Boss instructing Neow to Rez");
                    NeowBoss.neowboss.switchToRez();
                }
            }
        }

        AbstractCharBoss.boss = null;
        AbstractCharBoss.finishedSetup = false;
        hand.clear();
        /*
        drawPile.clear();
        discardPile.clear();
        exhaustPile.clear();
        */
        limbo.clear();
        orbs.clear();
        stance.onExitStance();
        stance = AbstractEnemyStance.getStanceFromName("Neutral");
        stance.onEnterStance();

        super.die();


    }


    @Override
    protected void onFinalBossVictoryLogic() {

        //AbstractDungeon.ascensionLevel = storedAsc;
    }

    private void updateCardsOnDamage() {
        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            for (final AbstractCard c : this.hand.group) {
                c.tookDamage();
            }
            /*
            for (final AbstractCard c : this.discardPile.group) {
                c.tookDamage();
            }
            for (final AbstractCard c : this.drawPile.group) {
                c.tookDamage();
            }
            */
        }
    }


    public void updateCardsOnDiscard() {
        for (final AbstractCard c : this.hand.group) {
            c.didDiscard();
        }
        /*
        for (final AbstractCard c : this.discardPile.group) {
            c.didDiscard();
        }
        for (final AbstractCard c : this.drawPile.group) {
            c.didDiscard();
        }
        */
    }

    @Override
    public void heal(final int healAmount) {
        int amt = healAmount;
        for (final AbstractRelic r : this.relics) {
            amt = r.onPlayerHeal(amt);
        }
        super.heal(amt);
        if (this.currentHealth > this.maxHealth / 2.0f && this.isBloodied) {
            this.isBloodied = false;
            for (final AbstractRelic r : this.relics) {
                r.onNotBloodied();
            }
        }
    }

    public void preBattlePrep() {
        this.damagedThisCombat = 0;
        this.cardsPlayedThisTurn = 0;
        this.attacksPlayedThisTurn = 0;
        this.maxOrbs = 0;
        this.orbs.clear();
        this.increaseMaxOrbSlots(this.masterMaxOrbs, false);
        this.isBloodied = (this.currentHealth <= this.maxHealth / 2);
        AbstractPlayer.poisonKillCount = 0;
        this.gameHandSize = this.masterHandSize;
        this.cardInUse = null;


        // this.drawPile.initializeDeck(this.masterDeck);

        //this.drawPile.clear();
        //CardGroup copy = new CardGroup(this.masterDeck, CardGroup.CardGroupType.DRAW_PILE);
        //for (AbstractCard c : copy.group) {
        //    this.drawPile.addToBottom(c);
        //}

        AbstractDungeon.overlayMenu.endTurnButton.enabled = false;
        this.hand.clear();
        /*
        this.discardPile.clear();
        this.exhaustPile.clear();
        */
        if (this.hasRelic("SlaversCollar")) {
            ((SlaversCollar) this.getRelic("SlaversCollar")).beforeEnergyPrep();
        }
        this.energy.prep();
        this.powers.clear();
        this.healthBarUpdatedEvent();
        this.applyPreCombatLogic();

    }

    public ArrayList<String> getRelicNames() {
        final ArrayList<String> arr = new ArrayList<String>();
        for (final AbstractRelic relic : this.relics) {
            arr.add(relic.relicId);
        }
        return arr;
    }

    public void applyPreCombatLogic() {
        for (final AbstractRelic r : this.relics) {
            if (r != null) {
                r.atPreBattle();
            }
        }
    }

    public void applyStartOfCombatLogic() {

        for (final AbstractRelic r : this.relics) {
            if (r != null) {
                r.atBattleStart();
            }
        }
    }

    public void applyStartOfCombatPreDrawLogic() {
        for (final AbstractRelic r : this.relics) {
            if (r != null) {
                r.atBattleStartPreDraw();
            }
        }
    }

    public void applyStartOfTurnRelics() {
        this.stance.atStartOfTurn();
        for (final AbstractRelic r : this.relics) {
            if (r != null) {
                r.atTurnStart();
            }
        }
    }

    public void applyStartOfTurnPostDrawRelics() {
        for (final AbstractRelic r : this.relics) {
            if (r != null) {
                r.atTurnStartPostDraw();
            }
        }
    }

    public void applyStartOfTurnPreDrawCards() {
        for (final AbstractCard c : this.hand.group) {
            if (c != null) {
                c.atTurnStartPreDraw();
            }
        }
    }

    public void applyStartOfTurnCards() {
        /*
        for (final AbstractCard c : this.drawPile.group) {
            if (c != null) {
                c.atTurnStart();
            }
        }
        */
        for (final AbstractCard c : this.hand.group) {
            if (c != null) {
                c.atTurnStart();
                c.triggerWhenDrawn();
            }
        }
        /*
        for (final AbstractCard c : this.discardPile.group) {
            if (c != null) {
                c.atTurnStart();
            }
        }
        */
    }

    public boolean relicsDoneAnimating() {
        for (final AbstractRelic r : this.relics) {
            if (!r.isDone) {
                return false;
            }
        }
        return true;
    }

    public void switchedStance() {
        for (final AbstractCard c : this.hand.group) {
            c.switchedStance();
        }
        /*
        for (final AbstractCard c : this.discardPile.group) {
            c.switchedStance();
        }
        for (final AbstractCard c : this.drawPile.group) {
            c.switchedStance();
        }
        */
    }

    public void onStanceChange(final String id) {
    }

    public void addBlock(final int blockAmount) {
        float tmp = (float) blockAmount;
        for (final AbstractRelic r : this.relics) {
            tmp = (float) r.onPlayerGainedBlock(tmp);
        }
        if (tmp > 0.0f) {
            for (final AbstractPower p : this.powers) {
                p.onGainedBlock(tmp);
            }
        }
        super.addBlock((int) Math.floor(tmp));
    }
    ///////////////////////////////////////
    ///////////[ORBS]//////////////////////
    ///////////////////////////////////////


    public void triggerEvokeAnimation(final int slot) {
        if (this.maxOrbs <= 0) {
            return;
        }
        this.orbs.get(slot).triggerEvokeAnimation();
    }

    public void evokeOrb() {
        if (!this.orbs.isEmpty() && !(this.orbs.get(0) instanceof EnemyEmptyOrbSlot)) {
            this.orbs.get(0).onEvoke();
            final AbstractEnemyOrb orbSlot = new EnemyEmptyOrbSlot();
            for (int i = 1; i < this.orbs.size(); ++i) {
                Collections.swap(this.orbs, i, i - 1);
            }
            this.orbs.set(this.orbs.size() - 1, orbSlot);
            for (int i = 0; i < this.orbs.size(); ++i) {
                this.orbs.get(i).setSlot(i, this.maxOrbs);
            }
        }
    }

    public ArrayList<AbstractEnemyOrb> orbsAsEn() {
        ArrayList<AbstractEnemyOrb> orbies = new ArrayList<AbstractEnemyOrb>();
        for (AbstractOrb o : orbs) {
            if (o instanceof AbstractEnemyOrb) {
                orbies.add((AbstractEnemyOrb)o);
            }
        }
        return orbies;
    }

    public void evokeNewestOrb() {
        if (!this.orbs.isEmpty() && !(this.orbs.get(this.orbs.size() - 1) instanceof EnemyEmptyOrbSlot)) {
            this.orbs.get(this.orbs.size() - 1).onEvoke();
        }
    }

    public void evokeWithoutLosingOrb() {
        if (!this.orbs.isEmpty() && !(this.orbs.get(0) instanceof EnemyEmptyOrbSlot)) {
            this.orbs.get(0).onEvoke();
        }
    }

    public void removeNextOrb() {
        if (!this.orbs.isEmpty() && !(this.orbs.get(0) instanceof EnemyEmptyOrbSlot)) {
            final AbstractEnemyOrb orbSlot = new EnemyEmptyOrbSlot(this.orbs.get(0).cX, this.orbs.get(0).cY);
            for (int i = 1; i < this.orbs.size(); ++i) {
                Collections.swap(this.orbs, i, i - 1);
            }
            this.orbs.set(this.orbs.size() - 1, orbSlot);
            for (int i = 0; i < this.orbs.size(); ++i) {
                this.orbs.get(i).setSlot(i, this.maxOrbs);
            }
        }
    }

    public boolean hasEmptyOrb() {
        if (this.orbs.isEmpty()) {
            return false;
        }
        for (final AbstractOrb o : this.orbs) {
            if (o instanceof EnemyEmptyOrbSlot) {
                return true;
            }
        }
        return false;
    }

    public boolean hasOrb() {
        return !this.orbs.isEmpty() && !(this.orbs.get(0) instanceof EnemyEmptyOrbSlot);
    }

    public int filledOrbCount() {
        int orbCount = 0;
        for (final AbstractOrb o : this.orbs) {
            if (!(o instanceof EnemyEmptyOrbSlot)) {
                ++orbCount;
            }
        }
        return orbCount;
    }

    public void channelOrb(AbstractOrb orbToSet) {
        if (this.maxOrbs <= 0) {
            AbstractDungeon.effectList.add(new ThoughtBubble(this.dialogX, this.dialogY, 3.0f, AbstractPlayer.MSG[4], true));
            return;
        }
        if (this.hasRelic("Dark Core") && !(orbToSet instanceof EnemyDark)) {
            orbToSet = new EnemyDark();
        }
        int index = -1;
        for (int i = 0; i < this.orbs.size(); ++i) {
            if (this.orbs.get(i) instanceof EnemyEmptyOrbSlot) {
                index = i;
                break;
            }
        }
        if (index != -1) {
            orbToSet.cX = this.orbs.get(index).cX;
            orbToSet.cY = this.orbs.get(index).cY;
            this.orbs.set(index, orbToSet);
            this.orbs.get(index).setSlot(index, this.maxOrbs);
            orbToSet.playChannelSFX();
            for (final AbstractPower p : this.powers) {
                p.onChannel(orbToSet);
            }
            AbstractDungeon.actionManager.orbsChanneledThisCombat.add(orbToSet);
            AbstractDungeon.actionManager.orbsChanneledThisTurn.add(orbToSet);
            orbToSet.applyFocus();
        } else {
            AbstractDungeon.actionManager.addToTop(new EnemyChannelAction(orbToSet));
            AbstractDungeon.actionManager.addToTop(new EnemyEvokeOrbAction(1));
            AbstractDungeon.actionManager.addToTop(new EnemyAnimateOrbAction(1));
        }
    }

    public void increaseMaxOrbSlots(final int amount, final boolean playSfx) {
        if (this.maxOrbs == 10) {
            AbstractDungeon.effectList.add(new ThoughtBubble(this.dialogX, this.dialogY, 3.0f, AbstractPlayer.MSG[3], true));
            return;
        }
        if (playSfx) {
            CardCrawlGame.sound.play("ORB_SLOT_GAIN", 0.1f);
        }
        this.maxOrbs += amount;
        for (int i = 0; i < amount; ++i) {
            this.orbs.add(new EnemyEmptyOrbSlot());
        }
        for (int i = 0; i < this.orbs.size(); ++i) {
            this.orbs.get(i).setSlot(i, this.maxOrbs);
        }
    }

    public void decreaseMaxOrbSlots(final int amount) {
        if (this.maxOrbs <= 0) {
            return;
        }
        this.maxOrbs -= amount;
        if (this.maxOrbs < 0) {
            this.maxOrbs = 0;
        }
        if (!this.orbs.isEmpty()) {
            this.orbs.remove(this.orbs.size() - 1);
        }
        for (int i = 0; i < this.orbs.size(); ++i) {
            this.orbs.get(i).setSlot(i, this.maxOrbs);
        }
    }

    public void applyStartOfTurnOrbs() {
        if (!this.orbs.isEmpty()) {
            for (final AbstractOrb o : this.orbs) {
                o.onStartOfTurn();
            }
            if (this.hasRelic("Cables") && !(this.orbs.get(0) instanceof EnemyEmptyOrbSlot)) {
                this.orbs.get(0).onStartOfTurn();
            }
        }
    }


    /////////////////////////////////////////////////////////////////////////////
    ////////////[[[[[[[[THE ALMIGHTY RENDERING]]]]]]]]///////////////////////////
    /////////////////////////////////////////////////////////////////////////////
    @Override
    public void render(final SpriteBatch sb) {
        super.render(sb);
        if (!this.isDead) {
            this.renderHand(sb);
            this.stance.render(sb);
            for (AbstractRelic r : this.relics) {
                r.render(sb);
            }
            if (!this.orbs.isEmpty()) {

                for (AbstractOrb o : this.orbs) {
                    o.render(sb);
                }
            }
            this.energyPanel.render(sb);
        }
    }

    public void renderHand(final SpriteBatch sb) {
        /*if (this.hoveredCard != null) {
            int aliveMonsters = 0;
            this.hand.renderHand(sb, this.hoveredCard);
            this.hoveredCard.renderHoverShadow(sb);
            if ((this.isDraggingCard || this.inSingleTargetMode) && this.isHoveringDropZone) {
                if (this.isDraggingCard && !this.inSingleTargetMode) {
                    AbstractMonster theMonster = null;
                    for (final AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
                        if (!m.isDying && m.currentHealth > 0) {
                            ++aliveMonsters;
                            theMonster = m;
                        }
                    }
                    if (aliveMonsters == 1 && this.hoveredMonster == null) {
                        this.hoveredCard.calculateCardDamage(theMonster);
                        this.hoveredCard.render(sb);
                        this.hoveredCard.genPreview();
                    }
                    else {
                        this.hoveredCard.render(sb);
                    }
                }
                if (!AbstractDungeon.getCurrRoom().isBattleEnding()) {
                    this.renderHoverReticle(sb);
                }
            }
            if (this.hoveredMonster != null) {
                this.hoveredCard.calculateCardDamage(this.hoveredMonster);
                this.hoveredCard.render(sb);
                this.hoveredCard.genPreview();
            }
            else if (aliveMonsters != 1) {
                this.hoveredCard.render(sb);
            }
        }
        else if (AbstractDungeon.screen == AbstractDungeon.CurrentScreen.HAND_SELECT) {
            this.hand.render(sb);
        }
        else {
            this.hand.renderHand(sb, this.cardInUse);
        }*/
        this.hand.renderHand(sb, this.cardInUse);
        if (this.cardInUse != null && AbstractDungeon.screen != AbstractDungeon.CurrentScreen.HAND_SELECT && !PeekButton.isPeeking) {
            this.cardInUse.render(sb);
            if (AbstractDungeon.getCurrRoom().phase != AbstractRoom.RoomPhase.COMBAT) {
                AbstractDungeon.effectList.add(new CardDisappearEffect(this.cardInUse.makeCopy(), this.cardInUse.current_x, this.cardInUse.current_y));
                this.cardInUse = null;
            }
        }
        this.limbo.render(sb);
    }

    private enum DrawTypes {
        Attack,
        Setup,
        EitherPhase;

        DrawTypes() {
        }
    }

    @Override
    public void dispose() {
        super.dispose();
        BossMechanicDisplayPanel.resetBossPanel();
    }
}
