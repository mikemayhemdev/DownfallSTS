package downfall.vfx.combat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.scenes.AbstractScene;
import com.megacrit.cardcrawl.scenes.TheEndingScene;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.DeathScreenFloatyEffect;
import downfall.downfallMod;
import downfall.util.TextureLoader;

import java.util.ArrayList;

public class FakeDeathScene
        extends TheEndingScene
{
    private ArrayList<DeathScreenFloatyEffect> particles;
   // private Texture filledPixel;
    private Color color;
    private boolean addColor;
   // private Color[] possibilities;

    public FakeDeathScene()
    {
        super();
        this.particles = new ArrayList();
        this.ambianceName = "AMBIANCE_BEYOND";
        fadeInAmbiance();
     //   this.filledPixel = TextureLoader.getTexture(downfallMod.assetPath("images/vfx/filledpixel.png"));
        this.color = Color.DARK_GRAY.cpy();
        //this.possibilities = new Color[] { this.color, Color.CYAN, Color.OLIVE, Color.PURPLE, Color.GOLD };
        this.color.a = 0.0F;
        this.addColor = true;
    }

    public void update()
    {
        if (this.particles.size() < 60) {
            DeathScreenFloatyEffect dfe2 = new DeathScreenFloatyEffect();
            dfe2.renderBehind = true;
            this.particles.add(dfe2);
        }
        for (int i = this.particles.size() - 1; i >= 0; i--)
        {
            DeathScreenFloatyEffect dfe = (DeathScreenFloatyEffect)this.particles.get(i);
            dfe.update();
            if (dfe.isDone) {
                this.particles.remove(i);
            }
        }
        if (this.addColor)
        {
            this.color.a += Gdx.graphics.getDeltaTime() / 8.0F;
            if (this.color.a >= 0.2F)
            {
                this.addColor = false;
                this.color.a = 0.2F;
            }
        }
        else
        {
            this.color.a -= Gdx.graphics.getDeltaTime() / 8.0F;
            if (this.color.a <= 0.0F)
            {
                this.addColor = true;
       //         this.color = this.possibilities[com.badlogic.gdx.math.MathUtils.random(this.possibilities.length - 1)].cpy();
                this.color.a = 0.0F;
            }
        }
    }

    public void renderCombatRoomBg(SpriteBatch sb)
    {
        sb.setColor(Color.WHITE);
        this.renderAtlasRegionIf(sb, this.bg, true);
       // sb.setColor(this.color);
       // sb.draw(this.filledPixel, 0.0F, 0.0F, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F, Settings.WIDTH, Settings.HEIGHT, 1.0F, 1.0F, 0.0F, 0, 0, 1, 1, false, false);
        sb.setColor(Color.WHITE);
        for (AbstractGameEffect age : this.particles) {
            age.render(sb);
        }
    }

    public void renderCombatRoomFg(SpriteBatch sb)
    {

    }

    public void renderCampfireRoom(SpriteBatch sb)
    {
        sb.setColor(Color.WHITE);
        renderAtlasRegionIf(sb, this.campfireBg, true);
        for (DeathScreenFloatyEffect dfe : this.particles) {
            dfe.render(sb);
        }
        sb.setColor(Color.WHITE);
        this.renderAtlasRegionIf(sb, this.campfireBg, true);
    }

    public void randomizeScene() {}
}
