package theHexaghost.ghostflames;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.combat.InflameEffect;
import theHexaghost.GhostflameHelper;
import theHexaghost.HexaMod;
import theHexaghost.powers.EnhancePower;
import downfall.util.TextureLoader;

import static theHexaghost.GhostflameHelper.activeGhostFlame;

public class BolsteringGhostflame extends AbstractGhostflame {
    public static Texture bruh = TextureLoader.getTexture(HexaMod.makeUIPath("bolster.png"));
    public static Texture bruhB = TextureLoader.getTexture(HexaMod.makeUIPath("bolsterBright.png"));
    public static Texture bruh2 = TextureLoader.getTexture(HexaMod.makeUIPath("block.png"));

    private String ID = "hexamod:BolsteringGhostflame";
    private String NAME = CardCrawlGame.languagePack.getOrbString(ID).NAME;
    private String[] DESCRIPTIONS = CardCrawlGame.languagePack.getOrbString(ID).DESCRIPTION;


    public BolsteringGhostflame(float x, float y) {
        super(x, y);
        block = 4;

        //this.textColor = new Color(.75F,.75F,1F,1F);
        this.triggersRequired = 1;

        this.effectIconXOffset = 60F;
        this.effectIconYOffset = -20F;

        this.advanceOnCardUse = true;
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
    public Texture getHelperTextureBright() {
        return bruhB;
    }

    @Override
    public int getActiveFlamesTriggerCount(){
        if (charged) {
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public void advanceTrigger(AbstractCard c) {
        if (!charged && c.type == AbstractCard.CardType.POWER){
            advanceTriggerAnim();
            charge();
        }
    }

    @Override
    public Texture getHelperEffectTexture() {
        return bruh2;
    }


    @Override
    public String returnHoverHelperText() {
        int x = getEffectCount();
        return x + "";
    }

    public int getEffectCount(){
        int x = block;
        if (AbstractDungeon.player.hasPower(EnhancePower.POWER_ID)) {
            x += AbstractDungeon.player.getPower(EnhancePower.POWER_ID).amount;
        }
        return x;
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
        int x = getEffectCount();
        s = s + DESCRIPTIONS[3] + x + DESCRIPTIONS[4];
        if (GhostflameHelper.activeGhostFlame == this) {
            s = s + DESCRIPTIONS[5];
        }
        return s;
    }

    public Color getFlameColor() {
        return Color.SKY.cpy();
        //return Color.SKY.cpy();
    }

    public Color getActiveColor() {
        return Color.PURPLE.cpy();
        //return Color.PURPLE.cpy();
    }
}
