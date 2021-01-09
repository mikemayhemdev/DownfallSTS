package slimebound.actions;


import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import slimebound.powers.SlimedPower;
import slimebound.vfx.SlimeProjectileEffect;


public class TendrilFlailAction extends com.megacrit.cardcrawl.actions.AbstractGameAction {

    private static final float DURATION = 0.01F;
    private static final float POST_ATTACK_WAIT_DUR = 0.2F;
    private AbstractCreature owner;
    private int numTimes;
    private int slimed;

    public TendrilFlailAction(AbstractCreature player, AbstractCreature target, int numTimes, int slimed) {

        this.owner = player;
        this.target = target;
        this.actionType = ActionType.POWER;
        this.attackEffect = AttackEffect.POISON;
        this.duration = 0.01F;
        this.slimed = slimed;
        this.numTimes = numTimes;
    }

    public void update() {
        if (this.target == null) {
            target = AbstractDungeon.getRandomMonster();
        }

        if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
            AbstractDungeon.actionManager.clearPostCombatActions();
            this.isDone = true;
            return;
        }

        if (this.target != null && this.target.currentHealth > 0) {
            AbstractDungeon.actionManager.addToBottom(new VFXAction(new SlimeProjectileEffect(this.owner.hb.cX, this.owner.hb.cY, this.target.hb.cX, this.target.hb.cY, 2F, true, 0.6F), 0.00F));
            //AbstractDungeon.actionManager.addToBottom(new WaitAction(0.1F));

            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.target, this.owner, new SlimedPower(this.target, this.owner, slimed), slimed, true, AttackEffect.POISON));

            if ((this.numTimes > 1) && (!AbstractDungeon.getMonsters().areMonstersBasicallyDead())) {
                this.numTimes -= 1;
                AbstractDungeon.actionManager.addToTop(new TendrilFlailAction(this.owner,
                        AbstractDungeon.getMonsters().getRandomMonster(true), this.numTimes, this.slimed));
            }

            //AbstractDungeon.actionManager.addToTop(new com.megacrit.cardcrawl.actions.utility.WaitAction(0.2F));
        }

        this.isDone = true;
    }
}

