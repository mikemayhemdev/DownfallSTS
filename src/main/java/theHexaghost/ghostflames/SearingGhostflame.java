package theHexaghost.ghostflames;

import com.badlogic.gdx.graphics.Texture;
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

    public static Texture bruh = TextureLoader.getTexture(HexaMod.makeUIPath("crushing.png"));
    public int attacksPlayedThisTurn = 0;

    public SearingGhostflame(float x, float y) {
        super(x, y);
        magic = 3;
    }

    @Override
    public void onCharge() {
        int x = magic;
        if (AbstractDungeon.player.hasPower(EnhancePower.POWER_ID)) {
            x += AbstractDungeon.player.getPower(EnhancePower.POWER_ID).amount;
        }
        AbstractMonster m = AbstractDungeon.getRandomMonster();
        if (!m.isDead && !m.isDying && !m.halfDead) {
            atb(new VFXAction(new FireballEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY, m.hb.cX, m.hb.cY), 0.5F));// 173
            atb(new BurnAction(m, x));
        }
    }

    @Override
    public Texture getHelperTexture() {
        return bruh;
    }

    @Override
    public String returnHoverHelperText() {
        if (charged) return "0";
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
            s = "#yIgnited. ";
        }
        if (GhostflameHelper.activeGhostFlame == this) {
            int x = (2 - attacksPlayedThisTurn);
            if (x == 1) {
                s = s + "#yActive. #yIgnites when #b" + x + " #yAttack is played this turn.";
            } else {
                s = s + "#yActive. #yIgnites when #b" + x + " #yAttacks are played this turn.";
            }
        } else {
            s = s + "Inactive. #yIgnites when #b2 #yAttacks are played while #yActive.";
        }
        int x = magic;
        if (AbstractDungeon.player.hasPower(EnhancePower.POWER_ID)) {
            x += AbstractDungeon.player.getPower(EnhancePower.POWER_ID).amount;
        }
        s = s + " NL #yIgnition: When #yIgnited, apply #b" + x + " #yBurn to a random enemy.";
        if (GhostflameHelper.activeGhostFlame == this) {
            s = s + " NL NL At the end of your turn, #yAdvance to the next Ghostflame.";
        }
        return s;

    }
}
