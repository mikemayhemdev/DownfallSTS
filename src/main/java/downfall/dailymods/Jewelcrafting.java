package downfall.dailymods;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.daily.mods.AbstractDailyMod;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.RunModStrings;
import downfall.downfallMod;


public class Jewelcrafting extends AbstractDailyMod {
    public static final String ID = downfallMod.makeID("Jewelcrafting");
    public static final String NAME;
    public static final String DESC;
    private static final RunModStrings modStrings;

    static {
        modStrings = CardCrawlGame.languagePack.getRunModString(ID);
        NAME = modStrings.NAME;
        DESC = modStrings.DESCRIPTION;
    }

    public Jewelcrafting() {
        super(ID, NAME, DESC, null, true);
        this.img = ImageMaster.loadImage("downfallResources/images/dailies/gems.png");
    }
}
