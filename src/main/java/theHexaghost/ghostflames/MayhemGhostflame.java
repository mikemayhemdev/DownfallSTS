package theHexaghost.ghostflames;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.PlayTopCardAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHexaghost.GhostflameHelper;
import theHexaghost.HexaMod;
import theHexaghost.util.TextureLoader;

public class MayhemGhostflame extends AbstractGhostflame {
    public static Texture bruh = TextureLoader.getTexture(HexaMod.makeUIPath("mayhem.png"));

    public MayhemGhostflame(float x, float y) {
        super(x, y);
    }

    @Override
    public void onCharge() {
        atb(new AbstractGameAction() {// 39
            public void update() {
                this.addToTop(new PlayTopCardAction(AbstractDungeon.getCurrRoom().monsters.getRandomMonster(null, true, AbstractDungeon.cardRandomRng), false));// 42 44
                this.isDone = true;// 49
            }// 50
        });
    }

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
            s = s + "#yActive. #yIgnites when you end your turn.";
        } else {
            s = s + "Inactive. #yIgnites when you end your turn while #yActive.";
        }
        s = s + " NL #yIgnition: When #yIgnited, play the top card of your draw pile.";
        if (GhostflameHelper.activeGhostFlame == this) {
            s = s + " NL NL At the end of your turn, #yAdvance to the next Ghostflame.";
        }
        return s;
    }
}
