package collector.ui;

import basemod.BaseMod;
import basemod.helpers.TooltipInfo;
import com.evacipated.cardcrawl.mod.stslib.icons.AbstractCustomIcon;
import downfall.util.TextureLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static collector.CollectorMod.makeID;

public class ReserveIcon extends AbstractCustomIcon {
    public static final String ID = makeID("reserve");
    private static ReserveIcon singleton;

    public ReserveIcon() {
        super(ID, TextureLoader.getTexture("collectorResources/images/char/mainChar/orb/alt/ReserveIcon.png"));
    }

    public static ReserveIcon get()
    {
        if (singleton == null) {
            singleton = new ReserveIcon();
        }
        return singleton;
    }

    @Override
    public List<TooltipInfo> getCustomTooltips() {
        ArrayList<TooltipInfo> list = new ArrayList<>();
        list.add(new TooltipInfo(BaseMod.getKeywordTitle(ID), BaseMod.getKeywordDescription(ID)));
        return list;
    }
}
