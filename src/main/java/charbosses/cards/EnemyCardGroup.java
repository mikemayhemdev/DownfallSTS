package charbosses.cards;

import java.util.ArrayList;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.cardManip.ExhaustCardEffect;

import charbosses.actions.common.EnemyDrawCardAction;
import charbosses.bosses.AbstractCharBoss;

public class EnemyCardGroup extends CardGroup {
	public AbstractCharBoss owner;
	public EnemyCardGroup(CardGroupType type) {
		super(type);
		this.owner = AbstractCharBoss.boss;
	}
	public EnemyCardGroup(CardGroupType type, AbstractCharBoss owner) {
		super(type);
		this.owner = owner;
	}
	public EnemyCardGroup(CardGroup group, CardGroupType type) {
		super(group, type);
		this.owner = AbstractCharBoss.boss;
	}
	public EnemyCardGroup(CardGroup group, CardGroupType type, AbstractCharBoss owner) {
		super(group, type);
		this.owner = owner;
	}
	
	public void moveToDiscardPile(final AbstractCard c) {
        this.resetCardBeforeMoving(c);
        c.darken(false);
        this.owner.discardPile.addToTop(c);
        this.owner.onCardDrawOrDiscard();
    }
	
	public void moveToExhaustPile(final AbstractCard c) {
        for (final AbstractRelic r : this.owner.relics) {
            r.onExhaust(c);
        }
        for (final AbstractPower p : this.owner.powers) {
            p.onExhaust(c);
        }
        c.triggerOnExhaust();
        this.resetCardBeforeMoving(c);
        AbstractDungeon.effectList.add(new ExhaustCardEffect(c));
        this.owner.exhaustPile.addToTop(c);
        this.owner.onCardDrawOrDiscard();
    }
    
    public void moveToHand(final AbstractCard c, final CardGroup group) {
        c.unhover();
        c.lighten(true);
        c.setAngle(0.0f);
        c.drawScale = 0.12f;
        c.targetDrawScale = 0.75f;
        c.current_x = CardGroup.DRAW_PILE_X;
        c.current_y = CardGroup.DRAW_PILE_Y;
        group.removeCard(c);
        this.owner.hand.addToTop(c);
        this.owner.hand.refreshHandLayout();
        this.owner.hand.applyPowers();
    }
	

    public void moveToHand(final AbstractCard c) {
        this.resetCardBeforeMoving(c);
        c.unhover();
        c.lighten(true);
        c.setAngle(0.0f);
        c.drawScale = 0.12f;
        c.targetDrawScale = 0.75f;
        c.current_x = CardGroup.DRAW_PILE_X;
        c.current_y = CardGroup.DRAW_PILE_Y;
        this.owner.hand.addToTop(c);
        this.owner.hand.refreshHandLayout();
        this.owner.hand.applyPowers();
    }
    
    public void moveToDeck(final AbstractCard c, final boolean randomSpot) {
        this.resetCardBeforeMoving(c);
        if (randomSpot)
        	this.owner.drawPile.addToRandomSpot(c);
        else
        	this.owner.drawPile.addToTop(c);
    }
    
    public void moveToBottomOfDeck(final AbstractCard c) {
        this.resetCardBeforeMoving(c);
        this.owner.drawPile.addToBottom(c);
    }
    
    private void resetCardBeforeMoving(final AbstractCard c) {
        if (AbstractDungeon.player.hoveredCard == c) {
            AbstractDungeon.player.releaseCard();
        }
        AbstractDungeon.actionManager.removeFromQueue(c);
        c.unhover();
        c.untip();
        c.stopGlowing();
        this.group.remove(c);
    }
    
    public void initializeDeck(final CardGroup masterDeck) {
        this.clear();
        final CardGroup copy = new CardGroup(masterDeck, CardGroupType.DRAW_PILE);
        copy.shuffle(AbstractDungeon.shuffleRng);
        final ArrayList<AbstractCard> placeOnTop = new ArrayList<AbstractCard>();
        for (final AbstractCard c : copy.group) {
            if (c.isInnate) {
                placeOnTop.add(c);
            }
            else if (c.inBottleFlame || c.inBottleLightning || c.inBottleTornado) {
                placeOnTop.add(c);
            }
            else {
                c.target_x = CardGroup.DRAW_PILE_X;
                c.target_y = CardGroup.DRAW_PILE_Y;
                c.current_x = CardGroup.DRAW_PILE_X;
                c.current_y = CardGroup.DRAW_PILE_Y;
                this.addToTop(c);
            }
        }
        for (final AbstractCard c : placeOnTop) {
            this.addToTop(c);
        }
        if (placeOnTop.size() > AbstractDungeon.player.masterHandSize) {
            AbstractDungeon.actionManager.addToTurnStart(new EnemyDrawCardAction(AbstractCharBoss.boss, placeOnTop.size() - AbstractCharBoss.boss.masterHandSize));
        }
        placeOnTop.clear();
    }
    
    public void triggerOnOtherCardPlayed(final AbstractCard usedCard) {
        for (final AbstractCard c : this.group) {
            if (c != usedCard) {
                c.triggerOnOtherCardPlayed(usedCard);
            }
        }
        for (final AbstractPower p : AbstractCharBoss.boss.powers) {
            p.onAfterCardPlayed(usedCard);
        }
    }
    
    public void removeCard(final AbstractCard c) {
        this.group.remove(c);
        if (this.type == CardGroupType.MASTER_DECK) {
            c.onRemoveFromMasterDeck();
        }
    }

    public void refreshHandLayout() {
        if (AbstractDungeon.getCurrRoom().monsters != null && AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
            return;
        }
        for (final AbstractOrb o : AbstractCharBoss.boss.orbs) {
            o.hideEvokeValues();
        }
        for (final AbstractRelic r : AbstractCharBoss.boss.relics) {
            r.onRefreshHand();
        }
        for (int i=0; i< this.group.size(); i++) {
        	AbstractCard c = this.group.get(i);
        	c.targetDrawScale = 0.75f;
        	c.target_x = Settings.WIDTH / 3.0f * 2.0f + (AbstractCard.IMG_WIDTH_S * 0.9f) * (i % 3);
        	c.target_y = Settings.HEIGHT / 2.0f + (AbstractCard.IMG_HEIGHT_S * 0.9f) * ((float)Math.floor(((float)i)/3.0f));
        }
    }
    
    public void glowCheck() {
        for (final AbstractCard c : this.group) {
            if (c.canUse(AbstractDungeon.player, AbstractCharBoss.boss) && AbstractDungeon.screen != AbstractDungeon.CurrentScreen.HAND_SELECT) {
                c.beginGlowing();
            }
            else {
                c.stopGlowing();
            }
            c.triggerOnGlowCheck();
        }
    }
}
