package theHexaghost.ghostflames;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.combat.ScreenOnFireEffect;
import theHexaghost.GhostflameHelper;
import theHexaghost.actions.ExtinguishAction;
import theHexaghost.powers.EnhancePower;

public class InfernoGhostflame extends AbstractGhostflame {

    public int energySpentThisTurn = 0;

    public InfernoGhostflame(float x, float y) {
        super(x, y);
        damage = 6;
    }

    @Override
    public void onCharge() {
        atb(new AbstractGameAction() {
            @Override
            public void update() {
                isDone = true;
                int x = damage;
                if (AbstractDungeon.player.hasPower(EnhancePower.POWER_ID)) {
                    x += AbstractDungeon.player.getPower(EnhancePower.POWER_ID).amount;
                }
                AbstractDungeon.actionManager.addToBottom(new VFXAction(AbstractDungeon.player, new ScreenOnFireEffect(), 1.0F));
                for (AbstractGhostflame gf : GhostflameHelper.hexaGhostFlames) {
                    //tfw no gf
                    if (gf.charged) {
                        atb(new DamageRandomEnemyAction(new DamageInfo(AbstractDungeon.player, x, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
                        atb(new ExtinguishAction(gf));
                    }
                }
            }
        });
    }

    @Override
    public void extinguish() {
        super.extinguish();
        energySpentThisTurn = 0;
    }

    @Override
    public String getDescription() {
        String s = "";
        if (charged) {
            s = "#yCharged. ";
        }
        if (GhostflameHelper.activeGhostFlame == this) {
            int x = (3 - energySpentThisTurn);
            switch (x) {
                case 3:
                    s = s + "#yActive. Spend [E] [E] [E] this turn to Charge.";
                    break;
                case 2:
                    s = s + "#yActive. Spend [E] [E] this turn to Charge.";
                    break;
                case 1:
                    s = s + "#yActive. Spend [E] this turn to Charge.";
                    break;
                default:
                    s = s + "Error. Please report to mod dev";
            }
        } else {
            s = s + "Inactive. Spend [E] [E] [E] while Active to Charge.";
        }
        int x = damage;
        if (AbstractDungeon.player.hasPower(EnhancePower.POWER_ID)) {
            x += AbstractDungeon.player.getPower(EnhancePower.POWER_ID).amount;
        }
        return s + " NL When #yCharged, deal #b" + x + " damage to a random enemy for each #yCharged #yGhostflame, then #yExtinguish them.";
    }
}
