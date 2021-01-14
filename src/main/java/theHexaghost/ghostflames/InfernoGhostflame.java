package theHexaghost.ghostflames;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.combat.ScreenOnFireEffect;
import theHexaghost.GhostflameHelper;
import theHexaghost.actions.AdvanceAction;
import theHexaghost.actions.ExtinguishAction;
import theHexaghost.powers.EnhancePower;
import downfall.util.TextureLoader;

import static theHexaghost.HexaMod.makeUIPath;

public class InfernoGhostflame extends AbstractGhostflame {

    public static Texture myTex = TextureLoader.getTexture(makeUIPath("inferno.png"));
    public static Texture myTexB = TextureLoader.getTexture(makeUIPath("infernoBright.png"));
    public static Texture bruh2 = TextureLoader.getTexture(makeUIPath("damage.png"));
    public int energySpentThisTurn = 0;

    private String ID = "hexamod:InfernoGhostflame";
    private String NAME = CardCrawlGame.languagePack.getOrbString(ID).NAME;
    private String[] DESCRIPTIONS = CardCrawlGame.languagePack.getOrbString(ID).DESCRIPTION;

    private Color flameColor = new Color(232F/255F, 164F/255F, 249F/255F, 1F);
    private Color activeColor = new Color(232F/255F * 0.5F, 164F/255F * 0.5F, 249F/255F * 0.5F, 1F);


    public InfernoGhostflame(float x, float y) {
        super(x, y);
        damage = 4;
        //this.textColor = new Color(1F,.75F,.75F,1F);
        this.triggersRequired = 3;

        this.effectIconXOffset = 80F;
        this.effectIconYOffset = -20F;

        this.advanceOnCardUse = true;
    }

    @Override
    public int getActiveFlamesTriggerCount() {
        return energySpentThisTurn;
    }

    @Override
    public void onCharge() {
        atb(new VFXAction(AbstractDungeon.player, new ScreenOnFireEffect(), 1.0F));
        int x = damage;
        if (AbstractDungeon.player.hasPower(EnhancePower.POWER_ID)) {
            x += AbstractDungeon.player.getPower(EnhancePower.POWER_ID).amount;
        }
        for (int j = GhostflameHelper.hexaGhostFlames.size() - 1; j >= 0; j--) {
            AbstractGhostflame gf = GhostflameHelper.hexaGhostFlames.get(j);
            if (gf.charged) {
                atb(new DamageRandomEnemyAction(new DamageInfo(AbstractDungeon.player, x, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
                //atb(new WaitAction(0.1F));  //Critical for keeping the UI not broken, and helps sell the anim
                atb(new ExtinguishAction(gf));
            }
        }
        if (GhostflameHelper.activeGhostFlame == this){
            atb(new AdvanceAction(false));
        }
        atb(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new EnhancePower(1), 1));
    }


    @Override
    public String returnHoverHelperText() {
        int x = getEffectCount();
        int chargedFlames = 0;
        for (int j = GhostflameHelper.hexaGhostFlames.size() - 1; j >= 0; j--) {
            AbstractGhostflame gf = GhostflameHelper.hexaGhostFlames.get(j);
            if (gf.charged) {
                chargedFlames++;
            }
        }
        if (charged) {
            return (x + "x" + chargedFlames);
        } else {
            return (x + "x" + (chargedFlames + 1));
        }
    }


    public int getEffectCount() {
        int x = damage;
        if (AbstractDungeon.player.hasPower(EnhancePower.POWER_ID)) {
            x += AbstractDungeon.player.getPower(EnhancePower.POWER_ID).amount;
        }
        return x;
    }

    @Override
    public void advanceTrigger(AbstractCard c) {
        if (!charged) {
            int x = c.costForTurn;
            if (c.freeToPlayOnce) x = 0;
            else if (c.cost == -1) x = c.energyOnUse;

            if (x > 0) {
                for (int i = 0; i < x; i++) {
                    advanceTriggerAnim();
                    energySpentThisTurn += 1;
                }
                if (energySpentThisTurn >= 3) {
                    charge();
                } else {
                    //activeGhostFlame.flash();
                }
            }
        }
    }

    @Override
    public Texture getHelperTexture() {
        return myTex;
    }

    @Override
    public Texture getHelperTextureBright() {
        return myTexB;
    }

    @Override
    public Texture getHelperEffectTexture() {
        return bruh2;
    }

    @Override
    public void reset() {
        energySpentThisTurn = 0;
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public String getDescription() {
        System.out.println(energySpentThisTurn);
        String s = "";
        if (charged) {
            s = DESCRIPTIONS[0];
        }
        if (GhostflameHelper.activeGhostFlame == this) {
            int x = (3 - energySpentThisTurn);
            switch (x) {
                case 3:
                    s = s + DESCRIPTIONS[1];
                    break;
                case 2:
                    s = s + DESCRIPTIONS[2];
                    break;
                case 1:
                    s = s + DESCRIPTIONS[3];
                    break;
                default:
                    s = s + DESCRIPTIONS[4] + x;
            }
        } else {
            s = s + DESCRIPTIONS[5];
        }
        int x = getEffectCount();
        s = s + DESCRIPTIONS[6] + x + DESCRIPTIONS[7];
      //  if (GhostflameHelper.activeGhostFlame == this) {
      //      s = s + DESCRIPTIONS[8];
     //   }
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
