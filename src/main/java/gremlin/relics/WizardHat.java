package gremlin.relics;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import gremlin.actions.RemoveRandomDebuffAction;

public class WizardHat extends AbstractGremlinRelic {
    public static final String ID = getID("WizardHat");
    private static final RelicStrings strings = CardCrawlGame.languagePack.getRelicStrings(ID);
    private static final AbstractRelic.RelicTier TIER = RelicTier.COMMON;
    private static final String IMG = "relics/wizard_hat.png";
    private static final AbstractRelic.LandingSound SOUND = LandingSound.MAGICAL;

    public WizardHat() {
        super(ID, IMG, TIER, SOUND);
    }

    @Override
    public String getUpdatedDescription() {
        return strings.DESCRIPTIONS[0];
    }

    public void onTrigger() {
        this.flash();
        this.addToBot(new RemoveRandomDebuffAction(AbstractDungeon.player));
    }
}
