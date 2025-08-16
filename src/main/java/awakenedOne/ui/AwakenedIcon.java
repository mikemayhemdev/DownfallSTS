package awakenedOne.ui;

import awakenedOne.util.TexLoader;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.icons.AbstractCustomIcon;

public class AwakenedIcon extends AbstractCustomIcon {
    public static final String ID = "awakened";
    private static final Texture iconTex = TexLoader.getTexture("awakenedResources/images/ui/AwesomeIcon.png");
    private static AwakenedIcon singleton;

    public AwakenedIcon() {
        super(ID, iconTex);
    }

    public static AwakenedIcon get() {
        if (singleton == null) {
            singleton = new AwakenedIcon();
        }
        return singleton;
    }

}
