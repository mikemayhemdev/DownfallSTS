package slimebound.orbs;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import slimebound.SlimeboundMod;
import slimebound.actions.SlimeAutoAttack;
import slimebound.relics.ScrapOozeRelic;
import slimebound.vfx.ScrapGlowParticle;
import slimebound.vfx.ScrapParticle;
import slimebound.vfx.SlimeFlareEffect;


public class ScrapOozeSlime
        extends SpawnedSlime {
    public static final String ID = "Slimebound:ScrapOozeSlime";
    public static final String atlasString = SlimeboundMod.getResourcePath("orbs/scrap.atlas");
    public static final String skeletonString = "images/monsters/theBottom/slimeAltS/skeleton.json";

    public float attachmentX;
    public float attachmentY;

    public float attachmentGlowX;
    public float attachmentGlowY;

    public boolean evoked;

    private ScrapParticle scrapVFX;
    private ScrapGlowParticle scrapGlowVFX;


    public ScrapOozeSlime() {

        super(ID, new Color(1.0F, 140F / 255F, 140F / 255F, 100F), atlasString, skeletonString, false, true, 0, 3, true, new Color(.45F, .58F, .58F, 1), SlimeFlareEffect.OrbFlareColor.AGGRESSIVE, new Texture("slimeboundResources/SlimeboundImages/orbs/3.png"));
        spawnVFX();
        if (AbstractDungeon.player.hasRelic(ScrapOozeRelic.ID)) {
            applyUniqueFocus(AbstractDungeon.player.getRelic(ScrapOozeRelic.ID).counter);
        }

    }

    public void updateDescription() {
        this.description = this.descriptions[0] + this.passiveAmount + this.descriptions[1];
    }

    public void postSpawnEffects() {

        this.scrapGlowVFX = new ScrapGlowParticle(this, Color.YELLOW);
        this.scrapVFX = new ScrapParticle(this);
        AbstractDungeon.effectList.add(this.scrapVFX);
        // AbstractDungeon.effectList.add(this.scrapGlowVFX);
    }

    @Override
    public void triggerEvokeAnimation() {
        super.triggerEvokeAnimation();
        //SlimeboundMod.logger.info("Evoking Scrap Ooze");
        this.evoked = true;
        cleanUpVFX();
    }

    private void cleanUpVFX() {
        //SlimeboundMod.logger.info("Cleaning up Scrap Ooze VFX");
        this.scrapVFX.finish();
        this.scrapGlowVFX.finish();
        this.evoked = true;
    }

    public void activateEffectUnique() {


        AbstractDungeon.actionManager.addToBottom(new SlimeAutoAttack(AbstractDungeon.player, this.passiveAmount, AbstractGameAction.AttackEffect.SLASH_DIAGONAL, this, false, false, false, 0, false, 0, false));

    }


    @Override
    public void render(SpriteBatch sb) {
        super.render(sb);
        this.attachmentX = this.skeleton.findBone("eyeshadow").getX();
        this.attachmentY = this.skeleton.findBone("eyeshadow").getY();

    }

    public AbstractOrb makeCopy() {
        return new ScrapOozeSlime();
    }
}


