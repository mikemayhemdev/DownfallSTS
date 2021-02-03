package gremlin.relics;

import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import gremlin.actions.MakeEchoAction;

public class StolenMerchandise extends AbstractGremlinRelic {
    private static final String ID = getID("StolenMerchandise");
    private static final RelicStrings strings = CardCrawlGame.languagePack.getRelicStrings(ID);
    private static final AbstractRelic.RelicTier TIER = RelicTier.SHOP;
    private static final String IMG = "relics/stolen_merchandise.png";
    private static final AbstractRelic.LandingSound SOUND = LandingSound.HEAVY;


    public StolenMerchandise() {
        super(ID, IMG, TIER, SOUND);
    }

    @Override
    public String getUpdatedDescription() {
        return strings.DESCRIPTIONS[0];
    }

    @Override
    public int getPrice() {
        return 0;
    }

    @Override
    public void onGremlinSwap() {
        this.flash();
        AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));

        AbstractCard colorless = AbstractDungeon.returnTrulyRandomColorlessCardInCombat();
        AbstractDungeon.actionManager.addToBottom(new MakeEchoAction(colorless));
    }
}

