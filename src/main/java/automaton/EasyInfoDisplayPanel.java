package automaton;

import com.megacrit.cardcrawl.core.Settings;

import java.util.ArrayList;

public abstract class EasyInfoDisplayPanel {
    public enum RENDER_TIMING {
        TIMING_RENDERSUBSCRIBER,
        TIMING_PLAYER_RENDER,
        TIMING_ENERGYPANEL_RENDER
    }

    public float x;
    public float y;

    public float width;

    public static ArrayList<EasyInfoDisplayPanel> specialDisplays = new ArrayList<>();

    public EasyInfoDisplayPanel(float x, float y, float width) {
        this.x = x * Settings.scale;
        this.y = y * Settings.scale;
        this.width = width * Settings.scale;
    }

    public abstract String getTitle();

    public abstract String getDescription();

    public abstract RENDER_TIMING getTiming();
}
