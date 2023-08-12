package collector.powers.collectioncards;

import collector.powers.AbstractCollectorPower;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.combat.LightningEffect;

import static collector.util.Wiz.atb;

public class TransientCardPower extends AbstractCollectorPower {
    public static final String NAME = "TransientCard";
    public static final String POWER_ID = makeID(NAME);
    public static final PowerType TYPE = PowerType.DEBUFF;
    public static final boolean TURN_BASED = false;

    public TransientCardPower() {
        super(NAME, TYPE, TURN_BASED, AbstractDungeon.player, null, 6);
        this.loadRegion("fading");
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        if (amount == 1) {
            this.addToBot(new VFXAction(new LightningEffect(this.owner.hb.cX, this.owner.hb.cY)));
            this.addToBot(new LoseHPAction(this.owner, this.owner, 99999));
        } else {
            atb(new ReducePowerAction(owner, owner, this, 1));
        }
    }
}