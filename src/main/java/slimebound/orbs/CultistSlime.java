package slimebound.orbs;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import reskinContent.patches.CharacterSelectScreenPatches;
import reskinContent.reskinContent;
import slimebound.SlimeboundMod;
import slimebound.actions.SlimeAutoAttack;
import slimebound.vfx.SlimeFlareEffect;
import slimebound.vfx.SticksParticle;
import slimebound.vfx.SticksParticleRight;


public class CultistSlime
        extends SpawnedSlime {
    public static final String ID = "Slimebound:CultistSlime";
    public static final String atlasString = SlimeboundMod.getResourcePath("orbs/cultist.atlas");
    public static final String skeletonString = "images/monsters/theBottom/slimeM/skeleton.json";

    public float attachmentX;
    public float attachmentY;

    public float attachmentXr;
    public float attachmentYr;

    private SticksParticle sticksLeftVFX;
    private SticksParticleRight sticksRightVFX;

    public CultistSlime() {
        super(ID, new Color(.55F, .55F, 1.0F, 100F), atlasString, skeletonString, true, false, 6, 0, true, new Color(.4F, .45F, .63F, 1), SlimeFlareEffect.OrbFlareColor.CULTIST, new Texture("slimeboundResources/SlimeboundImages/orbs/attackBuff.png"));
        spawnVFX();


    }

    public void postSpawnEffects() {
        this.sticksLeftVFX = new SticksParticle(this);
        this.sticksRightVFX = new SticksParticleRight(this);

        if(!CharacterSelectScreenPatches.characters[1].isOriginal()){
            AbstractDungeon.effectList.add(this.sticksLeftVFX);
            AbstractDungeon.effectList.add(this.sticksRightVFX);
        }
    }


    public void updateDescription() {
        this.description = this.descriptions[0] + this.passiveAmount + this.descriptions[1];
    }


    public void activateEffectUnique() {

        AbstractDungeon.actionManager.addToBottom(new SlimeAutoAttack(AbstractDungeon.player, this.passiveAmount, AbstractGameAction.AttackEffect.BLUNT_LIGHT, this, false, false, false, 0, false, 0, true));


    }


    @Override
    public void triggerEvokeAnimation() {
        super.triggerEvokeAnimation();
        cleanUpVFX();
    }

    public void cleanUpVFX() {
        if(CharacterSelectScreenPatches.characters[1].isOriginal()){
        this.sticksLeftVFX.finish();
        this.sticksRightVFX.finish();
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        super.render(sb);
        this.attachmentX = this.skeleton.findBone("eyeback1").getX();
        this.attachmentY = this.skeleton.findBone("eyeback1").getY();

        this.attachmentXr = this.skeleton.findBone("eyeback4").getX();
        this.attachmentYr = this.skeleton.findBone("eyeback4").getY();
    }

    public AbstractOrb makeCopy() {
        return new CultistSlime();
    }
}


