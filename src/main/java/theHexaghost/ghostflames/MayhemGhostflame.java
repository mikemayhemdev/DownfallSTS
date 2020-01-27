package theHexaghost.ghostflames;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.PlayTopCardAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.combat.InflameEffect;
import theHexaghost.GhostflameHelper;
import theHexaghost.HexaMod;
import theHexaghost.powers.EnhancePower;
import theHexaghost.util.TextureLoader;

public class MayhemGhostflame extends AbstractGhostflame {
    public MayhemGhostflame(float x, float y) {
        super(x, y);
    }

    @Override
    public void onCharge() {
        atb(new AbstractGameAction() {// 39
            public void update() {
                this.addToTop(new PlayTopCardAction(AbstractDungeon.getCurrRoom().monsters.getRandomMonster((AbstractMonster)null, true, AbstractDungeon.cardRandomRng), false));// 42 44
                this.isDone = true;// 49
            }// 50
        });
    }

    public static Texture bruh = TextureLoader.getTexture(HexaMod.makeUIPath("mayhem.png"));

    @Override
    public Texture getHelperTexture() {
        return bruh;
    }

    @Override
    public String returnHoverHelperText() {
        return "";
    }

    @Override
    public String getDescription() {
        String s = "";
        if (charged) {
            s = "#yIgnited. ";
        }
        if (GhostflameHelper.activeGhostFlame == this) {
            s = s + "#yActive. #yIgnites when you end your turn. NL At the end of your turn, #yAdvance to the next Ghostflame.";
        } else {
            s = s + "Inactive. #yIgnites when you end your turn while #yActive.";
        }
        return s + " NL #yIgnition: When #yIgnited, play the top card of your draw pile.";
    }
}
