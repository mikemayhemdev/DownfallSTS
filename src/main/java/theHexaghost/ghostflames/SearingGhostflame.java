package theHexaghost.ghostflames;

import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.FireballEffect;
import theHexaghost.GhostflameHelper;
import theHexaghost.powers.BurnPower;

public class SearingGhostflame extends AbstractGhostflame {

    public int attacksPlayedThisTurn = 0;

    public SearingGhostflame(float x, float y) {
        super(x, y);
        magic = 3;
    }

    @Override
    public void onCharge() {
        for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (!m.isDead && !m.isDying) {
                atb(new VFXAction(new FireballEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY, m.hb.cX, m.hb.cY), 0.5F));// 173
                atb(new ApplyPowerAction(m, AbstractDungeon.player, new BurnPower(m, magic), magic));
            }
        }
    }

    @Override
    public String getDescription() {
        String s;
        if (charged) {
            s = "#yCharged.";
        } else if (GhostflameHelper.activeGhostFlame == this) {
            int x = (2 - attacksPlayedThisTurn);
            if (x == 1) {
                s = "Active. Play #b" + x + " #yAttack this turn to #yCharge.";
            } else {
                s = "Active. Play #b" + x + " #yAttacks this turn to #yCharge.";
            }
        } else {
            s = "Inactive. Play #b2 #yAttacks while #yActive to #yCharge.";
        }
        return s + " NL When #yCharged, apply #b" + magic + " #yBurn to ALL enemies.";
    }
}
