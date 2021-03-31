package gremlin.relics;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class FragmentationGrenade extends AbstractGremlinRelic {
    public static final String ID = getID("FragmentationGrenade");
    private static final RelicStrings strings = CardCrawlGame.languagePack.getRelicStrings(ID);
    private static final AbstractRelic.RelicTier TIER = RelicTier.COMMON;
    private static final String IMG = "relics/fragmentation_grenade.png";
    private static final AbstractRelic.LandingSound SOUND = LandingSound.CLINK;

    public static final int OOMPH = 3;

    public FragmentationGrenade() {
        super(ID, IMG, TIER, SOUND);
    }

    @Override
    public String getUpdatedDescription() {
        return strings.DESCRIPTIONS[0] + OOMPH + strings.DESCRIPTIONS[1];
    }
}


