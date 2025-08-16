package hermit.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.AbstractPower.PowerType;

public class ReduceDebuffsAction extends AbstractGameAction {
    private final AbstractCreature c;

    public ReduceDebuffsAction(AbstractCreature c, int amount) {
        this.c = c;
        this.duration = 0.5F;
        this.amount=amount;
    }

    // Jacked from Packmaster.
    public void update() {
        if (this.amount <= 0) {
            this.isDone = true;
            return;
        }

        for (AbstractPower p : this.c.powers) {
            if (p.type == PowerType.DEBUFF) {
                // ReducePowerAction only works properly for powers with positive amount, so we use our own action to
                // handle the various debuffs that have negative amount (e.g. Wraith Form, negative Dexterity). This also
                // handles debuffs that don't stack (e.g. NoDrawPower from Battle Trance), since those use amount -1.
                if (p.amount > 0) {
                    this.addToTop(new ReducePowerAction(this.c, this.c, p.ID, this.amount));
                }
                else if (p.amount < 0 && Math.abs(p.amount) <= this.amount) {
                    this.addToTop(new RemoveSpecificPowerAction(this.c, this.c, p));
                }
                else {
                    this.addToTop(new AbstractGameAction() {
                        @Override
                        public void update() {
                            p.stackPower(ReduceDebuffsAction.this.amount);
                            p.updateDescription();
                            AbstractDungeon.onModifyPower();
                            this.isDone = true;
                        }
                    });
                }
            }
        }

        this.isDone = true;
    }
}