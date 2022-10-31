package theHexaghost.ghostflames;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.PlayTopCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHexaghost.GhostflameHelper;
import theHexaghost.HexaMod;
import downfall.util.TextureLoader;

public class MayhemGhostflame extends AbstractGhostflame {
    public static Texture bruh = TextureLoader.getTexture(HexaMod.makeUIPath("mayhem.png"));
    public static Texture bruhB = TextureLoader.getTexture(HexaMod.makeUIPath("mayhemBright.png"));
    public static Texture bruh2 = TextureLoader.getTexture(HexaMod.makeUIPath("card.png"));

    private String ID = "hexamod:MayhemGhostflame";
    private String NAME = CardCrawlGame.languagePack.getOrbString(ID).NAME;
    private String[] DESCRIPTIONS = CardCrawlGame.languagePack.getOrbString(ID).DESCRIPTION;

    private Color flameColor = new Color(164F/255F, 210F/255F, 249F/255F, 1F);
    private Color activeColor = new Color(164F/255F * 0.5F, 210F/255F * 0.5F, 249F/255F * 0.5F, 1F);

    public MayhemGhostflame(float x, float y) {
        super(x, y);
        //this.textColor = new Color(.5F,1F,1F,1F);
        this.triggersRequired = 1;

        this.effectIconXOffset = 80F;
        this.effectIconYOffset = -20F;

    }

    @Override
    public int getActiveFlamesTriggerCount() {
        return 1;
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
    public void advanceTrigger(AbstractCard c) {
        if (!charged){
            advanceTriggerAnim();
            charge();
        }
    }

    @Override
    public Texture getHelperTexture() {
        return bruh;
    }

    @Override
    public Texture getHelperTextureBright() {
        return bruhB;
    }

    @Override
    public Texture getHelperEffectTexture() {
        return bruh2;
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


    public Color getFlameColor() {
        return activeColor.cpy();
        //return Color.SKY.cpy();
    }

    public Color getActiveColor() {
        //return activeColor.cpy();
        return Color.PURPLE.cpy();
    }
}
