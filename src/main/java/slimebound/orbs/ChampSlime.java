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
import slimebound.vfx.CrownParticle;
import slimebound.vfx.SlimeFlareEffect;


public class ChampSlime
        extends SpawnedSlime {
    public static final String ID = "Slimebound:ChampSlime";
    public static final String atlasString = SlimeboundMod.getResourcePath("orbs/champ.atlas");
    public static final String skeletonString = "images/monsters/theBottom/slimeAltM/skeleton.json";


    public float attachmentX;
    public float attachmentY;

    private CrownParticle crownVFX;

    public ChampSlime() {

        super(ID, new Color(1.0F, 100F / 255F, 100F / 255F, 100F), atlasString, skeletonString, true, true, 4, 0, true, new Color(.45F, .58F, .58F, 1), SlimeFlareEffect.OrbFlareColor.AGGRESSIVE, new Texture("slimeboundResources/SlimeboundImages/orbs/attackDebuff.png"));
        this.debuffAmount = 1;
        this.extraFontColor = new Color(204F / 255F, 98F / 255F, 114F / 255F, 1F);
        spawnVFX();

    }

    public void postSpawnEffects() {
        this.crownVFX = new CrownParticle(this);
        if(!CharacterSelectScreenPatches.characters[1].reskinAnimation){
        AbstractDungeon.effectList.add(this.crownVFX);
        }
    }


    public void updateDescription() {
        this.description = this.descriptions[0] + this.passiveAmount + this.descriptions[1] + this.debuffAmount + this.descriptions[2];
    }


    public void activateEffectUnique() {


        AbstractDungeon.actionManager.addToBottom(new SlimeAutoAttack(AbstractDungeon.player, this.passiveAmount, AbstractGameAction.AttackEffect.BLUNT_HEAVY, this, false, false, false, 1, false, 0, false, false, true, false, false));

    }

    @Override
    public void triggerEvokeAnimation() {
        super.triggerEvokeAnimation();
        cleanUpVFX();
    }

    public void cleanUpVFX() {
        if(!CharacterSelectScreenPatches.characters[1].reskinAnimation)
        this.crownVFX.finish();
    }

    @Override
    public void render(SpriteBatch sb) {
        super.render(sb);
        this.attachmentX = this.skeleton.findBone("bone8").getX();
        this.attachmentY = this.skeleton.findBone("bone8").getY();
    }

    public AbstractOrb makeCopy() {
        return new ChampSlime();
    }
}


