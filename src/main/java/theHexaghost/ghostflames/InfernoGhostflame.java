package theHexaghost.ghostflames;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.combat.ScreenOnFireEffect;
import theHexaghost.GhostflameHelper;
import theHexaghost.actions.ExtinguishAction;

public class InfernoGhostflame extends AbstractGhostflame {

    public int energySpentThisTurn = 0;

    public InfernoGhostflame(float x, float y) {
        super(x, y);
        damage = 6;
    }

    @Override
    public void onCharge() {
        AbstractDungeon.actionManager.addToBottom(new VFXAction(AbstractDungeon.player, new ScreenOnFireEffect(), 1.0F));
        for (AbstractGhostflame gf : GhostflameHelper.hexaGhostFlames) {
            //tfw no gf
            if (gf.charged) {
                atb(new DamageRandomEnemyAction(new DamageInfo(AbstractDungeon.player, damage, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
                atb(new ExtinguishAction(gf));
            }
        }
    }

    @Override
    public String getDescription() {
        String s;
        if (charged) {
            s = "#yCharged.";
        } else if (GhostflameHelper.activeGhostFlame == this) {
            int x = (3 - energySpentThisTurn);
            switch (x) {
                case 3:
                    s = "Active. Spend [E] [E] [E] this turn to Charge.";
                    break;
                case 2:
                    s = "Active. Spend [E] [E] this turn to Charge.";
                    break;
                case 1:
                    s = "Active. Spend [E] this turn to Charge.";
                    break;
                default:
                    s = "Error. Please report to mod dev";
            }
        } else {
            s = "Inactive. Spend [E] [E] [E] while Active to Charge.";
        }
        return s + " NL When #yCharged, deal #b" + damage + " damage to a random enemy for each #yCharged #yGhostflame, then #yExtinguish them.";
    }
}
