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
import slimebound.vfx.AntennaeParticle;
import slimebound.vfx.SlimeFlareEffect;
import slimebound.vfx.StopwatchParticle;


public class SlowingSlime
        extends SpawnedSlime {
    public static final String ID = "Slimebound:SlowingSlime";
    public static final String atlasString = SlimeboundMod.getResourcePath("orbs/slowing.atlas");
    public static final String skeletonString = "images/monsters/theBottom/slimeAltS/skeleton.json";

    public float attachmentX;
    public float attachmentY;

    public float attachmentXStopwatch;
    public float attachmentYStopwatch;


    private AntennaeParticle antennae;
    private StopwatchParticle stopwatch;

    public SlowingSlime() {

        super(ID, new Color(1.0F, 100F / 255F, 100F / 255F, 100F), atlasString, skeletonString, false, true, 4, 1, true, new Color(.45F, .58F, .58F, 1), SlimeFlareEffect.OrbFlareColor.AGGRESSIVE, new Texture("slimeboundResources/SlimeboundImages/orbs/attackDebuff.png"));
        this.debuffAmount = 1;
        this.extraFontColor = new Color(137F / 255F, 204F / 255F, 170F / 255F, 1F);
        spawnVFX();

    }

    public void postSpawnEffects() {
        this.antennae = new AntennaeParticle(this);
        this.stopwatch = new StopwatchParticle(this);

        if(CharacterSelectScreenPatches.characters[1].isOriginal()){
        AbstractDungeon.effectList.add(this.antennae);
        AbstractDungeon.effectList.add(this.stopwatch);
        }
    }

    public void updateDescription() {
        int cool = 0;
        int cool2 = 0;

        cool = this.passiveAmount;
        cool2 = this.debuffAmount;

        if (cool < 0) {
            cool = 0;
        }

        if (cool2 < 0) {
            cool2 = 0;
        }

        this.description = this.descriptions[0] + cool + this.descriptions[1] + cool2 + this.descriptions[2];
    }


    public void activateEffectUnique() {


        AbstractDungeon.actionManager.addToBottom(new SlimeAutoAttack(AbstractDungeon.player, this.passiveAmount, AbstractGameAction.AttackEffect.BLUNT_HEAVY, this, false, false, true, debuffAmount, false, 0, false, false, false, false, false));

    }


    @Override
    public void triggerEvokeAnimation() {
        super.triggerEvokeAnimation();
        cleanUpVFX();
    }

    public void cleanUpVFX() {
        if(CharacterSelectScreenPatches.characters[1].isOriginal()){
            if (this.stopwatch != null) {
                this.stopwatch.finish();
                this.antennae.finish();
            }
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        super.render(sb);
        this.attachmentX = this.skeleton.findBone("bone7").getX();
        this.attachmentY = this.skeleton.findBone("bone7").getY();

        this.attachmentXStopwatch = this.skeleton.findBone("bone4").getX();
        this.attachmentYStopwatch = this.skeleton.findBone("bone4").getY();
    }

    public AbstractOrb makeCopy() {
        return new SlowingSlime();
    }
}


