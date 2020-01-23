package theHexaghost.ghostflames;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.combat.ScreenOnFireEffect;
import theHexaghost.GhostflameHelper;
import theHexaghost.HexaMod;
import theHexaghost.actions.ExtinguishAction;
import theHexaghost.powers.EnhancePower;
import theHexaghost.util.TextureLoader;

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
                for (AbstractGhostflame gf : GhostflameHelper.hexaGhostFlames) {
                    //tfw no gf
                    if (gf.charged) {
                        addToTop(new ExtinguishAction(gf));
                        addToTop(new DamageRandomEnemyAction(new DamageInfo(AbstractDungeon.player, x, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
                    }
                }
                AbstractDungeon.actionManager.addToTop(new VFXAction(AbstractDungeon.player, new ScreenOnFireEffect(), 1.0F));
            }
        });
    }

    @Override
    public String returnHoverHelperText() {
        if (charged) return "0";
        return String.valueOf(Math.max(0, 3 - energySpentThisTurn));
    }

    public static Texture bruh1 = TextureLoader.getTexture(HexaMod.makeUIPath("inferno1.png"));
    public static Texture bruh2 = TextureLoader.getTexture(HexaMod.makeUIPath("inferno2.png"));
    public static Texture bruh3 = TextureLoader.getTexture(HexaMod.makeUIPath("inferno3.png"));
    public static Texture bruh4 = TextureLoader.getTexture(HexaMod.makeUIPath("inferno4.png"));
    public static Texture bruh5 = TextureLoader.getTexture(HexaMod.makeUIPath("inferno5.png"));
    public static Texture bruh6 = TextureLoader.getTexture(HexaMod.makeUIPath("inferno6.png"));

    @Override
    public Texture getHelperTexture() {
        int i = 0;
        for (AbstractGhostflame gf : GhostflameHelper.hexaGhostFlames) {
            if (gf.charged)
                i++;
        }
        switch (i) {
            case 0:
                return bruh1;
            case 1:
                return bruh2;
            case 2:
                return bruh3;
            case 3:
                return bruh4;
            case 4:
                return bruh5;
            case 5:
                return bruh6;
            case 6:
                return bruh6;
        }
        return bruh1;
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
            s = "Charged. ";
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
                    s = s + "Error. Please report to mod dev: " + x;
            }
        } else {
            s = s + "Inactive. Spend [E] [E] [E] while #yActive to Charge.";
        }
        int x = damage;
        if (AbstractDungeon.player.hasPower(EnhancePower.POWER_ID)) {
            x += AbstractDungeon.player.getPower(EnhancePower.POWER_ID).amount;
        }
        return s + " NL When Charged, deal #b" + x + " damage to a random enemy for each #yCharged #yGhostflame, then #yExtinguish them.";
    }
}
