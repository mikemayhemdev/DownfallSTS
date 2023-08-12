package collector.util;

import basemod.abstracts.CustomEnergyOrb;
import collector.CollectorChar;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.math.Interpolation;
import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import downfall.util.TextureLoader;

public class DoubleEnergyOrb extends CustomEnergyOrb {
    public static final int SECOND_ORB_W = 128;
    public static final int PRIMARY_ORB_W = 128;
    public static final float SECOND_ORB_IMG_SCALE = 0.75F * Settings.scale;
    public static final float PRIMARY_ORB_IMG_SCALE = 1.15F * Settings.scale;
    public static final float X_OFFSET = 100f * Settings.scale;
    public static final float Y_OFFSET = 0f * Settings.scale;
    protected Texture secondBaseLayer;
    protected Texture[] secondEnergyLayers;
    protected Texture[] secondNoEnergyLayers;
    protected float[] secondLayerSpeeds;
    protected float[] secondAngles;

    private Texture mask;
    private FrameBuffer fbo;

    public DoubleEnergyOrb(String[] orbTexturePaths, String orbVfxPath, float[] layerSpeeds, String[] orbTexturePathsAlt, String orbVfxPathAlt) {
        super(orbTexturePaths, orbVfxPath, layerSpeeds);

        int numLayers = 5;
        secondEnergyLayers = new Texture[numLayers];
        secondNoEnergyLayers = new Texture[numLayers];
        assert orbTexturePathsAlt.length >= 3;

        assert orbTexturePathsAlt.length % 2 == 1;

        int middleIdx = orbTexturePathsAlt.length / 2;
        this.secondEnergyLayers = new Texture[middleIdx];
        this.secondNoEnergyLayers = new Texture[middleIdx];

        for(int i = 0; i < middleIdx; ++i) {
            this.secondEnergyLayers[i] = ImageMaster.loadImage(orbTexturePathsAlt[i]);
            this.secondNoEnergyLayers[i] = ImageMaster.loadImage(orbTexturePathsAlt[i + middleIdx + 1]);
        }

        secondBaseLayer = TextureLoader.getTexture("collectorResources/images/char/mainChar/orb/alt/layer6.png");
        this.orbVfx = ImageMaster.loadImage(orbVfxPath);

        if (layerSpeeds == null) {
            layerSpeeds = new float[]{-20.0F, 20.0F, -40.0F, 40.0F, 360.0F};
        }

        this.secondLayerSpeeds = layerSpeeds;
        this.secondAngles = new float[this.secondLayerSpeeds.length];

        assert this.secondEnergyLayers.length == this.secondLayerSpeeds.length;

        fbo = new FrameBuffer(Pixmap.Format.RGBA8888, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false, false);
        mask = TextureLoader.getTexture("collectorResources/images/char/mainChar/orb/mask.png");

    }

    public Texture getEnergyImage() {
        return this.orbVfx;
    }

    public void updateOrb(int energyCount) {
        super.updateOrb(energyCount);
        int d = this.secondAngles.length;
        for (int i = 0; i < this.secondAngles.length; i++) {
            if (energyCount == 0) {
                this.secondAngles[i] -= Gdx.graphics.getDeltaTime() * this.secondLayerSpeeds[d - 1 - i] / 4.0F;
            } else {
                this.secondAngles[i] -= Gdx.graphics.getDeltaTime() * this.secondLayerSpeeds[d - 1 - i];
            }
        }

        if (secondVfxTimer != 0.0F) {
            this.secondEnergyVfxColor.a = Interpolation.exp10In.apply(0.5F, 0.0F, 1.0F - secondVfxTimer / 2.0F);
            this.secondEnergyVfxAngle += Gdx.graphics.getDeltaTime() * -30.0F;
            this.secondEnergyVfxScale = Settings.scale * Interpolation.exp10In.apply(1.0F, 0.1F, 1.0F - secondVfxTimer / 2.0F);
            secondVfxTimer -= Gdx.graphics.getDeltaTime();
            if (secondVfxTimer < 0.0F) {
                secondVfxTimer = 0.0F;
                this.secondEnergyVfxColor.a = 0.0F;
            }
        }
        hb.update();
    }

    public void renderOrb(SpriteBatch sb, boolean enabled, float current_x, float current_y) {
        hb.move(current_x + X_OFFSET, current_y + Y_OFFSET);
        sb.setColor(Color.WHITE);
        int i;

        if (AbstractDungeon.player.chosenClass.equals(CollectorChar.Enums.THE_COLLECTOR) || NewReserves.reserveCount() > 0) {
            if (NewReserves.reserveCount() > 0) {
                for (i = 0; i < this.secondEnergyLayers.length; ++i) {
                    sb.draw(this.secondEnergyLayers[i], current_x + X_OFFSET - SECOND_ORB_W / 2F, current_y + Y_OFFSET - SECOND_ORB_W / 2F, SECOND_ORB_W / 2F, SECOND_ORB_W / 2F, SECOND_ORB_W, SECOND_ORB_W, SECOND_ORB_IMG_SCALE, SECOND_ORB_IMG_SCALE, this.secondAngles[i], 0, 0, SECOND_ORB_W, SECOND_ORB_W, false, false);
                }
            } else {
                for (i = 0; i < this.secondNoEnergyLayers.length; ++i) {
                    sb.draw(this.secondNoEnergyLayers[i], current_x + X_OFFSET - SECOND_ORB_W / 2F, current_y + Y_OFFSET - SECOND_ORB_W / 2F, SECOND_ORB_W / 2F, SECOND_ORB_W / 2F, SECOND_ORB_W, SECOND_ORB_W, SECOND_ORB_IMG_SCALE, SECOND_ORB_IMG_SCALE, this.secondAngles[i], 0, 0, SECOND_ORB_W, SECOND_ORB_W, false, false);
                }
            }
            sb.draw(this.secondBaseLayer, current_x + X_OFFSET - SECOND_ORB_W / 2F, current_y + Y_OFFSET - SECOND_ORB_W / 2F, SECOND_ORB_W / 2F, SECOND_ORB_W / 2F, SECOND_ORB_W, SECOND_ORB_W, SECOND_ORB_IMG_SCALE, SECOND_ORB_IMG_SCALE, 0.0F, 0, 0, SECOND_ORB_W, SECOND_ORB_W, false, false);
        }
        sb.setColor(Color.WHITE);
        sb.end();
        fbo.begin();

        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        Gdx.gl.glColorMask(true, true, true, true);
        sb.begin();

        sb.setColor(Color.WHITE);
        sb.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        if (enabled) {
            for (i = 0; i < this.energyLayers.length; ++i) {
                sb.draw(this.energyLayers[i], current_x - PRIMARY_ORB_W / 2F, current_y - PRIMARY_ORB_W / 2F, PRIMARY_ORB_W / 2F, PRIMARY_ORB_W / 2F, PRIMARY_ORB_W, PRIMARY_ORB_W, PRIMARY_ORB_IMG_SCALE, PRIMARY_ORB_IMG_SCALE, this.angles[i], 0, 0, 128, 128, false, false);
            }
        } else {
            for (i = 0; i < this.noEnergyLayers.length; ++i) {
                sb.draw(this.noEnergyLayers[i], current_x - PRIMARY_ORB_W / 2F, current_y - PRIMARY_ORB_W / 2F, PRIMARY_ORB_W / 2F, PRIMARY_ORB_W / 2F, PRIMARY_ORB_W, PRIMARY_ORB_W, PRIMARY_ORB_IMG_SCALE, PRIMARY_ORB_IMG_SCALE, this.angles[i], 0, 0, 128, 128, false, false);
            }
        }


        if (hb.hovered && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT && !AbstractDungeon.isScreenUp) {
            TipHelper.renderGenericTip(50.0F * Settings.scale, 380.0F * Settings.scale, uiStrings.TEXT[0], uiStrings.TEXT[1]);
        }

        sb.setBlendFunction(0, GL20.GL_SRC_ALPHA);
        sb.setColor(new Color(1, 1, 1, 1));
        sb.draw(mask, current_x - 64, current_y - 64, 64, 64, 128, 128, 1F, 1F, 0, 0, 0, 128, 128, false, false);
        sb.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        sb.end();

        fbo.end();
        sb.begin();
        TextureRegion drawTex = new TextureRegion(fbo.getColorBufferTexture());
        drawTex.flip(false, true);
        sb.draw(drawTex, -Settings.VERT_LETTERBOX_AMT, -Settings.HORIZ_LETTERBOX_AMT);
        sb.draw(this.baseLayer, current_x - PRIMARY_ORB_W / 2F, current_y - PRIMARY_ORB_W / 2F, PRIMARY_ORB_W / 2F, PRIMARY_ORB_W / 2F, PRIMARY_ORB_W, PRIMARY_ORB_W, PRIMARY_ORB_IMG_SCALE, PRIMARY_ORB_IMG_SCALE, 0.0F, 0, 0, 128, 128, false, false);
        hb.render(sb);
    }

    @SpirePatch(clz = AbstractPlayer.class, method = SpirePatch.CLASS)
    public static class DoubleOrbField {
        public static SpireField<Boolean> isDoubleOrb = new SpireField<>(() -> Boolean.FALSE);
    }

    public static float secondVfxTimer = 0F;
    private static float secondEnergyVfxAngle = 0F;
    private static float secondEnergyVfxScale = Settings.scale;
    private static Color secondEnergyVfxColor = Color.WHITE.cpy();

    private static Hitbox hb = new Hitbox(80 * Settings.scale, 80 * Settings.scale);
    private static UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("collector:SecondEnergyOrb");

    @SpirePatch(clz = EnergyPanel.class, method = "renderVfx")
    public static class FlashSecondOrbPatch {
        @SpirePrefixPatch
        public static void flashSecondOrb(EnergyPanel __instance, SpriteBatch sb, Texture ___gainEnergyImg, Color ___energyVfxColor, float ___energyVfxScale, float ___energyVfxAngle) {
            if (DoubleOrbField.isDoubleOrb.get(AbstractDungeon.player) && secondVfxTimer > 0) {
                sb.setBlendFunction(770, 1);
                sb.setColor(secondEnergyVfxColor);
                sb.draw(___gainEnergyImg, __instance.current_x + X_OFFSET - SECOND_ORB_W, __instance.current_y + Y_OFFSET - SECOND_ORB_W, SECOND_ORB_W, SECOND_ORB_W, SECOND_ORB_W * 2F, SECOND_ORB_W * 2F, secondEnergyVfxScale * SECOND_ORB_IMG_SCALE / PRIMARY_ORB_IMG_SCALE, secondEnergyVfxScale * SECOND_ORB_IMG_SCALE / PRIMARY_ORB_IMG_SCALE, secondEnergyVfxAngle - 50.0F, 0, 0, SECOND_ORB_W * 2, SECOND_ORB_W * 2, true, false);
                sb.draw(___gainEnergyImg, __instance.current_x + X_OFFSET - SECOND_ORB_W, __instance.current_y + Y_OFFSET - SECOND_ORB_W, SECOND_ORB_W, SECOND_ORB_W, SECOND_ORB_W * 2F, SECOND_ORB_W * 2F, secondEnergyVfxScale * SECOND_ORB_IMG_SCALE / PRIMARY_ORB_IMG_SCALE, secondEnergyVfxScale * SECOND_ORB_IMG_SCALE / PRIMARY_ORB_IMG_SCALE, -secondEnergyVfxAngle, 0, 0, SECOND_ORB_W * 2, SECOND_ORB_W * 2, false, false);
                sb.setBlendFunction(770, 771);
            }
        }
    }
}
