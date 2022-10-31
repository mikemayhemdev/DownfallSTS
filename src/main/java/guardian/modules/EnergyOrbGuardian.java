package guardian.modules;

import basemod.abstracts.CustomEnergyOrb;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import guardian.characters.GuardianCharacter;

public class EnergyOrbGuardian extends CustomEnergyOrb {
    private static final float ORB_IMG_SCALE = 1.15F * Settings.scale;
    private static final Texture inheritOrbCover = ImageMaster.loadImage("guardian/img/ui/topPanel/Inherit/ballBg.png");
    private float angle4;
    private float angle3;
    private float angle2;
    private float angle1;
    private float scale5 = 1.0f;

    public EnergyOrbGuardian(String[] orbTexturePaths, String orbVfxPath) {
        super(orbTexturePaths, orbVfxPath, null);
    }

    @Override
    public void updateOrb(int orbCount) {
        if (orbCount == 0) {
            this.angle4 += Gdx.graphics.getDeltaTime() * -5.0F;
            this.angle3 += Gdx.graphics.getDeltaTime() * 5.0F;
            this.angle2 += Gdx.graphics.getDeltaTime() * -8.0F;
            this.angle1 += Gdx.graphics.getDeltaTime() * 8.0F;
        } else {
            this.angle4 += Gdx.graphics.getDeltaTime() * -20.0F;
            this.angle3 += Gdx.graphics.getDeltaTime() * 20.0F;
            this.angle2 += Gdx.graphics.getDeltaTime() * -40.0F;
            this.angle1 += Gdx.graphics.getDeltaTime() * 40.0F;
        }

        scale5 += Gdx.graphics.getDeltaTime() * 5.0f * (GuardianCharacter.orbScaleFinal - scale5);
        if((GuardianCharacter.orbScaleFinal - scale5 < 0.0f && GuardianCharacter.orbScaleFinal - scale5 > -0.01f )
            || (GuardianCharacter.orbScaleFinal - scale5 > 0.0f && GuardianCharacter.orbScaleFinal - scale5 < 0.01f )){
             if(GuardianCharacter.orbScaleFinal < 1.0f){
                 scale5 = 0.7f;
             }else {
                 scale5 = 1.0f;
             }
        }
    }

    @Override
    public void renderOrb(SpriteBatch sb, boolean enabled, float current_x, float current_y) {
        sb.setColor(Color.WHITE);

        if (enabled) {
            sb.draw(this.energyLayers[5], current_x - 64.0f, current_y - 64.0f, 64.0f, 64.0f, 128.0F, 128.0F, ORB_IMG_SCALE * scale5, ORB_IMG_SCALE * scale5, 0.0f, 0, 0, 128, 128, false, false);
            sb.draw(this.energyLayers[0], current_x - 64.0f, current_y - 64.0f, 64.0f, 64.0f, 128.0F, 128.0F, ORB_IMG_SCALE, ORB_IMG_SCALE, 0.0f, 0, 0, 128, 128, false, false);
            sb.draw(this.energyLayers[1], current_x - 64.0f, current_y - 64.0f, 64.0f, 64.0f, 128.0F, 128.0F, ORB_IMG_SCALE, ORB_IMG_SCALE, this.angle1, 0, 0, 128, 128, false, false);
            sb.draw(this.energyLayers[2], current_x - 64.0f, current_y - 64.0f, 64.0f, 64.0f, 128.0F, 128.0F, ORB_IMG_SCALE, ORB_IMG_SCALE, this.angle2, 0, 0, 128, 128, false, false);
            sb.draw(this.energyLayers[3], current_x - 64.0f, current_y - 64.0f, 64.0f, 64.0f, 128.0F, 128.0F, ORB_IMG_SCALE, ORB_IMG_SCALE, this.angle3, 0, 0, 128, 128, false, false);
            sb.draw(this.energyLayers[4], current_x - 64.0f, current_y - 64.0f, 64.0f, 64.0f, 128.0F, 128.0F, ORB_IMG_SCALE, ORB_IMG_SCALE, this.angle4, 0, 0, 128, 128, false, false);

        } else {
            sb.draw(this.noEnergyLayers[5], current_x - 64.0f, current_y - 64.0f, 64.0f, 64.0f, 128.0F, 128.0F, ORB_IMG_SCALE * scale5, ORB_IMG_SCALE * scale5, 0.0f, 0, 0, 128, 128, false, false);
            sb.draw(this.noEnergyLayers[0], current_x - 64.0f, current_y - 64.0f, 64.0f, 64.0f, 128.0F, 128.0F, ORB_IMG_SCALE, ORB_IMG_SCALE, 0.0f, 0, 0, 128, 128, false, false);
            sb.draw(this.noEnergyLayers[1], current_x - 64.0f, current_y - 64.0f, 64.0f, 64.0f, 128.0F, 128.0F, ORB_IMG_SCALE, ORB_IMG_SCALE, this.angle1, 0, 0, 128, 128, false, false);
            sb.draw(this.noEnergyLayers[2], current_x - 64.0f, current_y - 64.0f, 64.0f, 64.0f, 128.0F, 128.0F, ORB_IMG_SCALE, ORB_IMG_SCALE, this.angle2, 0, 0, 128, 128, false, false);
            sb.draw(this.noEnergyLayers[3], current_x - 64.0f, current_y - 64.0f, 64.0f, 64.0f, 128.0F, 128.0F, ORB_IMG_SCALE, ORB_IMG_SCALE, this.angle3, 0, 0, 128, 128, false, false);
            sb.draw(this.noEnergyLayers[4], current_x - 64.0f, current_y - 64.0f, 64.0f, 64.0f, 128.0F, 128.0F, ORB_IMG_SCALE, ORB_IMG_SCALE, this.angle4, 0, 0, 128, 128, false, false);
        }

        sb.draw(this.baseLayer, current_x - 64.0f, current_y - 64.0f, 64.0f, 64.0f, 128.0F, 128.0F, ORB_IMG_SCALE, ORB_IMG_SCALE, 0.0f, 0, 0, 128, 128, false, false);
    }
}
