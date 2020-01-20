package theHexaghost.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.MathHelper;
import com.megacrit.cardcrawl.vfx.BobEffect;

public class MyBody implements Disposable {
    public static final String ID = "MyBody";
    private float rotationSpeed = 1.0F;
    public float targetRotationSpeed = 30.0F;
    private BobEffect effect = new BobEffect(0.75F);
    private static final String IMG_DIR = "images/monsters/theBottom/boss/ghost/";
    private static final int W = 512;
    private Texture plasma1;
    private Texture plasma2;
    private Texture plasma3;
    private Texture shadow;
    private float plasma1Angle = 0.0F;
    private float plasma2Angle = 0.0F;
    private float plasma3Angle = 0.0F;
    private static final float BODY_OFFSET_Y;

    public MyBody() {
        this.plasma1 = ImageMaster.loadImage("images/monsters/theBottom/boss/ghost/plasma1.png");// 36
        this.plasma2 = ImageMaster.loadImage("images/monsters/theBottom/boss/ghost/plasma2.png");// 37
        this.plasma3 = ImageMaster.loadImage("images/monsters/theBottom/boss/ghost/plasma3.png");// 38
        this.shadow = ImageMaster.loadImage("images/monsters/theBottom/boss/ghost/shadow.png");// 39
    }// 40

    public void update() {
        this.effect.update();// 43
        this.plasma1Angle += this.rotationSpeed * Gdx.graphics.getDeltaTime();// 44
        this.plasma2Angle += this.rotationSpeed / 2.0F * Gdx.graphics.getDeltaTime();// 45
        this.plasma3Angle += this.rotationSpeed / 3.0F * Gdx.graphics.getDeltaTime();// 46
        this.rotationSpeed = MathHelper.fadeLerpSnap(this.rotationSpeed, this.targetRotationSpeed);// 48
        this.effect.speed = this.rotationSpeed * Gdx.graphics.getDeltaTime();// 49
    }// 50

    public void render(SpriteBatch sb) {
        sb.setColor(AbstractDungeon.player.tint.color);// 53
        sb.draw(this.plasma3, AbstractDungeon.player.drawX - 256.0F + AbstractDungeon.player.animX + 12.0F * Settings.scale, AbstractDungeon.player.drawY + AbstractDungeon.player.animY + this.effect.y * 2.0F - 256.0F + BODY_OFFSET_Y, 256.0F, 256.0F, 512.0F, 512.0F, Settings.scale * 0.95F, Settings.scale * 0.95F, this.plasma3Angle, 0, 0, 512, 512, true, false);// 54
        sb.draw(this.plasma2, AbstractDungeon.player.drawX - 256.0F + AbstractDungeon.player.animX + 6.0F * Settings.scale, AbstractDungeon.player.drawY + AbstractDungeon.player.animY + this.effect.y - 256.0F + BODY_OFFSET_Y, 256.0F, 256.0F, 512.0F, 512.0F, Settings.scale, Settings.scale, this.plasma2Angle, 0, 0, 512, 512, true, false);// 71
        sb.draw(this.plasma1, AbstractDungeon.player.drawX - 256.0F + AbstractDungeon.player.animX, AbstractDungeon.player.drawY + AbstractDungeon.player.animY + this.effect.y * 0.5F - 256.0F + BODY_OFFSET_Y, 256.0F, 256.0F, 512.0F, 512.0F, Settings.scale, Settings.scale, this.plasma1Angle, 0, 0, 512, 512, true, false);// 88
        sb.draw(this.shadow, AbstractDungeon.player.drawX - 256.0F + AbstractDungeon.player.animX + 12.0F * Settings.scale, AbstractDungeon.player.drawY + AbstractDungeon.player.animY + this.effect.y / 4.0F - 15.0F * Settings.scale - 256.0F + BODY_OFFSET_Y, 256.0F, 256.0F, 512.0F, 512.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 512, 512, true, false);// 106
    }// 123

    public void dispose() {
        this.plasma1.dispose();// 127
        this.plasma2.dispose();// 128
        this.plasma3.dispose();// 129
        this.shadow.dispose();// 130
    }// 131

    static {
        BODY_OFFSET_Y = 256.0F * Settings.scale;// 32
    }
}
