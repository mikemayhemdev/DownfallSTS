package slimebound.dailymods;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.daily.mods.AbstractDailyMod;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.RunModStrings;


public class AllSplit extends AbstractDailyMod {
    public static final String ID = "Slimebound:AllSplit";
    public static final String NAME;
    public static final String DESC;
    private static final RunModStrings modStrings;

    static {
        modStrings = CardCrawlGame.languagePack.getRunModString("Slimebound:AllSplit");
        NAME = modStrings.NAME;
        DESC = modStrings.DESCRIPTION;
    }

    public AllSplit() {
        super("Slimebound:AllSplit", NAME, DESC, null, false);
        this.img = ImageMaster.loadImage("slimeboundResources/SlimeboundImages/relics/heartofgoo.png");
    }
}
