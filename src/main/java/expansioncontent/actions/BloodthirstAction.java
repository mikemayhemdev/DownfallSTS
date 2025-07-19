package expansioncontent.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.GetAllInBattleInstances;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.potions.PowerPotion;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.Sozu;
import guardian.orbs.StasisOrb;

import java.util.Iterator;
import java.util.UUID;

public class BloodthirstAction extends AbstractGameAction {
    private DamageInfo info;
    private static final float DURATION = 0.1F;
    private UUID uuid;
    private boolean potion;

    public BloodthirstAction(AbstractCreature target, DamageInfo info, UUID targetUUID, boolean powerpotion) {
        this.info = info;
        this.setValues(target, info);
        this.actionType = ActionType.DAMAGE;
        this.duration = DURATION;
        this.uuid = targetUUID;
        potion = powerpotion;
    }

    @Override
    public void update() {
        if (this.duration == DURATION && this.target != null) {
            this.target.damage(this.info);

            if ((((AbstractMonster) this.target).isDying || this.target.currentHealth <= 0)
                    && !this.target.halfDead
                    && !this.target.hasPower("Minion")) {

                Iterator<AbstractCard> var1 = AbstractDungeon.player.masterDeck.group.iterator();
                while (var1.hasNext()) {
                    AbstractCard c = var1.next();
                    if (c.uuid.equals(this.uuid)) {
                        addToBot((AbstractGameAction)new ExhaustSpecificCardAction(c, AbstractDungeon.player.hand, true));
                        addToBot((AbstractGameAction)new ExhaustSpecificCardAction(c, AbstractDungeon.player.discardPile, false));
                        addToBot((AbstractGameAction)new ExhaustSpecificCardAction(c, AbstractDungeon.player.limbo, true));
                    }
                }
                for (var1 = GetAllInBattleInstances.get(this.uuid).iterator(); var1.hasNext();) {
                    AbstractCard c = var1.next();
                    addToBot((AbstractGameAction)new ExhaustSpecificCardAction(c, AbstractDungeon.player.hand, true));
                    addToBot((AbstractGameAction)new ExhaustSpecificCardAction(c, AbstractDungeon.player.discardPile, false));
                    addToBot((AbstractGameAction)new ExhaustSpecificCardAction(c, AbstractDungeon.player.limbo, true));
                }

                for (AbstractOrb o : AbstractDungeon.player.orbs) {
                    if (o instanceof StasisOrb) {
                        if (((StasisOrb) o).stasisCard.uuid == this.uuid) {
                            AbstractDungeon.player.orbs.remove(o);
                        }
                    }
                }

            AbstractRelic sozu = AbstractDungeon.player.getRelic(Sozu.ID);
                if (sozu != null) {
                    sozu.flash();
                } else {
                    if (!potion) {
                        AbstractDungeon.player.obtainPotion(AbstractDungeon.returnRandomPotion(true));
                    }
                if (potion) {
                    AbstractPotion potion = new PowerPotion();
                    AbstractDungeon.player.obtainPotion(potion.makeCopy());
                    }
                }
            }

            if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
                AbstractDungeon.actionManager.clearPostCombatActions();
            }
        }
        this.tickDuration();
    }

}