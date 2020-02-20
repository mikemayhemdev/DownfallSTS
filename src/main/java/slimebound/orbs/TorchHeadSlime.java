package slimebound.orbs;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.vfx.TorchHeadFireEffect;
import slimebound.SlimeboundMod;
import slimebound.actions.SlimeAutoAttack;
import slimebound.vfx.SlimeFlareEffect;


public class TorchHeadSlime
        extends SpawnedSlime {
    public static final String ID = "Slimebound:TorchHeadSlime";
    public static final String atlasString = SlimeboundMod.getResourcePath("orbs/torchhead.atlas");
    public static final String skeletonString = "images/monsters/theBottom/slimeAltM/skeleton.json";
    private static final float FIRE_TIME = 0.04F;
    private float fireTimer = 0.0F;
    private boolean fireOn = false;

    public TorchHeadSlime() {

        super(ID, new Color(1.0F, 1.0F, 1.0F, 100F), atlasString, skeletonString, true, true, 6, 0, true, new Color(.65F, .65F, .57F, 1), SlimeFlareEffect.OrbFlareColor.TORCHHEAD, new Texture("slimeboundResources/SlimeboundImages/orbs/5.png"));
        spawnVFX();
    }

    public void postSpawnEffects() {
        fireOn = true;
    }

    public void updateDescription() {
        this.description = this.descriptions[0] + this.passiveAmount + this.descriptions[1];
    }


    public void activateEffectUnique() {

        AbstractDungeon.actionManager.addToBottom(new SlimeAutoAttack(AbstractDungeon.player, this.passiveAmount, AbstractGameAction.AttackEffect.BLUNT_LIGHT, this, false, false, false, 0, false, 0, false));


    }


    public AbstractOrb makeCopy() {

        return new TorchHeadSlime();

    }


    public void update() {
        super.update();

        if (fireOn) {
            this.fireTimer -= Gdx.graphics.getDeltaTime();
            if (this.fireTimer < 0.0F) {
                this.fireTimer = 0.04F;
                AbstractDungeon.effectList.add(new TorchHeadFireEffect(this.cX, this.cY + 15));

            }
        }

    }
}


