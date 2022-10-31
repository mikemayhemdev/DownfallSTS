package hermit.actions;


import com.megacrit.cardcrawl.actions.AbstractGameAction;
        import com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType;
        import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
        import com.megacrit.cardcrawl.actions.utility.WaitAction;
        import com.megacrit.cardcrawl.cards.DamageInfo;
        import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
        import com.megacrit.cardcrawl.core.AbstractCreature;
        import com.megacrit.cardcrawl.core.Settings;
        import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
        import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

public class FastLoseHPAction extends AbstractGameAction {
    private static final float DURATION = 0.33F;

    public FastLoseHPAction(AbstractCreature target, AbstractCreature source, int amount) {
        this(target, source, amount, AttackEffect.NONE);
    }

    public FastLoseHPAction(AbstractCreature target, AbstractCreature source, int amount, AttackEffect effect) {
        this.setValues(target, source, amount);
        this.actionType = ActionType.DAMAGE;
        this.attackEffect = effect;
        this.duration = 0.1F;
    }

    public void update() {
        if (this.duration == 0.1F && this.target.currentHealth > 0) {
            AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.target.hb.cX, this.target.hb.cY, this.attackEffect));
        }

        this.tickDuration();
        if (this.isDone) {
            this.target.damage(new DamageInfo(this.source, this.amount, DamageInfo.DamageType.HP_LOSS));
            if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
                AbstractDungeon.actionManager.clearPostCombatActions();
            }

            if (!Settings.FAST_MODE) {
                this.addToTop(new WaitAction(0.1F));
            }
        }

    }
}