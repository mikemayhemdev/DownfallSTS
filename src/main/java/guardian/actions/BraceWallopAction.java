package guardian.actions;

import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.AddTemporaryHPAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import static guardian.cards.AbstractGuardianCard.brace;


//thorton code
public class BraceWallopAction extends AbstractGameAction {
    private int damage;

    public BraceWallopAction(AbstractCreature target, AbstractCreature source, int amount, DamageInfo.DamageType type, AbstractGameAction.AttackEffect effect) {
        setValues(target, source, amount);
        this.damage = amount;
        this.actionType = AbstractGameAction.ActionType.DAMAGE;
        this.damageType = type;
        this.attackEffect = effect;
    }

    public void update() {
        if (this.duration == 0.5F)
            AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.target.hb.cX, this.target.hb.cY, this.attackEffect));
        tickDuration();
        if (this.isDone) {
            braceattack();
            this.target.damage(new DamageInfo(this.source, this.damage, this.damageType));
            if (AbstractDungeon.getMonsters().areMonstersBasicallyDead())
                AbstractDungeon.actionManager.clearPostCombatActions();
            AbstractDungeon.actionManager.addToTop((AbstractGameAction) new WaitAction(0.1F));
        }
    }

    private void braceattack() {
        int tmp = this.damage;
        tmp -= this.target.currentBlock;
        if (tmp > this.target.currentHealth)
            tmp = this.target.currentHealth;
        if (tmp > 0)
            brace(tmp);
    }
}