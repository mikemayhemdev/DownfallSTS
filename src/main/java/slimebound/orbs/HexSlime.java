package slimebound.orbs;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.MathHelper;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.BobEffect;
import slimebound.actions.CheckForSixHexAction;
import slimebound.powers.PotencyPower;
import slimebound.vfx.SlimeFlareEffect;


public class HexSlime
        extends SpawnedSlime {
    public static final String ID = "Slimebound:HexSlime";


    private BobEffect effect = new BobEffect(2.0F);
    private float activateTimer;
    public boolean activated = false;
    public boolean hidden = false;
    public boolean playedSfx = false;
    private Color color;
    private float x;
    private float y;
    private float particleTimer = 0.0F;
    private static final float PARTICLE_INTERVAL = 0.06F;


    public HexSlime() {
        super(ID,-25,new Color (.65F,.65F,1.0F,100F),"images/monsters/theBottom/slimeM/skeleton.atlas","images/monsters/theBottom/slimeM/skeleton.json","idle",1.5F,new Color(119F/255F,119/255F,1F,2F), 0,0, false, new Color(.36F, .55F, .85F, 1), SlimeFlareEffect.OrbFlareColor.HEX, new Texture("slimeboundResources/SlimeboundImages/orbs/sleep.png"), "slimeboundResources/SlimeboundImages/orbs/hex.png");
        this.x = (x * Settings.scale + MathUtils.random(-10.0F, 10.0F) * Settings.scale);
        this.y = (y * Settings.scale + MathUtils.random(-10.0F, 10.0F) * Settings.scale);
        this.color = Color.CHARTREUSE.cpy();
        this.color.a = 0.0F;
        this.activated = true;
        spawnVFX();

    }


    public void updateDescription() {

        this.description = this.descriptions[0];
    }

    @Override
    public void applyFocus() {

    }



    public void activateEffectUnique() {


    }

    public void postSpawnEffects() {
        if (this instanceof HexSlime) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new StrengthPower(AbstractDungeon.player, 1), 1));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new DexterityPower(AbstractDungeon.player, 1), 1));

            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new PotencyPower(AbstractDungeon.player, AbstractDungeon.player, 2), 2));

        }
    }

    public void update() {
        super.update();

        this.activateTimer -= Gdx.graphics.getDeltaTime();

        if (this.activateTimer < 0.0F) {


            this.color.a = MathHelper.fadeLerpSnap(this.color.a, 1.0F);

            this.effect.update();

            this.effect.update();

            this.particleTimer -= Gdx.graphics.getDeltaTime();

            if (this.particleTimer < 0.0F) {

                AbstractDungeon.effectList.add(new com.megacrit.cardcrawl.vfx.GhostlyWeakFireEffect(this.cX, this.cY + 15F * Settings.scale));


                this.particleTimer = 0.06F;

            }

        } else {

            this.effect.update();

            this.particleTimer -= Gdx.graphics.getDeltaTime();

            if (this.particleTimer < 0.0F) {

                AbstractDungeon.effectList.add(new com.megacrit.cardcrawl.vfx.GhostlyWeakFireEffect(this.cX, this.cY + 20F * Settings.scale));


                this.particleTimer = 0.06F;

            }

        }
    }


    public AbstractOrb makeCopy() {
        return new HexSlime();
    }
}


