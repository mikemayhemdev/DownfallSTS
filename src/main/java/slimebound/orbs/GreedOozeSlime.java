package slimebound.orbs;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import slimebound.actions.SlimeAutoAttack;
import slimebound.relics.GreedOozeRelic;
import slimebound.vfx.GoldCoinsParticle;
import slimebound.vfx.GreedGlowParticle;
import slimebound.vfx.SlimeFlareEffect;


public class GreedOozeSlime
        extends SpawnedSlime {
    public static final String ID = "Slimebound:GreedOozeSlime";

    public float attachmentX;
    public float attachmentY;
    public Boolean stopShiny = false;
    private GoldCoinsParticle goldcoinsVFX;
    private float shinyTimer;


    public GreedOozeSlime() {

        super(ID, -36, new Color(1F, 1F, 0F / 255F, 100F), "images/monsters/theBottom/slimeS/skeleton.atlas", "images/monsters/theBottom/slimeS/skeleton.json", "idle", .85F, new Color(1F, 1F, 30F / 255F, 2F), 0, 3, true, new Color(1F, 1F, 30F / 255F, 1), SlimeFlareEffect.OrbFlareColor.AGGRESSIVE, new Texture("slimeboundResources/SlimeboundImages/orbs/4.png"), "slimeboundResources/SlimeboundImages/orbs/aggressive.png");
        spawnVFX();
        if (AbstractDungeon.player.hasRelic(GreedOozeRelic.ID)) {
            applyUniqueFocus(AbstractDungeon.player.getRelic(GreedOozeRelic.ID).counter);
        }

    }

    public void updateDescription() {
        this.description = this.descriptions[0] + this.passiveAmount + this.descriptions[1];
    }


    public void postSpawnEffects() {
        this.goldcoinsVFX = new GoldCoinsParticle(this);
        AbstractDungeon.effectList.add(this.goldcoinsVFX);
    }

    @Override
    public void onEvoke() {
        super.onEvoke();
        cleanUpVFX();
    }

    public void cleanUpVFX() {
        this.goldcoinsVFX.finish();

    }

    public void activateEffectUnique() {


        AbstractDungeon.actionManager.addToBottom(new SlimeAutoAttack(AbstractDungeon.player, this.passiveAmount, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL, this, false, false, false, 0, false, 0, false));

    }

    @Override
    public void render(SpriteBatch sb) {
        super.render(sb);
        this.attachmentX = (this.skeleton.findBone("eyeback").getX()) * Settings.scale;
        this.attachmentY = (this.skeleton.findBone("eyeback").getY()) * Settings.scale;


        if (!this.stopShiny) updateShiny();
    }

    private void updateShiny() {

        this.shinyTimer -= Gdx.graphics.getDeltaTime();
        if (this.shinyTimer < 0.0F && !Settings.DISABLE_EFFECTS) {
            this.shinyTimer = 0.2F;

            AbstractDungeon.topLevelEffects.add(new GreedGlowParticle(this));

        }

    }

    public AbstractOrb makeCopy() {
        return new GreedOozeSlime();
    }
}


