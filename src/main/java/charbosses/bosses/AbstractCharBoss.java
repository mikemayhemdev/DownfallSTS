package charbosses.bosses;

import charbosses.actions.common.EnemyDiscardAtEndOfTurnAction;
import charbosses.actions.common.EnemyDrawCardAction;
import charbosses.actions.common.EnemyUseCardAction;
import charbosses.actions.orb.EnemyAnimateOrbAction;
import charbosses.actions.orb.EnemyChannelAction;
import charbosses.actions.orb.EnemyEvokeOrbAction;
import charbosses.actions.orb.EnemyTriggerEndOfTurnOrbActions;
import charbosses.actions.util.CharbossDoNextCardAction;
import charbosses.actions.util.CharbossSortHandAction;
import charbosses.actions.util.CharbossTurnstartDrawAction;
import charbosses.actions.util.DelayedActionAction;
import charbosses.cards.AbstractBossCard;
import charbosses.cards.AbstractBossDeckArchetype;
import charbosses.cards.EnemyCardGroup;
import charbosses.cards.curses.*;
import charbosses.core.EnemyEnergyManager;
import charbosses.orbs.EnemyDark;
import charbosses.orbs.EnemyEmptyOrbSlot;
import charbosses.relics.*;
import charbosses.ui.EnemyEnergyPanel;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier;
import com.megacrit.cardcrawl.relics.LizardTail;
import com.megacrit.cardcrawl.relics.SlaversCollar;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.stances.AbstractStance;
import com.megacrit.cardcrawl.stances.NeutralStance;
import com.megacrit.cardcrawl.ui.buttons.PeekButton;
import com.megacrit.cardcrawl.ui.panels.energyorb.EnergyOrbInterface;
import com.megacrit.cardcrawl.vfx.ThoughtBubble;
import com.megacrit.cardcrawl.vfx.cardManip.CardDisappearEffect;
import com.megacrit.cardcrawl.vfx.combat.BlockedWordEffect;
import com.megacrit.cardcrawl.vfx.combat.DeckPoofEffect;
import com.megacrit.cardcrawl.vfx.combat.HbBlockBrokenEffect;
import com.megacrit.cardcrawl.vfx.combat.StrikeEffect;

import java.util.ArrayList;
import java.util.Collections;

public abstract class AbstractCharBoss extends AbstractMonster {

    public static AbstractCharBoss boss;
    public static boolean finishedSetup;
    /////////////////////////////////////////////////////////////////////////////
    ////////////[[[[[[[[STATIC SHIT DOWN HERE]]]]]]]]////////////////////////////
    /////////////////////////////////////////////////////////////////////////////
    public static ArrayList<AbstractCharbossRelic> VALID_RELICS;
    public static ArrayList<AbstractCharbossRelic> BOSS_RELICS;
    public static ArrayList<AbstractBossCard> CURSES;

    static {
        VALID_RELICS = new ArrayList<AbstractCharbossRelic>();
        VALID_RELICS.add(new CBR_Anchor());
        VALID_RELICS.add(new CBR_BirdFacedUrn());
        VALID_RELICS.add(new CBR_Boot());
        VALID_RELICS.add(new CBR_BronzeScales());
        VALID_RELICS.add(new CBR_CaptainsWheel());
        VALID_RELICS.add(new CBR_DuvuDoll());
        VALID_RELICS.add(new CBR_Girya());
        VALID_RELICS.add(new CBR_HandDrill());
        VALID_RELICS.add(new CBR_HappyFlower());
        VALID_RELICS.add(new CBR_IceCream());
        VALID_RELICS.add(new CBR_IncenseBurner());
        VALID_RELICS.add(new CBR_Kunai());
        VALID_RELICS.add(new CBR_LetterOpener());
        VALID_RELICS.add(new CBR_LizardTail());
        VALID_RELICS.add(new CBR_Mango());
        VALID_RELICS.add(new CBR_MercuryHourglass());
        VALID_RELICS.add(new CBR_Orichalcum());
        VALID_RELICS.add(new CBR_SelfFormingClay());
        VALID_RELICS.add(new CBR_Shuriken());
        VALID_RELICS.add(new CBR_SmoothStone());
        VALID_RELICS.add(new CBR_Torii());
        VALID_RELICS.add(new CBR_TungstenRod());
        VALID_RELICS.add(new CBR_Vajra());
        VALID_RELICS.add(new CBR_WarPaint());
        VALID_RELICS.add(new CBR_Whetstone());


        BOSS_RELICS = new ArrayList<AbstractCharbossRelic>();
        BOSS_RELICS.add(new CBR_CursedKey());
        BOSS_RELICS.add(new CBR_PhilosopherStone());
        BOSS_RELICS.add(new CBR_Ectoplasm());
        BOSS_RELICS.add(new CBR_CallingBell());

        CURSES = new ArrayList<AbstractBossCard>();
        CURSES.add(new EnInjury());
        CURSES.add(new EnDecay());
        CURSES.add(new EnDoubt());
        CURSES.add(new EnRegret());
        CURSES.add(new EnWrithe());
        CURSES.add(new EnShame());

        CURSES.add(new EnWrithe());
        CURSES.add(new EnDoubt());
    }

    public ArrayList<AbstractCharbossRelic> relics;
    public AbstractStance stance;
    public ArrayList<AbstractOrb> orbs;
    public int maxOrbs;
    public int masterMaxOrbs;
    public int masterHandSize;
    public int gameHandSize;
    public EnemyEnergyManager energy;
    public EnergyOrbInterface energyOrb;
    public EnemyEnergyPanel energyPanel;
    public CardGroup masterDeck;
    public CardGroup drawPile;
    public CardGroup hand;
    public CardGroup discardPile;
    public CardGroup exhaustPile;
    public CardGroup limbo;
    public AbstractCard cardInUse;
    public int damagedThisCombat;
    public int cardsPlayedThisTurn;
    public int attacksPlayedThisTurn;
    public AbstractPlayer.PlayerClass chosenClass;
    public AbstractBossDeckArchetype chosenArchetype = null;
    public int uncommonRelicBudget;
    public int rareRelicBudget;
    public int relicBudget;
    public ArrayList<AbstractCharbossRelic> relicPool;
    public ArrayList<AbstractCharbossRelic> bossRelicPool;
    protected AbstractCharbossRelic startingRelic = null;

    public AbstractCharBoss(String name, String id, int maxHealth, float hb_x, float hb_y, float hb_w, float hb_h, String imgUrl, float offsetX, float offsetY, PlayerClass playerClass) {
        super(name, id, maxHealth, hb_x, hb_y, hb_w, hb_h, imgUrl, offsetX, offsetY);
        AbstractCharBoss.finishedSetup = false;
        this.type = EnemyType.BOSS;
        this.chosenClass = playerClass;
        this.energyPanel = new EnemyEnergyPanel(this);
        this.masterDeck = new EnemyCardGroup(CardGroupType.MASTER_DECK, this);
        this.drawPile = new EnemyCardGroup(CardGroupType.DRAW_PILE, this);
        this.discardPile = new EnemyCardGroup(CardGroupType.DISCARD_PILE, this);
        this.hand = new EnemyCardGroup(CardGroupType.HAND, this);
        this.exhaustPile = new EnemyCardGroup(CardGroupType.EXHAUST_PILE, this);
        this.limbo = new EnemyCardGroup(CardGroupType.UNSPECIFIED, this);
        this.masterHandSize = 5;
        this.gameHandSize = 5;
        this.masterMaxOrbs = this.maxOrbs = 0;
        this.stance = new NeutralStance();
        this.orbs = new ArrayList<AbstractOrb>();
        this.relicBudget = AbstractDungeon.actNum * 3 - 1;
        this.uncommonRelicBudget = AbstractDungeon.actNum * 2 - 1;
        this.rareRelicBudget = AbstractDungeon.actNum - 1;
        this.relicPool = new ArrayList<AbstractCharbossRelic>(VALID_RELICS);
        this.bossRelicPool = new ArrayList<AbstractCharbossRelic>(BOSS_RELICS);
    }

    public static AbstractBossCard getRandomCurse() {
        return CURSES.get(AbstractDungeon.monsterRng.random(CURSES.size() - 1));
    }

    @Override
    public void init() {
        AbstractCharBoss.boss = this;
        this.setHp(this.maxHealth + (AbstractDungeon.actNum - 1) * 50, this.maxHealth + 20 + (AbstractDungeon.actNum - 1) * 75);
        this.generateAll();
        super.init();
        this.preBattlePrep();
        AbstractCharBoss.finishedSetup = true;
    }

    @Override
    public void getMove(int num) {
        this.setMove((byte) 0, Intent.NONE);
    }

    public abstract void generateDeck();

    public void generateRelics() {
        this.relics = new ArrayList<AbstractCharbossRelic>();
        if (this.startingRelic != null) {
            this.startingRelic.instantObtain(this);
        }

        for (int i = 0; i < AbstractDungeon.actNum - 1; i++) {
            this.bossRelicPool.remove(AbstractDungeon.monsterRng.random(this.bossRelicPool.size() - 1)).instantObtain(this);
        }

        for (int i = 0; i < this.relicBudget; i++) {
            int r = AbstractDungeon.monsterRng.random(this.relicPool.size() - 1);
            AbstractRelic.RelicTier rt = this.relicPool.get(r).tier;
            if (rt == RelicTier.UNCOMMON) {
                if (this.uncommonRelicBudget > 0) {
                    this.uncommonRelicBudget--;
                } else {
                    i--;
                    continue;
                }
            } else if (rt == RelicTier.RARE) {
                if (this.rareRelicBudget > 0) {
                    this.rareRelicBudget--;
                } else {
                    i--;
                    continue;
                }
            }
            this.relicPool.remove(r).instantObtain(this);
        }
    }

    public void generateHistory() {
        //CharbossHistoryPower p = new CharbossHistoryPower(this);
    }

    public void generateAll() {
        this.generateDeck();
        this.generateRelics();
        this.generateHistory();
        for (AbstractCard c : this.masterDeck.group) {
            ((AbstractBossCard) c).owner = this;
        }
    }

    public void usePreBattleAction() {
        for (AbstractCharbossRelic r : this.relics) {
            r.atBattleStartPreDraw();
        }
        AbstractDungeon.actionManager.addToBottom(new DelayedActionAction(new CharbossTurnstartDrawAction()));
        for (AbstractCharbossRelic r : this.relics) {
            r.atBattleStart();
        }
    }

    @Override
    public void takeTurn() {
        this.startTurn();
        this.makePlay();
    }

    public void makePlay() {
        for (AbstractCard _c : this.hand.group) {
            AbstractBossCard c = (AbstractBossCard) _c;
            if (c.canUse(AbstractDungeon.player, this)) {
                this.useCard(c, this, this.energyPanel.totalCount);
                this.addToBot(new DelayedActionAction(new CharbossDoNextCardAction()));
                return;
            }
        }
    }

    @Override
    public void update() {
        super.update();
        for (AbstractRelic r : this.relics) {
            r.update();
        }
        this.combatUpdate();
    }

    @Override
    public void applyEndOfTurnTriggers() {
        for (final AbstractRelic r : this.relics) {
            r.onPlayerEndTurn();
        }
        for (final AbstractPower p : this.powers) {
            if (!this.isPlayer) {
                p.atEndOfTurnPreEndTurnCards(false);
            }
            p.atEndOfTurn(this.isPlayer);
        }
        AbstractDungeon.actionManager.addToBottom(new EnemyTriggerEndOfTurnOrbActions());
        for (final AbstractCard c : this.hand.group) {
            c.triggerOnEndOfTurnForPlayingCard();
        }
        this.stance.onEndOfTurn();

        AbstractDungeon.actionManager.addToBottom(new EnemyDiscardAtEndOfTurnAction());
        for (final AbstractCard c : this.drawPile.group) {
            c.resetAttributes();
        }
        for (final AbstractCard c : this.discardPile.group) {
            c.resetAttributes();
        }
        for (final AbstractCard c : this.hand.group) {
            c.resetAttributes();
        }
        AbstractDungeon.actionManager.addToBottom(new DelayedActionAction(new CharbossTurnstartDrawAction()));
    }

    public void startTurn() {
        this.cardsPlayedThisTurn = 0;
        this.attacksPlayedThisTurn = 0;
        this.energy.recharge();
        this.applyStartOfTurnRelics();
        this.applyStartOfTurnPreDrawCards();
        this.applyStartOfTurnCards();
        this.applyStartOfTurnPowers();
        this.applyStartOfTurnOrbs();
    }

    public void endTurnStartTurn() {
        if (!AbstractDungeon.getCurrRoom().isBattleOver) {
            AbstractDungeon.actionManager.addToBottom(new EnemyDrawCardAction(this, this.gameHandSize, true));
            AbstractDungeon.actionManager.addToBottom(new WaitAction(0.2f));
            AbstractDungeon.actionManager.addToBottom(new CharbossSortHandAction());
            this.applyStartOfTurnPostDrawRelics();
            this.applyStartOfTurnPostDrawPowers();
        }
    }

    public void applyPowers() {
        super.applyPowers();
        this.hand.applyPowers();
        this.drawPile.applyPowers();
        this.discardPile.applyPowers();
    }

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
                    if (cardsByValue.get(i).getValue() < c.getValue() + AbstractDungeon.aiRng.random(0, 4) - 2) {
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
        int budget = this.energy.energy;
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
                    if (((AbstractBossCard) sortedCards.get(j)).getPriority() < c.getPriority()) {
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
        this.hand.group = sortedCards;
        this.hand.refreshHandLayout();
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

    public void draw() {
        if (this.hand.size() == 10) {
            //this.createHandIsFullDialog();
            return;
        }
        CardCrawlGame.sound.playAV("CARD_DRAW_8", -0.12f, 0.25f);
        this.draw(1);
        this.onCardDrawOrDiscard();
    }

    public void draw(final int numCards) {
        for (int i = 0; i < numCards; ++i) {
            if (!this.drawPile.isEmpty()) {
                final AbstractCard c = this.drawPile.getTopCard();
                c.current_x = CardGroup.DRAW_PILE_X;
                c.current_y = CardGroup.DRAW_PILE_Y;
                c.setAngle(0.0f, true);
                c.lighten(false);
                c.drawScale = 0.12f;
                c.targetDrawScale = AbstractBossCard.HAND_SCALE;
                c.triggerWhenDrawn();
                this.hand.addToHand(c);
                this.drawPile.removeTopCard();
                for (final AbstractPower p : this.powers) {
                    p.onCardDraw(c);
                }
                for (final AbstractRelic r : this.relics) {
                    r.onCardDraw(c);
                }
            }
        }
    }

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
        AbstractDungeon.actionManager.addToBottom(new EnemyUseCardAction(c, monster));
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
    }

    public void combatUpdate() {
        if (this.cardInUse != null) {
            this.cardInUse.update();
        }
        this.energyPanel.update();
        this.limbo.update();
        this.exhaustPile.update();
        this.hand.update();
        this.drawPile.update();
        this.discardPile.update();
        this.hand.updateHoverLogic();
        for (final AbstractPower p : this.powers) {
            p.updateParticles();
        }
        for (final AbstractOrb o : this.orbs) {
            o.update();
        }
        this.stance.update();
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
        if (damageAmount > 1 && this.hasPower("IntangiblePlayer")) {
            damageAmount = 1;
        }
        final boolean weakenedToZero = damageAmount == 0;
        damageAmount = this.decrementBlock(info, damageAmount);
        if (info.owner == this) {
            for (final AbstractRelic r : this.relics) {
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
        if (damageAmount > 0) {
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
                    if (this.hasRelic("Lizard Tail") && ((LizardTail) this.getRelic("Lizard Tail")).counter == -1) {
                        this.currentHealth = 0;
                        this.getRelic("Lizard Tail").onTrigger();
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
        super.die();
        AbstractCharBoss.boss = null;
        AbstractCharBoss.finishedSetup = false;
    }

    private void updateCardsOnDamage() {
        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            for (final AbstractCard c : this.hand.group) {
                c.tookDamage();
            }
            for (final AbstractCard c : this.discardPile.group) {
                c.tookDamage();
            }
            for (final AbstractCard c : this.drawPile.group) {
                c.tookDamage();
            }
        }
    }

    public void updateCardsOnDiscard() {
        for (final AbstractCard c : this.hand.group) {
            c.didDiscard();
        }
        for (final AbstractCard c : this.discardPile.group) {
            c.didDiscard();
        }
        for (final AbstractCard c : this.drawPile.group) {
            c.didDiscard();
        }
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
        this.drawPile.initializeDeck(this.masterDeck);
        AbstractDungeon.overlayMenu.endTurnButton.enabled = false;
        this.hand.clear();
        this.discardPile.clear();
        this.exhaustPile.clear();
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
    ///////////////////////////////////////
    ///////////[ORBS]//////////////////////
    ///////////////////////////////////////

    public void applyStartOfTurnCards() {
        for (final AbstractCard c : this.drawPile.group) {
            if (c != null) {
                c.atTurnStart();
            }
        }
        for (final AbstractCard c : this.hand.group) {
            if (c != null) {
                c.atTurnStart();
            }
        }
        for (final AbstractCard c : this.discardPile.group) {
            if (c != null) {
                c.atTurnStart();
            }
        }
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
        for (final AbstractCard c : this.discardPile.group) {
            c.switchedStance();
        }
        for (final AbstractCard c : this.drawPile.group) {
            c.switchedStance();
        }
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

    public void triggerEvokeAnimation(final int slot) {
        if (this.maxOrbs <= 0) {
            return;
        }
        this.orbs.get(slot).triggerEvokeAnimation();
    }

    public void evokeOrb() {
        if (!this.orbs.isEmpty() && !(this.orbs.get(0) instanceof EnemyEmptyOrbSlot)) {
            this.orbs.get(0).onEvoke();
            final AbstractOrb orbSlot = new EnemyEmptyOrbSlot();
            for (int i = 1; i < this.orbs.size(); ++i) {
                Collections.swap(this.orbs, i, i - 1);
            }
            this.orbs.set(this.orbs.size() - 1, orbSlot);
            for (int i = 0; i < this.orbs.size(); ++i) {
                this.orbs.get(i).setSlot(i, this.maxOrbs);
            }
        }
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
            final AbstractOrb orbSlot = new EnemyEmptyOrbSlot(this.orbs.get(0).cX, this.orbs.get(0).cY);
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
        if (this.maxOrbs > 0) {
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
        this.renderHand(sb);
        for (AbstractRelic r : this.relics) {
            r.render(sb);
        }
        this.energyPanel.render(sb);
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
                        this.hoveredCard.applyPowers();
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
                this.hoveredCard.applyPowers();
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
}
