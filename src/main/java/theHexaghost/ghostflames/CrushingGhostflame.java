package theHexaghost.ghostflames;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.GoldenSlashEffect;
import theHexaghost.GhostflameHelper;

public class CrushingGhostflame extends AbstractGhostflame {

    public int skillsPlayedThisTurn = 0;

    public CrushingGhostflame(float x, float y) {
        super(x, y);
        damage = 5;
    }

    @Override
    public void onCharge() {
        for (int i = 0; i < 2; i++) {
            atb(new AbstractGameAction() {
                @Override
                public void update() {
                    isDone = true;
                    AbstractMonster m = AbstractDungeon.getRandomMonster();
                    atb(new VFXAction(new GoldenSlashEffect(m.hb.cX, m.hb.cY, true)));
                    atb(new DamageAction(m, new DamageInfo(AbstractDungeon.player, damage, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.NONE));
                }
            });
        }
    }

    @Override
    public String getDescription() {
        String s;
        if (charged) {
            s = "#yCharged.";
        } else if (GhostflameHelper.activeGhostFlame == this) {
            int x = (2 - skillsPlayedThisTurn);
            if (x == 1) {
                s = "Active. Play #b" + x + " #ySkill this turn to Charge.";
            } else {
                s = "Active. Play #b" + x + " #ySkills this turn to Charge.";
            }
        } else {
            s = "Inactive. Play #b2 #ySkills while #yActive to #yCharge.";
        }
        return s + " NL When Charged, deal #b" + damage + " damage to a random enemy twice.";
    }
}
