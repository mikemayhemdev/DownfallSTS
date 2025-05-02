package awakenedOne.ui;

import awakenedOne.util.TexLoader;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.icons.AbstractCustomIcon;

import java.util.Collections;
import java.util.List;

import static awakenedOne.AwakenedOneMod.makeID;

public class AwakenedIcon extends AbstractCustomIcon {
    public static final String ID = "awakened";
    private static AwakenedIcon singleton;
    private static final Texture iconTex = TexLoader.getTexture("awakenedResources/images/ui/AwesomeIcon.png");

    public AwakenedIcon() {
        super(ID, iconTex);
    }

    public static AwakenedIcon get() {
        if (singleton == null) {
            singleton = new AwakenedIcon();
        }
        return singleton;
    }

    @Override
    public List<String> keywordLinks() {
        return Collections.singletonList(makeID("awakened"));
    }
}
