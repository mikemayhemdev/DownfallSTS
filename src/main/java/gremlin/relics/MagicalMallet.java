package gremlin.relics;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import gremlin.powers.WizPower;

public class MagicalMallet extends AbstractGremlinRelic {
    public static final String ID = getID("MagicalMallet");
    private static final RelicStrings strings = CardCrawlGame.languagePack.getRelicStrings(ID);
    private static final AbstractRelic.RelicTier TIER = RelicTier.UNCOMMON;
    private static final String IMG = "relics/magical_mallet.png";
    private static final AbstractRelic.LandingSound SOUND = LandingSound.HEAVY;

    public MagicalMallet() {
        super(ID, IMG, TIER, SOUND);
    }

    @Override
    public String getUpdatedDescription() {
        return strings.DESCRIPTIONS[0];
    }

    public void atTurnStart() {
        this.counter = 3;
    }
    public void onVictory() {
        this.counter = -1;
    }

    public void onTrigger(AbstractCreature target) {
        if (this.counter > 0) {
            this.flash();
            this.addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new WizPower(AbstractDungeon.player, 1), 1));
            this.counter -= 1;
        }
    }
}

