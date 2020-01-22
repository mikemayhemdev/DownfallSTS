package theHexaghost.ghostflames;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.FireballEffect;
import theHexaghost.GhostflameHelper;
import theHexaghost.HexaMod;
import theHexaghost.actions.BurnAction;
import theHexaghost.powers.EnhancePower;
import theHexaghost.util.TextureLoader;

public class SearingGhostflame extends AbstractGhostflame {

    public int attacksPlayedThisTurn = 0;

    public SearingGhostflame(float x, float y) {
        super(x, y);
        magic = 3;
    }

    @Override
    public void onCharge() {
        atb(new AbstractGameAction() {
            @Override
            public void update() {
                isDone = true;
                int x = magic;
                if (AbstractDungeon.player.hasPower(EnhancePower.POWER_ID)) {
                    x += AbstractDungeon.player.getPower(EnhancePower.POWER_ID).amount;
                }
                for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
                    if (!m.isDead && !m.isDying) {
                        atb(new VFXAction(new FireballEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY, m.hb.cX, m.hb.cY), 0.5F));// 173
                        atb(new BurnAction(m, x));
                    }
                }
            }
        });
    }

    public static Texture bruh = TextureLoader.getTexture(HexaMod.makeUIPath("searing.png"));

    @Override
    public Texture getHelperTexture() {
        return bruh;
    }

    @Override
    public String returnHoverHelperText() {
        return String.valueOf(2 - attacksPlayedThisTurn);
    }

    @Override
    public void reset() {
        attacksPlayedThisTurn = 0;
    }

    @Override
    public String getDescription() {
        String s = "";
        if (charged) {
            s = "Charged. ";
        }
        if (GhostflameHelper.activeGhostFlame == this) {
            int x = (2 - attacksPlayedThisTurn);
            if (x == 1) {
                s = s + "#yActive. Play #b" + x + " #yAttack this turn to Charge.";
            } else {
                s = s + "#yActive. Play #b" + x + " #yAttacks this turn to Charge.";
            }
        } else {
            s = s + "Inactive. Play #b2 #yAttacks while #yActive to Charge.";
        }
        int x = magic;
        if (AbstractDungeon.player.hasPower(EnhancePower.POWER_ID)) {
            x += AbstractDungeon.player.getPower(EnhancePower.POWER_ID).amount;
        }
        return s + " NL When Charged, apply #b" + x + " #yBurn to ALL enemies.";
    }
}
