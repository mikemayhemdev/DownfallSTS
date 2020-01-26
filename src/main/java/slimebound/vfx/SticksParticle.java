package slimebound.vfx;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import slimebound.characters.SlimeboundCharacter;
import slimebound.orbs.CultistSlime;
import slimebound.orbs.SpawnedSlime;


public class SticksParticle extends com.megacrit.cardcrawl.vfx.AbstractGameEffect {
    private static final float DURATION = 0.75F;
    private static final float START_SCALE = 1.2F * Settings.scale;
    private float scale = 1F;
    private static int W;
    private Texture img;
    public CultistSlime p;
    private static int xOffset = 15;
    private static int yOffset = -35;

    public SticksParticle(CultistSlime p) {
        this.duration = 0.05F;
        this.img = ImageMaster.loadImage("slimeboundResources/SlimeboundImages/vfx/cultistleftstick.png");
        W = img.getWidth();
        this.p = p;
        this.renderBehind = false;


    }

    public void update() {


    }
    public void finish(){
        this.isDone = true;

    }
    public void dispose() {
        this.img.dispose();
        this.isDone = true;
    }

    public void render(SpriteBatch sb, float x, float y) {
    }


    public void render(SpriteBatch sb) {


        sb.setColor(new Color(1F, 1F, 1F, 2F));

        sb.draw(this.img, this.p.attachmentX + this.p.animX + this.p.cX - W / 2.0F + ((xOffset) * Settings.scale), this.p.attachmentY + p.animY + this.p.cY - W / 2.0F + ((yOffset) * Settings.scale), W / 2.0F, W / 2.0F, W, W, this.scale * Settings.scale, this.scale * Settings.scale, 0.0F, 0, 0, W, W, false, false);


    }
}


