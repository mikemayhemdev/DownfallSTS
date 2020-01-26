package slimebound.orbs;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import slimebound.actions.SlimeAutoAttack;
import slimebound.relics.ScrapOozeRelic;
import slimebound.vfx.ScrapGlowParticle;
import slimebound.vfx.ScrapParticle;
import slimebound.vfx.SlimeFlareEffect;


public class ScrapOozeSlime
        extends SpawnedSlime {
    public static final String ID = "Slimebound:ScrapOozeSlime";

    public float attachmentX;
    public float attachmentY;

    public float attachmentGlowX;
    public float attachmentGlowY;

    private ScrapParticle scrapVFX;
    private ScrapGlowParticle scrapGlowVFX;


    public ScrapOozeSlime() {

        super(ID, -17,new Color (1.0F,140F/255F,140F/255F,100F),"images/monsters/theBottom/slimeAltS/skeleton.atlas","images/monsters/theBottom/slimeAltS/skeleton.json","idle",.85F,new Color(0.8F,0.4F,0.4F,2F), 0, 3, true, new Color(.45F, .58F, .58F, 1), SlimeFlareEffect.OrbFlareColor.AGGRESSIVE, new Texture("slimeboundResources/SlimeboundImages/orbs/3.png"), "slimeboundResources/SlimeboundImages/orbs/aggressive.png");
        spawnVFX();
        if (AbstractDungeon.player.hasRelic(ScrapOozeRelic.ID)){
            applyUniqueFocus(AbstractDungeon.player.getRelic(ScrapOozeRelic.ID).counter);
        }

    }

    public void updateDescription() {
        this.description = this.descriptions[0] + this.passiveAmount + this.descriptions[1];
    }

    public void postSpawnEffects(){

        this.scrapGlowVFX = new ScrapGlowParticle(this, Color.YELLOW);
        this.scrapVFX = new ScrapParticle(this);
        AbstractDungeon.effectList.add(this.scrapVFX);
       // AbstractDungeon.effectList.add(this.scrapGlowVFX);
    }

    @Override
    public void onEvoke() {
        super.onEvoke();
        cleanUpVFX();
    }
    public void cleanUpVFX() {

        this.scrapVFX.finish();
        this.scrapGlowVFX.finish();
    }

    public void activateEffectUnique() {


        AbstractDungeon.actionManager.addToBottom(new SlimeAutoAttack(AbstractDungeon.player,this.passiveAmount, AbstractGameAction.AttackEffect.SLASH_DIAGONAL,this,false,false,false,0,false,0,false));

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


