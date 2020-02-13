package theHexaghost.ghostflames;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.GoldenSlashEffect;
import theHexaghost.GhostflameHelper;
import theHexaghost.HexaMod;
import theHexaghost.powers.EnhancePower;
import theHexaghost.util.TextureLoader;

public class CrushingGhostflame extends AbstractGhostflame {

    public static Texture bruh = TextureLoader.getTexture(HexaMod.makeUIPath("searing.png"));
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
                    int x = damage;
                    if (AbstractDungeon.player.hasPower(EnhancePower.POWER_ID)) {
                        x += AbstractDungeon.player.getPower(EnhancePower.POWER_ID).amount;
                    }
                    isDone = true;
                    AbstractMonster m = AbstractDungeon.getRandomMonster();
                    addToTop(new DamageAction(m, new DamageInfo(AbstractDungeon.player, x, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.NONE));
                    addToTop(new VFXAction(new GoldenSlashEffect(m.hb.cX, m.hb.cY, true)));
                }
            });
        }
    }

    @Override
    public String returnHoverHelperText() {
        if (charged) return "0";
        return String.valueOf(2 - skillsPlayedThisTurn);
    }

    @Override
    public Texture getHelperTexture() {
        return bruh;
    }

    @Override
    public void reset() {
        skillsPlayedThisTurn = 0;
    }

    @Override
    public String getDescription() {
        String s = "";
        if (charged) {
            s = "#yIgnited. ";
        }
        if (GhostflameHelper.activeGhostFlame == this) {
            int x = (2 - skillsPlayedThisTurn);
            if (x == 1) {
                s = s + "#yActive. #yIgnites after #b" + x + " #ySkill is played this turn.";
            } else {
                s = s + "#yActive. #yIgnites after #b" + x + " #ySkills are played this turn.";
            }
        } else {
            s = s + "Inactive. #yIgnites after #b2 #ySkills are played while #yActive.";
        }
        int x = damage;
        if (AbstractDungeon.player.hasPower(EnhancePower.POWER_ID)) {
            x += AbstractDungeon.player.getPower(EnhancePower.POWER_ID).amount;
        }
        s = s + " NL #yIgnition: When #yIgnited, deal #b" + x + " damage to a random enemy twice.";
        if (GhostflameHelper.activeGhostFlame == this) {
            s = s + " NL NL At the end of your turn, #yAdvance to the next Ghostflame.";
        }
        return s;
    }
}
