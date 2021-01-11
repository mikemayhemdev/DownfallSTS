package downfall.dailymods;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.daily.mods.AbstractDailyMod;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.RunModStrings;
import downfall.downfallMod;


public class Analytical extends AbstractDailyMod {
    public static final String ID = downfallMod.makeID("Analytical");
    public static final String NAME;
    public static final String DESC;
    private static final RunModStrings modStrings;

    static {
        modStrings = CardCrawlGame.languagePack.getRunModString(ID);
        NAME = modStrings.NAME;
        DESC = modStrings.DESCRIPTION;
    }

    public Analytical() {
        super(ID, NAME, DESC, null, false);
        this.img = ImageMaster.loadImage("downfallResources/images/dailies/analytical.png");
    }
}
