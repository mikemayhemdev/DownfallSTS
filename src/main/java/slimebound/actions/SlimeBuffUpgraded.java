package slimebound.actions;


import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import slimebound.orbs.CultistSlime;
import slimebound.orbs.SpawnedSlime;


public class SlimeBuffUpgraded extends AbstractGameAction {

    private static final float DURATION = 0.01F;
    private AbstractCreature owner;
    private static final float POST_ATTACK_WAIT_DUR = 0.2F;
    private int amount;
    private SpawnedSlime slime;

    public SlimeBuffUpgraded(Integer amount, SpawnedSlime o) {

    this.amount=amount;
    this.slime=o;

    }

    public void update() {


        slime.applyUniqueFocus(amount);

        this.isDone = true;
    }
}

