package gremlin.relics;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class GremlinGravestone extends AbstractGremlinRelic {
    public static final String ID = getID("GremlinGravestone");
    private static final RelicStrings strings = CardCrawlGame.languagePack.getRelicStrings(ID);
    private static final AbstractRelic.RelicTier TIER = RelicTier.RARE;
    private static final String IMG = "relics/gremlin_gravestone.png";
    private static final AbstractRelic.LandingSound SOUND = LandingSound.HEAVY;

    public GremlinGravestone() {
        super(ID, IMG, TIER, SOUND);
    }

    @Override
    public String getUpdatedDescription() {
        return strings.DESCRIPTIONS[0];
    }

}
