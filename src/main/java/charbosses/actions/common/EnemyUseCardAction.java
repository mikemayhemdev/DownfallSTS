package charbosses.actions.common;

import charbosses.actions.utility.EnemyHandCheckAction;
import charbosses.actions.utility.EnemyShowCardAction;
import charbosses.actions.utility.EnemyShowCardAndPoofAction;
import charbosses.bosses.AbstractCharBoss;
import charbosses.powers.cardpowers.EnemyReboundPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.AbstractPower.PowerType;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import slimebound.SlimeboundMod;

public class EnemyUseCardAction extends AbstractGameAction {
    private static final float DUR = 0.15f;
    public AbstractCreature target;
    public boolean exhaustCard;
    public boolean returnToHand;
    public boolean reboundCard;
    private AbstractCard targetCard;

    public EnemyUseCardAction(final AbstractCard card, final AbstractCreature target) {
        this.target = null;
        this.reboundCard = false;
        this.targetCard = card;
        this.target = target;
        if (card.exhaustOnUseOnce || card.exhaust) {
            this.exhaustCard = true;
        }
        this.setValues(AbstractCharBoss.boss, null, 1);
        this.duration = 0.15f;
        for (final AbstractPower p : AbstractCharBoss.boss.powers) {
            if (!card.dontTriggerOnUseCard && p.type != PowerType.DEBUFF) {
                p.onUseCard(card, this.makeNormalCardAction());
            }
        }
        for (final AbstractRelic r : AbstractCharBoss.boss.relics) {
            if (!card.dontTriggerOnUseCard) {
                r.onUseCard(card, this.makeNormalCardAction());
            }
        }
        for (final AbstractCard c : AbstractCharBoss.boss.hand.group) {
            if (!card.dontTriggerOnUseCard) {
                c.triggerOnCardPlayed(card);
            }
        }
        if (this.exhaustCard) {
            this.actionType = ActionType.EXHAUST;
        } else {
            this.actionType = ActionType.USE;
        }
    }

    public EnemyUseCardAction(final AbstractCard targetCard) {
        this(targetCard, null);
    }

    public UseCardAction makeNormalCardAction() {
        AbstractCard cc = this.targetCard.makeStatEquivalentCopy();
        cc.dontTriggerOnUseCard = true;
        return new UseCardAction(cc, AbstractCharBoss.boss);
    }

    @Override
    public void update() {
        if (this.duration == 0.15f) {
            //SlimeboundMod.logger.info("using card" + this.reboundCard);
            if(AbstractCharBoss.boss != null){
            for (final AbstractPower p : AbstractCharBoss.boss.powers) {
                if (!this.targetCard.dontTriggerOnUseCard && p.type != PowerType.DEBUFF) {
                    //SlimeboundMod.logger.info(p);
                    if (p instanceof EnemyReboundPower){
                        //SlimeboundMod.logger.info("detected rebound power");
                        EnemyReboundPower eP = (EnemyReboundPower)p;
                        eP.onAfterUse(this.targetCard, this);
                    }

                    //SlimeboundMod.logger.info("using normal on after use");
                    p.onAfterUseCard(this.targetCard, this.makeNormalCardAction());
                }
            }

            this.targetCard.freeToPlayOnce = false;
            this.targetCard.isInAutoplay = false;
            if (this.targetCard.purgeOnUse) {
                this.addToTop(new EnemyShowCardAndPoofAction(this.targetCard));
                this.isDone = true;
                AbstractCharBoss.boss.cardInUse = null;
                return;
            }
            if (this.targetCard.type == AbstractCard.CardType.POWER) {
                this.addToTop(new EnemyShowCardAction(this.targetCard));
                if (Settings.FAST_MODE) {
                    this.addToTop(new WaitAction(0.1f));
                } else {
                    this.addToTop(new WaitAction(0.7f));
                }
                AbstractCharBoss.boss.hand.empower(this.targetCard);
                this.isDone = true;
                AbstractCharBoss.boss.hand.applyPowers();
                AbstractCharBoss.boss.hand.glowCheck();
                AbstractCharBoss.boss.cardInUse = null;
                return;
            }
            AbstractCharBoss.boss.cardInUse = null;
            boolean spoonProc = false;
            if (this.exhaustCard && AbstractCharBoss.boss.hasRelic("Strange Spoon") && this.targetCard.type != AbstractCard.CardType.POWER) {
                spoonProc = AbstractDungeon.cardRandomRng.randomBoolean();
            }
            //SlimeboundMod.logger.info("before spoon check");
            //SlimeboundMod.logger.info("using card" + this.reboundCard);
            if (!this.exhaustCard || spoonProc) {
                if (spoonProc) {
                    AbstractCharBoss.boss.getRelic("Strange Spoon").flash();
                }
                if (this.reboundCard) {

                    //SlimeboundMod.logger.info("detected rebound card");
                    AbstractCharBoss.boss.hand.moveToDeck(this.targetCard, false);
                } else if (this.targetCard.shuffleBackIntoDrawPile) {
                    AbstractCharBoss.boss.hand.moveToDeck(this.targetCard, true);
                } else if (this.targetCard.returnToHand) {
                    AbstractCharBoss.boss.hand.moveToHand(this.targetCard);
                    AbstractCharBoss.boss.onCardDrawOrDiscard();
                } else {
                    AbstractCharBoss.boss.hand.moveToDiscardPile(this.targetCard);
                }
            } else {
                AbstractCharBoss.boss.hand.moveToExhaustPile(this.targetCard);
            }
            this.targetCard.exhaustOnUseOnce = false;
            this.targetCard.dontTriggerOnUseCard = false;
            this.addToBot(new EnemyHandCheckAction());
        }
        }
        this.tickDuration();
    }
}
