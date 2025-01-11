package expansioncontent.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.GetAllInBattleInstances;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.Sozu;

import java.util.Iterator;
import java.util.UUID;

public class BloodthirstAction extends AbstractGameAction {
    private DamageInfo info;
    private static final float DURATION = 0.1F;
    private UUID uuid;

    public BloodthirstAction(AbstractCreature target, DamageInfo info, UUID targetUUID) {
        this.info = info;
        this.setValues(target, info);
        this.actionType = ActionType.DAMAGE;
        this.duration = DURATION;
        this.uuid = targetUUID;
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
                        // c.applyPowers();
                        addToBot((AbstractGameAction)new ExhaustSpecificCardAction(c, AbstractDungeon.player.discardPile, false));
                    }
                }
                for (var1 = GetAllInBattleInstances.get(this.uuid).iterator(); var1.hasNext();) {
                    AbstractCard c = var1.next();
                    //c.applyPowers();
                    addToBot((AbstractGameAction)new ExhaustSpecificCardAction(c, AbstractDungeon.player.discardPile, false));
                }
            AbstractRelic sozu = AbstractDungeon.player.getRelic(Sozu.ID);
                if (sozu != null) {
                    sozu.flash();
                } else {
                    AbstractDungeon.player.obtainPotion(AbstractDungeon.returnRandomPotion(true));
                }
            }

            if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
                AbstractDungeon.actionManager.clearPostCombatActions();
            }
        }
        this.tickDuration();
    }

}