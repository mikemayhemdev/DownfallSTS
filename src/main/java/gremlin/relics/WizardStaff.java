package gremlin.relics;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class WizardStaff extends AbstractGremlinRelic {
    public static final String ID = getID("WizardStaff");
    private static final RelicStrings strings = CardCrawlGame.languagePack.getRelicStrings(ID);
    private static final AbstractRelic.RelicTier TIER = RelicTier.UNCOMMON;
    private static final String IMG = "relics/wizard_staff.png";
    private static final AbstractRelic.LandingSound SOUND = LandingSound.MAGICAL;

    public static final int OOMPH = 7;

    public WizardStaff() {
        super(ID, IMG, TIER, SOUND);
    }

    @Override
    public String getUpdatedDescription() {
        return strings.DESCRIPTIONS[0];
    }
}

