package slimebound.vfx;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import slimebound.orbs.GreedOozeSlime;
import slimebound.orbs.ScrapOozeSlime;

import java.util.Set;


public class GoldCoinsParticle extends com.megacrit.cardcrawl.vfx.AbstractGameEffect {

    private float scale = 1.2F;
    private static int W;
    private Texture img;
    public GreedOozeSlime p;
    private static float xOffset = 15F * Settings.scale;
    private static float yOffset = -37F * Settings.scale;

    public GoldCoinsParticle(GreedOozeSlime p) {
        this.duration = 0.05F;
        this.img = ImageMaster.loadImage("slimeboundResources/SlimeboundImages/vfx/coins.png");
        W = img.getWidth();
        this.p = p;
        this.renderBehind = false;


    }
    public void finish(){
        this.isDone = true;

    }

    public void update() {


    }
    public void dispose() {
        this.img.dispose();
        this.isDone = true;
    }

    public void render(SpriteBatch sb, float x, float y) {
    }


    public void render(SpriteBatch sb) {


        sb.setColor(new Color(.8F, .8F, .8F, 2F));

        sb.draw(this.img, this.p.attachmentX + p.animX + this.p.cX - W / 2.0F + xOffset, this.p.attachmentY + this.p.animY + this.p.cY - W / 2.0F + yOffset, W / 2.0F, W / 2.0F, W, W, this.scale * Settings.scale, this.scale * Settings.scale, 0.0F, 0, 0, W, W, false, false);



    }
}


