package sneckomod.vfx;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

import static com.megacrit.cardcrawl.helpers.FontHelper.layout;

public class AnnouncementEffect extends AbstractGameEffect {
    public String msg;

    public AnnouncementEffect(Color col, String msg, float duration) {
        this.startingDuration = this.duration = duration;
        this.msg = msg;
        this.color = col.cpy();
    }

    public void update() {
        super.update();
    }

    public void render(SpriteBatch sb) {
        if (!this.isDone) {
            String tmp = msg.replaceAll("\\d", "0");
            layout.setText(FontHelper.SCP_cardEnergyFont, tmp);
            float baseBox = layout.width;
            layout.setText(FontHelper.SCP_cardEnergyFont, msg);
            sb.setColor(Settings.TWO_THIRDS_TRANSPARENT_BLACK_COLOR);
            //sb.draw(ImageMaster.WHITE_SQUARE_IMG, Settings.WIDTH / 2.0F - baseBox / 2.0F - 12.0F * Settings.scale, y - 24.0F * Settings.scale, baseBox + 24.0F * Settings.scale, layout.height * Settings.scale);
            FontHelper.renderFont(sb, FontHelper.SCP_cardEnergyFont, msg, (Settings.WIDTH / 2.0F) - baseBox / 2.0F, (Settings.HEIGHT - (180.0f * Settings.scale)) + layout.height / 2.0F, color);
            //FontHelper.renderFontCentered(sb, FontHelper.SCP_cardEnergyFont, msg, Settings.WIDTH / 2.0F, y, color);
        }
    }

    public void dispose() {
    }
}
