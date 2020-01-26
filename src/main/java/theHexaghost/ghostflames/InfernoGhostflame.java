package theHexaghost.ghostflames;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.combat.ScreenOnFireEffect;
import theHexaghost.GhostflameHelper;
import theHexaghost.actions.ExtinguishAction;
import theHexaghost.powers.EnhancePower;
import theHexaghost.util.TextureLoader;

import static theHexaghost.HexaMod.makeUIPath;

public class InfernoGhostflame extends AbstractGhostflame {

    public int energySpentThisTurn = 0;

    public InfernoGhostflame(float x, float y) {
        super(x, y);
        damage = 6;
    }

    @Override
    public void onCharge() {
        int x = damage;
        if (AbstractDungeon.player.hasPower(EnhancePower.POWER_ID)) {
            x += AbstractDungeon.player.getPower(EnhancePower.POWER_ID).amount;
        }
        for (int j = GhostflameHelper.hexaGhostFlames.size() - 1; j >= 0; j--) {
            AbstractGhostflame gf = GhostflameHelper.hexaGhostFlames.get(j);
            if (gf.charged) {
                att(new ExtinguishAction(gf));
                att(new DamageRandomEnemyAction(new DamageInfo(AbstractDungeon.player, x, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
            }
        }
        AbstractDungeon.actionManager.addToTop(new VFXAction(AbstractDungeon.player, new ScreenOnFireEffect(), 1.0F));
    }

    @Override
    public String returnHoverHelperText() {
        if (charged) return "0";
        return String.valueOf(Math.max(0, 3 - energySpentThisTurn));
    }

    public static Texture myTex = TextureLoader.getTexture(makeUIPath("inferno.png"));

    @Override
    public Texture getHelperTexture() {
        return myTex;
    }

    @Override
    public void reset() {
        energySpentThisTurn = 0;
    }

    @Override
    public String getDescription() {
        System.out.println(energySpentThisTurn);
        String s = "";
        if (charged) {
            s = "Ignited. ";
        }
        if (GhostflameHelper.activeGhostFlame == this) {
            int x = (3 - energySpentThisTurn);
            switch (x) {
                case 3:
                    s = s + "#yActive. Ignites after spending [E] [E] [E] this turn. NL At the end of your turn, #yAdvance to the next Ghostflame.";
                    break;
                case 2:
                    s = s + "#yActive. Ignites after spending [E] [E] this turn. NL At the end of your turn, #yAdvance to the next Ghostflame.";
                    break;
                case 1:
                    s = s + "#yActive. Ignites after spending [E] this turn. NL At the end of your turn, #yAdvance to the next Ghostflame.";
                    break;
                default:
                    s = s + "Error. Please report to mod dev: " + x;
            }
        } else {
            s = s + "Inactive. Ignites after spending [E] [E] [E] while #yActive.";
        }
        int x = damage;
        if (AbstractDungeon.player.hasPower(EnhancePower.POWER_ID)) {
            x += AbstractDungeon.player.getPower(EnhancePower.POWER_ID).amount;
        }
        return s + " NL #yIgnition: When #yIgnited, deal #b" + x + " damage to a random enemy for each #yIgnited #yGhostflame, then #yExtinguish them.";
    }
}
