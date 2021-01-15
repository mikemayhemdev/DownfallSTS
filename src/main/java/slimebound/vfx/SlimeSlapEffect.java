package slimebound.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;

public class SlimeSlapEffect extends com.megacrit.cardcrawl.vfx.AbstractGameEffect {
    private static final int W = 64;
    private static Texture img = null;
    private float x;
    private float y;
    private int frame = 0;
    private float animTimer = 0.15F;

    private float scaleX;
    private boolean flipX;

    public SlimeSlapEffect(float x, float y) {
        this.x = x;
        this.y = y;

        this.img = ImageMaster.loadImage("slimeboundResources/SlimeboundImages/vfx/slap.png");

        this.frame = 0;
        this.scale = 1F;
        this.rotation = 0.0F;
        this.scale *= Settings.scale;
        this.color = Color.WHITE.cpy();
        scaleX = 1;
        flipX = true;
    }

    public void dispose() {
        this.isDone = true;

    }

    public void update() {
        this.animTimer -= Gdx.graphics.getDeltaTime();

        if (frame == 0){
            scale = Interpolation.linear.apply(2F, 0F, animTimer / 0.2F);
            this.color.a = com.megacrit.cardcrawl.helpers.MathHelper.fadeLerpSnap(this.color.a, 1.0F);
        }
        else if (frame == 2){
            scaleX = Interpolation.linear.apply(1F, 0F, animTimer / 0.1F);
        }
        else if (frame == 3){
            scaleX = Interpolation.linear.apply(0F, 1F, animTimer / 0.15F);
        }
        else if (frame == 5){
            scale = Interpolation.linear.apply(0F, 2F, animTimer / 0.2F);
            this.color.a = com.megacrit.cardcrawl.helpers.MathHelper.fadeLerpSnap(this.color.a, 0F);
        }

        if (this.animTimer < 0.0F) {
            if (frame == 0){
                frame = 1;
                animTimer += 0.2F;  //Pause before Slap
            }
            else if (frame == 1){
                frame = 2;
                animTimer += 0.1F;  //Slap start
            }
            else if (frame == 2){
                frame = 3;
                animTimer += 0.15F;  //Slap midway
                flipX = false;  //Midway through slap, flip the mirror so it renders the other way
            }
            else if (frame == 3){
                frame = 4;
                animTimer += 0.2F;  //Pause before Fade
            }
            //TODO - Something about this doesn't line up, the hand snaps back to ScaleX = 0 or close to it.
            else if (frame == 4){
                frame = 5;
                animTimer += 0.2F;  //Fade
            }
            else if (frame == 5){
                isDone = true;
            }

        }
    }

    public void render(SpriteBatch sb) {
        sb.setColor(this.color);
        sb.draw(img, this.x, this.y, 42, 42, 84, 84, this.scale * scaleX, this.scale, this.rotation, 0, 0, 84, 84, flipX, false);

    }
}


