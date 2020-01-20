package theHexaghost.ghostflames;

import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.combat.InflameEffect;
import theHexaghost.GhostflameHelper;

public class BolsteringGhostflame extends AbstractGhostflame {
    public BolsteringGhostflame(float x, float y) {
        super(x, y);
        block = 6;
    }

    @Override
    public void onCharge() {
        atb(new VFXAction(AbstractDungeon.player, new InflameEffect(AbstractDungeon.player), 0.5F));// 194
        atb(new GainBlockAction(AbstractDungeon.player, block));
        atb(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new StrengthPower(AbstractDungeon.player, 1), 1));
    }

    @Override
    public String getDescription() {
        String s;
        if (charged) {
            s = "#yCharged.";
        } else if (GhostflameHelper.activeGhostFlame == this) {
            s = "Active. Play a #yPower this turn to Charge.";
        } else {
            s = "Inactive. Play a #yPower while Active to Charge.";
        }
        return s + " NL When Charged, gain #b" + block + " #yBlock and #b1 #yStrength.";
    }
}
