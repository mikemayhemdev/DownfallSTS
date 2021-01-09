package automaton.vfx;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.MathHelper;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.BobEffect;


public class FloatingBronzeOrbEffect extends com.megacrit.cardcrawl.vfx.AbstractGameEffect {
    private static int W;
    public AbstractPlayer p;
    public AbstractMonster m;
    private float scale = 1F;
    private Texture img;
    public float currentX;
    private float targetX;
    public float currentY;
    private float targetY;
    private boolean entering;
    private boolean floatinup;
    private BobEffect bobEffect;

    public FloatingBronzeOrbEffect(AbstractPlayer p) {
        this.duration = 0.5F;
        this.entering = true;
        this.img = ImageMaster.loadImage("images/monsters/theCity/automaton/orb.png");
        W = img.getWidth();
        this.p = p;
        this.renderBehind = false;
        this.targetX = p.drawX + (100F * Settings.scale);
        this.currentX = p.drawX - (150F * Settings.scale);
        this.targetY = p.drawY + (150F * Settings.scale);
        this.currentY = p.drawY + (1000F * Settings.scale);
        bobEffect = new BobEffect(1F);
    }

    public void update() {
        this.duration -= Gdx.graphics.getDeltaTime();
        bobEffect.update();

        if (this.entering || this.floatinup) {
            this.currentX = MathHelper.orbLerpSnap(this.currentX, this.targetX);
            this.currentY = MathHelper.orbLerpSnap(this.currentY, this.targetY);
        }

        if (this.duration <= 0F) {
            if (this.entering) {
                this.entering = false;
            }

        }
    }

    public void dispose() {
        this.isDone = true;
        this.img.dispose();
    }

    public void render(SpriteBatch sb, float x, float y) {
    }


    public void render(SpriteBatch sb) {


        sb.setColor(new Color(1F, 1F, 1F, 2F));

        sb.draw(this.img, this.currentX, this.currentY + bobEffect.y, W / 2.0F, W / 2.0F, W, W, this.scale * Settings.scale, this.scale * Settings.scale, 0.0F, 0, 0, W, W, false, false);


    }
}


