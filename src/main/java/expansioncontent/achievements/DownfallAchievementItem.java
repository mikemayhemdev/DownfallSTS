package expansioncontent.achievements;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

public class DownfallAchievementItem {
    private TextureAtlas.AtlasRegion unlockedImg;
    private TextureAtlas.AtlasRegion lockedImg;
    public TextureAtlas.AtlasRegion currentImg;
    private static final Color LOCKED_COLOR = new Color(1.0F, 1.0F, 1.0F, 0.8F);
    public static TextureAtlas atlas;
    private String title;
    private String desc;
    public String key;
    public boolean isUnlocked;
    public Hitbox hb;

    public DownfallAchievementItem(String title, String desc, String key, TextureAtlas.AtlasRegion unlockedImage, TextureAtlas.AtlasRegion lockedImage) {
        this.hb = new Hitbox(160.0F * Settings.scale, 160.0F * Settings.scale);
        this.isUnlocked = UnlockTracker.achievementPref.getBoolean(key, false);
        this.key = key;
        this.title = title;
        this.desc = desc;
        this.unlockedImg = unlockedImage;
        this.lockedImg = lockedImage;
        this.currentImg = lockedImage;
    }

    public String getKey() {
        return key;
    }

    public void reloadImg() {
        if (this.isUnlocked) {
            this.unlockedImg = atlas.findRegion(this.unlockedImg.name);
        } else {
            this.lockedImg = atlas.findRegion(this.lockedImg.name);
        }
    }

    public void render(SpriteBatch sb, float x, float y) {
        TextureAtlas.AtlasRegion currentImg = this.isUnlocked ? this.unlockedImg : this.lockedImg;
        this.currentImg = currentImg;
        Color currentColor = this.isUnlocked ? Color.WHITE : LOCKED_COLOR;
        sb.setColor(currentColor);
        if (this.hb.hovered) {
            sb.draw(currentImg, x - (float)currentImg.packedWidth / 2.0F, y - (float)currentImg.packedHeight / 2.0F, (float)currentImg.packedWidth / 2.0F, (float)currentImg.packedHeight / 2.0F, (float)currentImg.packedWidth, (float)currentImg.packedHeight, Settings.scale * 1.1F, Settings.scale * 1.1F, 0.0F);
        } else {
            sb.draw(currentImg, x - (float)currentImg.packedWidth / 2.0F, y - (float)currentImg.packedHeight / 2.0F, (float)currentImg.packedWidth / 2.0F, (float)currentImg.packedHeight / 2.0F, (float)currentImg.packedWidth, (float)currentImg.packedHeight, Settings.scale, Settings.scale, 0.0F);
        }
        this.hb.move(x, y);
        this.hb.render(sb);
    }

    public void update() {
        if (this.hb != null) {
            this.hb.update();
            if (this.hb.hovered) {
                TipHelper.renderGenericTip((float) InputHelper.mX + 100.0F * Settings.scale, (float)InputHelper.mY, this.title, this.desc);
            }
        }
    }
}