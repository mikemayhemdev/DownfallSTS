package slimebound.orbs;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.MathHelper;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.vfx.BobEffect;
import slimebound.SlimeboundMod;
import slimebound.actions.SlimeAutoAttack;
import slimebound.vfx.SlimeFlareEffect;


public class HexSlime
        extends SpawnedSlime {
    public static final String ID = "Slimebound:HexSlime";
    public static final String atlasString = SlimeboundMod.getResourcePath("orbs/hex.atlas");
    public static final String skeletonString = "images/monsters/theBottom/slimeM/skeleton.json";
    private static final float PARTICLE_INTERVAL = 0.06F;
    public boolean activated = false;
    public boolean hidden = false;
    public boolean playedSfx = false;
    private BobEffect effect = new BobEffect(2.0F);
    private float activateTimer;
    private Color color;
    private float x;
    private float y;
    private float particleTimer = 0.0F;


    public HexSlime() {
        super(ID, new Color(.65F, .65F, 1.0F, 100F), atlasString, skeletonString, true, false, 3, 0, false, new Color(.36F, .55F, .85F, 1), SlimeFlareEffect.OrbFlareColor.HEX, new Texture("slimeboundResources/SlimeboundImages/orbs/attackDebuff.png"));
        this.x = (x * Settings.scale + (5F + MathUtils.random(-10.0F, 10.0F) * Settings.scale));
        this.y = (y * Settings.scale + (-30F + MathUtils.random(-10.0F, 10.0F) * Settings.scale));
        this.color = Color.CHARTREUSE.cpy();
        this.extraFontColor = new Color(.5F, 1F, .5F, 1F);
        this.color.a = 0.0F;
        this.activated = true;
        this.debuffAmount = 3;
        spawnVFX();

    }


    public void updateDescription() {

        this.description = this.descriptions[0] + this.passiveAmount + this.descriptions[1] + 3 + this.descriptions[2];
    }


    public void activateEffectUnique() {
        AbstractDungeon.actionManager.addToBottom(new SlimeAutoAttack(AbstractDungeon.player, this.passiveAmount, AbstractGameAction.AttackEffect.FIRE, this, false, false, false, 3, false, 0, false, false, false, true, true));


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

                AbstractDungeon.effectList.add(new com.megacrit.cardcrawl.vfx.GhostlyWeakFireEffect(this.cX + (5F * Settings.scale), this.cY + (50F * Settings.scale)));


                this.particleTimer = 0.06F;

            }

        } else {

            this.effect.update();

            this.particleTimer -= Gdx.graphics.getDeltaTime();

            if (this.particleTimer < 0.0F) {

                AbstractDungeon.effectList.add(new com.megacrit.cardcrawl.vfx.GhostlyWeakFireEffect(this.cX + (5F * Settings.scale), this.cY + (50F * Settings.scale)));


                this.particleTimer = 0.06F;

            }

        }
    }


    public AbstractOrb makeCopy() {
        return new HexSlime();
    }
}


