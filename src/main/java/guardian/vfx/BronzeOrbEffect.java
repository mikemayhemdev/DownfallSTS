package guardian.vfx;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.esotericsoftware.spine.Skeleton;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.MathHelper;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.SmallLaserEffect;


public class BronzeOrbEffect extends com.megacrit.cardcrawl.vfx.AbstractGameEffect {
    private float scale = 1F;
    private static int W;
    private Texture img;
    public AbstractPlayer p;
    public AbstractMonster m;
    private float currentX;
    private float targetX;
    private float currentY;
    private float targetY;
    private boolean entering;
    private boolean firing;
    private boolean exiting;
    private float Xoffset = 80F * Settings.scale;
    private float YOffset = 150F * Settings.scale;

    public BronzeOrbEffect(AbstractPlayer p, AbstractMonster m) {
        this.duration = 0.5F;
        this.entering = true;
        this.img = ImageMaster.loadImage("images/monsters/theCity/automaton/orb.png");
        W = img.getWidth();
        this.p = p;
        this.m = m;
        this.renderBehind = false;
        this.targetX = p.drawX + (100F * Settings.scale);
        this.currentX = p.drawX - (150F * Settings.scale);
        this.targetY = p.drawY + (150F * Settings.scale);
        this.currentY = p.drawY + (1000F * Settings.scale);
    }

    public void update() {
        this.duration -= Gdx.graphics.getDeltaTime();

        if (this.entering || this.exiting) {
            this.currentX = MathHelper.orbLerpSnap(this.currentX, this.targetX);
            this.currentY = MathHelper.orbLerpSnap(this.currentY, this.targetY);
        }

        if (this.duration <= 0F) {
            if (this.entering) {
                this.duration = 0.5F;
                this.entering = false;
                this.firing = true;
                CardCrawlGame.sound.play("ATTACK_MAGIC_BEAM_SHORT", 0.5F);
                AbstractDungeon.topLevelEffects.add(new SmallLaserEffectColored(this.targetX + Xoffset, this.targetY + YOffset, m.hb.cX, m.hb.cY, Color.CYAN));

            } else if (this.firing) {
                this.duration = 0.5F;
                this.firing = false;
                this.targetX = p.drawX + (250F * Settings.scale);
                this.targetY = p.drawY + (700F * Settings.scale);
                this.exiting = true;
            } else if (this.exiting) {
                this.exiting = false;
                dispose();
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

        sb.draw(this.img, this.currentX, this.currentY, W / 2.0F, W / 2.0F, W, W, this.scale * Settings.scale, this.scale * Settings.scale, 0.0F, 0, 0, W, W, false, false);


    }
}


