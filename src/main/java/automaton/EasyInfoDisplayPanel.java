package automaton;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.Settings;

import java.util.ArrayList;

public abstract class EasyInfoDisplayPanel {
    public float x;
    public float y;

    public float width;

    public static ArrayList<EasyInfoDisplayPanel> specialDisplays = new ArrayList<>();

    public static void render(SpriteBatch sb) {
        SuperTip.render(sb);
    }

    public EasyInfoDisplayPanel(float x, float y, float width) {
        this.x = x * Settings.scale;
        this.y = y * Settings.scale;
        this.width = width * Settings.scale;
    }

    public abstract String getTitle();

    public abstract String getDescription();
}
