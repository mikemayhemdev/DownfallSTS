package theHexaghost.ghostflames;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.combat.InflameEffect;
import theHexaghost.GhostflameHelper;
import theHexaghost.HexaMod;
import theHexaghost.powers.EnhancePower;
import theHexaghost.util.TextureLoader;

public class BolsteringGhostflame extends AbstractGhostflame {
    public static Texture bruh = TextureLoader.getTexture(HexaMod.makeUIPath("bolster.png"));

    public BolsteringGhostflame(float x, float y) {
        super(x, y);
        block = 6;
    }

    @Override
    public void onCharge() {
        int x = block;
        if (AbstractDungeon.player.hasPower(EnhancePower.POWER_ID)) {
            x += AbstractDungeon.player.getPower(EnhancePower.POWER_ID).amount;
        }
        atb(new VFXAction(AbstractDungeon.player, new InflameEffect(AbstractDungeon.player), 0.5F));// 194
        atb(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new StrengthPower(AbstractDungeon.player, 1), 1));
        atb(new GainBlockAction(AbstractDungeon.player, x));
    }

    @Override
    public Texture getHelperTexture() {
        return bruh;
    }

    @Override
    public String returnHoverHelperText() {
        if (charged)
            return "0";
        return "1";
    }

    @Override
    public String getDescription() {
        String s = "";
        if (charged) {
            s = "#yIgnited. ";
        }
        if (GhostflameHelper.activeGhostFlame == this) {
            s = s + "#yActive. #yIgnites when a #yPower is played this turn.";
        } else {
            s = s + "Inactive. #yIgnites when a #yPower is played while #yActive.";
        }
        int x = block;
        if (AbstractDungeon.player.hasPower(EnhancePower.POWER_ID)) {
            x += AbstractDungeon.player.getPower(EnhancePower.POWER_ID).amount;
        }
        s = s + " NL #yIgnition: When #yIgnited, gain #b" + x + " #yBlock and #b1 #yStrength.";
        if (GhostflameHelper.activeGhostFlame == this) {
            s = s + " NL NL At the end of your turn, #yAdvance to the next Ghostflame.";
        }
        return s;
    }
}
