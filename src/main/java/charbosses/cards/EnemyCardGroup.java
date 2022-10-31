package charbosses.cards;

import charbosses.bosses.AbstractCharBoss;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.cardManip.ExhaustCardEffect;

import java.util.ArrayList;

public class EnemyCardGroup extends CardGroup {
    public static final int HAND_ROW_LENGTH = 10;
    public static final float HAND_HEIGHT_OFFSET = 0.56F;
    public AbstractCharBoss owner;
	public static AbstractBossCard hov2holder = null;

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
        //c.darken(false);
        System.out.printf(c.name + " DARKEN-CANCELED");
        //this.owner.discardPile.addToTop(c);
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
        //this.owner.exhaustPile.addToTop(c);
        this.owner.onCardDrawOrDiscard();
    }

    public void moveToHand(final AbstractCard c, final CardGroup group) {
        c.unhover();
        c.lighten(true);
        c.setAngle(0.0f);
        c.drawScale = 0.12f;
        c.targetDrawScale = AbstractBossCard.HAND_SCALE;
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
        c.targetDrawScale = AbstractBossCard.HAND_SCALE;
        c.current_x = CardGroup.DRAW_PILE_X;
        c.current_y = CardGroup.DRAW_PILE_Y;
        this.owner.hand.addToTop(c);
        this.owner.hand.refreshHandLayout();
        this.owner.hand.applyPowers();
    }

    public void moveToDeck(final AbstractCard c, final boolean randomSpot) {
        this.resetCardBeforeMoving(c);
        /*
        if (randomSpot)
            this.owner.drawPile.addToRandomSpot(c);
        else {
            //SlimeboundMod.logger.info("adding card to top");
            this.owner.drawPile.addToTop(c);
        }
        */
    }

    public void moveToBottomOfDeck(final AbstractCard c) {
        this.resetCardBeforeMoving(c);
        //this.owner.drawPile.addToBottom(c);
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
            } else if (c.inBottleFlame || c.inBottleLightning || c.inBottleTornado) {
                placeOnTop.add(c);
            } else {
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
            //AbstractDungeon.actionManager.addToTurnStart(new EnemyDrawCardAction(AbstractCharBoss.boss, placeOnTop.size() - AbstractCharBoss.boss.masterHandSize));
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
        AbstractCard hoveredcard = null;
        for (int i = 0; i < this.group.size(); i++) {
            AbstractCard c = this.group.get(i);
			if (EnemyCardGroup.hov2holder != c) {
				c.targetDrawScale = AbstractBossCard.HAND_SCALE;
			}
            int cardsinrow = Math.min(this.group.size() - HAND_ROW_LENGTH * (int) Math.floor((float) i / (float) HAND_ROW_LENGTH), HAND_ROW_LENGTH);
            float widthspacing = AbstractCard.IMG_WIDTH_S + 100.0f * Settings.scale;
            c.target_x = Settings.WIDTH * .9F - ((cardsinrow + 0.5f) * (widthspacing * AbstractBossCard.HAND_SCALE)) + (widthspacing * AbstractBossCard.HAND_SCALE) * (i % HAND_ROW_LENGTH);
            c.target_y = Settings.HEIGHT * HAND_HEIGHT_OFFSET + (AbstractCard.IMG_HEIGHT_S * AbstractBossCard.HAND_SCALE) * ((float) Math.floor(((float) i) / (float) HAND_ROW_LENGTH) + (this.group.size() > HAND_ROW_LENGTH ? 0.0f : 1.0f));
            if (((AbstractBossCard) c).hov2 && c.hb.hovered) {
				if (hoveredcard == null || EnemyCardGroup.hov2holder == c) {
					hoveredcard = c;
				}
            }
        }
        if (hoveredcard != null) {
            this.hoverCardPush(hoveredcard);
        }
    }

    public void glowCheck() {
        for (final AbstractCard c : this.group) {
            if (c.canUse(AbstractDungeon.player, AbstractCharBoss.boss) && AbstractDungeon.screen != AbstractDungeon.CurrentScreen.HAND_SELECT) {
                c.beginGlowing();
            } else {
                c.stopGlowing();
            }
            c.triggerOnGlowCheck();
        }
    }

    public int getCardNumber(AbstractCard c) {
        for (int i = 0; i < this.group.size(); ++i) {
            if (c.equals(this.group.get(i))) {
                return i;
            }
        }
        return -1;
    }

    public ArrayList<AbstractCard> getCardRow(AbstractCard c) {
        int cardNum = getCardNumber(c);
        ArrayList<AbstractCard> cardrow = new ArrayList<AbstractCard>();
        for (int i = HAND_ROW_LENGTH * (int) Math.floor((float) cardNum / (float) HAND_ROW_LENGTH); i < this.group.size() && i < HAND_ROW_LENGTH * (1 + (int) Math.floor((float) i / (float) HAND_ROW_LENGTH)); i++) {
            cardrow.add(this.group.get(i));
        }
        return cardrow;
    }

    public void hoverCardPush(final AbstractCard c) {
        int cardNum = getCardNumber(c) % HAND_ROW_LENGTH;
        ArrayList<AbstractCard> cardrow = getCardRow(c);
        if (cardrow.size() > 1) {
            float pushAmt = 0.4f;
            if (cardrow.size() == 2) {
                pushAmt = 0.2f;
            } else if (cardrow.size() == 3 || cardrow.size() == 4) {
                pushAmt = 0.27f;
            }
            pushAmt *= AbstractBossCard.HAND_SCALE / 0.75f;
            for (int currentSlot = cardNum + 1; currentSlot < cardrow.size(); ++currentSlot) {
                final AbstractCard abstractCard = cardrow.get(currentSlot);
                abstractCard.target_x += AbstractCard.IMG_WIDTH_S * pushAmt;
                pushAmt *= 0.25f;
            }
            pushAmt = 0.4f;
            if (cardrow.size() == 2) {
                pushAmt = 0.2f;
            } else if (cardrow.size() == 3 || cardrow.size() == 4) {
                pushAmt = 0.27f;
            }
            pushAmt *= AbstractBossCard.HAND_SCALE / 0.75f;
            for (int currentSlot = cardNum - 1; currentSlot > -1 && currentSlot < cardrow.size(); --currentSlot) {
                final AbstractCard abstractCard2 = cardrow.get(currentSlot);
                abstractCard2.target_x -= AbstractCard.IMG_WIDTH_S * pushAmt;
                pushAmt *= 0.25f;
            }
        }
    }

    public AbstractBossCard getHighestValueCard() {
        AbstractBossCard r = null;
        int record = -99;
        for (AbstractCard c : this.group) {
            AbstractBossCard cc = (AbstractBossCard) c;
            if (cc.getValue() > record) {
                r = cc;
                record = cc.getValue();
            }
        }
        return r;
    }

    public AbstractBossCard getHighestValueCard(AbstractCard.CardType type) {
        AbstractBossCard r = null;
        int record = -99;
        for (AbstractCard c : this.group) {
            if (c.type == type) {
                AbstractBossCard cc = (AbstractBossCard) c;
                if (cc.getValue() > record) {
                    r = cc;
                    record = cc.getValue();
                }
            }
        }
        return r;
    }

    public AbstractBossCard getHighestUpgradeValueCard() {
        AbstractBossCard r = null;
        int record = -99;
        for (AbstractCard c : this.group) {
            AbstractBossCard cc = (AbstractBossCard) c;
            if (cc.getUpgradeValue() > record) {
                r = cc;
                record = cc.getValue();
            }
        }
        return r;
    }
}
