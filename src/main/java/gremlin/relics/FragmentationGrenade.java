package gremlin.relics;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class FragmentationGrenade extends AbstractGremlinRelic {
    public static final String ID = getID("FragmentationGrenade");
    private static final RelicStrings strings = CardCrawlGame.languagePack.getRelicStrings(ID);
    private static final AbstractRelic.RelicTier TIER = RelicTier.UNCOMMON;
    private static final String IMG = "relics/fragmentation_grenade.png";
    private static final AbstractRelic.LandingSound SOUND = LandingSound.CLINK;

    public static final int OOMPH = 3;

    public FragmentationGrenade() {
        super(ID, IMG, TIER, SOUND);
    }

//    public float atDamageModify(float damage, AbstractCard c) {
//        return c.costForTurn != 0 && (!c.freeToPlayOnce || c.cost == -1) ? damage : damage + 2.0F;
//    }

    @Override
    public String getUpdatedDescription() {
        return strings.DESCRIPTIONS[0] + OOMPH + strings.DESCRIPTIONS[1];
    }
}


