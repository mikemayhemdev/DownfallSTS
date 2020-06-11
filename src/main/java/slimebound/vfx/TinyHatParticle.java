package slimebound.vfx;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import reskinContent.reskinContent;
import slimebound.characters.SlimeboundCharacter;


public class TinyHatParticle extends com.megacrit.cardcrawl.vfx.AbstractGameEffect {
    private static final float DURATION = 0.75F;
    private static final float START_SCALE = 1.2F * com.megacrit.cardcrawl.core.Settings.scale;
    private static int W;
    private static int H;
    private static int xOffset = 45;
    private static int yOffset = 85;
    public SlimeboundCharacter p;
    private float scale = .75F;
    private Texture img;
    private static float duplicatedScale = 1.0f;

    public TinyHatParticle(AbstractPlayer p) {
        this.duration = 0.05F;
        if(reskinContent.slimeOriginalAnimation){
            this.img = ImageMaster.loadImage("slimeboundResources/SlimeboundImages/relics/tinybowlerhatinverted.png");
            W = img.getWidth();
            H = img.getHeight();
            xOffset = 20;
            yOffset = 0;
            this.scale = .75f;
        }else {
            this.img = ImageMaster.loadImage("TheGuardianChan/monsters/TheSlimeBossWaifu/char/hat.png");
            W = img.getWidth();
            H = img.getHeight();
            xOffset = -17;
            yOffset = -2;
            this.scale = 0.7f;
        }



        this.p = (SlimeboundCharacter) p;
        this.renderBehind = false;


    }

    public void update() {


    }

    public void dispose() {
        this.isDone = true;
        this.img.dispose();
    }

    public void render(SpriteBatch sb, float x, float y) {
    }


    public void render(SpriteBatch sb) {


        sb.setColor(new Color(1F, 1F, 1F, 2F));

        sb.draw(this.img, this.p.hatX + p.animX + this.p.drawX - W / 2.0F + ((xOffset / p.renderscale) * Settings.scale), this.p.hatY + this.p.animY + this.p.drawY - W / 2.0F + ((yOffset / p.renderscale) * Settings.scale), W / 2.0F, W / 2.0F, W, W, this.scale * Settings.scale, this.scale * Settings.scale, 0.0F, 0, 0, W, W, false, false);
//        if(AbstractDungeon.player.hasPower("Slimebound:DuplicatedFormPower" ) && !reskinContent.slimeOriginalAnimation ){duplicatedScale = 1.5f;}else {duplicatedScale = 1.0f;}

//        if(SlimeboundCharacter.showHat){
//            sb.draw(this.img, this.p.hatX + p.animX + this.p.drawX - W / 2.0F * Settings.scale  + ((xOffset / p.renderscale) * Settings.scale/ duplicatedScale), this.p.hatY + this.p.animY + this.p.drawY - H / 2.0F * Settings.scale + ((yOffset/p.renderscale) * Settings.scale/ duplicatedScale), W / 2.0F , H / 2.0F, W, H, Settings.scale * scale / duplicatedScale, Settings.scale * scale / duplicatedScale, this.p.animationAngle, 0, 0, W, H, false, false);


        }
}


