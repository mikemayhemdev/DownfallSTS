package gremlin.relics;

import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.AddTemporaryHPAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import gremlin.powers.WizPower;

public class ImpeccablePecs extends AbstractGremlinRelic {
    public static final String ID = getID("ImpeccablePecs");
    private static final RelicStrings strings = CardCrawlGame.languagePack.getRelicStrings(ID);
    private static final AbstractRelic.RelicTier TIER = RelicTier.UNCOMMON;
    private static final String IMG = "relics/impeccable_pecs.png";
    private static final AbstractRelic.LandingSound SOUND = LandingSound.SOLID;

    public ImpeccablePecs() {
        super(ID, IMG, TIER, SOUND);
    }

    // Rework this Relic at some point
    // I mean I also want to rework this, it's very weird
    // Removed to-do, made grems exclusive instead. Not worth the effort.


    @Override
    public String getUpdatedDescription() {
        return strings.DESCRIPTIONS[0];
    }

    public void atBattleStart() {
        this.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new StrengthPower(AbstractDungeon.player, 1), 1));
    }

    public void onTrigger(int amount) {
        if (amount > 0) {
            this.flash();
            AbstractDungeon.actionManager.addToBottom(new AddTemporaryHPAction(AbstractDungeon.player, AbstractDungeon.player, amount));
        }
    }
}


