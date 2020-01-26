package slimebound.actions;


import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import slimebound.orbs.CultistSlime;
import slimebound.orbs.SpawnedSlime;
import slimebound.vfx.SlimeIntentEffect;
import slimebound.vfx.SlimeIntentMovementEffect;


public class SlimeAutoCultistBuff extends AbstractGameAction {

    private static final float DURATION = 0.01F;
    private AbstractCreature owner;
    private static final float POST_ATTACK_WAIT_DUR = 0.2F;
    private int damage;
    private int debuffamount;
    private AbstractPower p;
    private AttackEffect AE;
    private SpawnedSlime slime;

    public SlimeAutoCultistBuff(Integer damage, SpawnedSlime o) {


        this.actionType = ActionType.POWER;
        this.duration = 0.01F;
        this.debuffamount=amount;
        this.slime=o;
        this.AE=AE;
        this.damage=damage;

    }

    public void update() {


        slime.applyUniqueFocus(this.damage);

        this.isDone = true;
    }
}

