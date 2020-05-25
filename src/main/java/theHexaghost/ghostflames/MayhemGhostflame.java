package theHexaghost.ghostflames;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.PlayTopCardAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHexaghost.GhostflameHelper;
import theHexaghost.HexaMod;
import theHexaghost.util.TextureLoader;

public class MayhemGhostflame extends AbstractGhostflame {
    public static Texture bruh = TextureLoader.getTexture(HexaMod.makeUIPath("mayhem.png"));

    private String ID = "hexamod:MayhemGhostflame";
    private String NAME = CardCrawlGame.languagePack.getOrbString(ID).NAME;
    private String[] DESCRIPTIONS = CardCrawlGame.languagePack.getOrbString(ID).DESCRIPTION;

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
    public String getName(){ return NAME;}

    @Override
    public String getDescription() {
        String s = "";
        if (charged) {
            s = DESCRIPTIONS[0];
        }
        if (GhostflameHelper.activeGhostFlame == this) {
            s = s + DESCRIPTIONS[1];
        } else {
            s = s + DESCRIPTIONS[2];
        }
        s = s + DESCRIPTIONS[3];
        if (GhostflameHelper.activeGhostFlame == this) {
            s = s + DESCRIPTIONS[4];
        }
        return s;
    }
}
