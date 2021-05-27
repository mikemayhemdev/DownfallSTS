package gremlin.relics;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.tempCards.Shiv;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import gremlin.cards.SupplyScrollCard;
import gremlin.cards.Ward;

public class SupplyScroll extends AbstractGremlinRelic {
    public static final String ID = getID("SupplyScroll");
    private static final RelicStrings strings = CardCrawlGame.languagePack.getRelicStrings(ID);
    private static final AbstractRelic.RelicTier TIER = RelicTier.COMMON;
    private static final String IMG = "relics/supply_scroll.png";
    private static final AbstractRelic.LandingSound SOUND = LandingSound.FLAT;

    public static final int SUPPLY = 4;

    public SupplyScroll() {
        super(ID, IMG, TIER, SOUND);
    }

    @Override
    public String getUpdatedDescription() {
        return strings.DESCRIPTIONS[0];
    }

    public void atBattleStart() {
        this.counter = 0;
    }

    public void atTurnStart() {
        if (!this.grayscale) {
            ++this.counter;
        }

        if (this.counter == 3) {
            this.flash();
            this.addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(new SupplyScrollCard()));

            this.counter = -1;
            this.grayscale = true;
        }

    }

    public void onVictory() {
        this.counter = -1;
        this.grayscale = false;
    }
}

