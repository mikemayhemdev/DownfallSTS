package gremlin.relics;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import gremlin.actions.GremlinSwapAction;

public class GremlinKnob extends AbstractGremlinRelic {
    public static final String ID = getID("GremlinKnob");
    private static final RelicStrings strings = CardCrawlGame.languagePack.getRelicStrings(ID);
    private static final AbstractRelic.RelicTier TIER = RelicTier.STARTER;
    private static final String IMG = "relics/gremlinKnob.png";
    private static final AbstractRelic.LandingSound SOUND = LandingSound.FLAT;


    public GremlinKnob() {
        super(ID, IMG, TIER, SOUND);
    }

    @Override
    public String getUpdatedDescription() {
        return strings.DESCRIPTIONS[0];
    }

    @Override
    public void atBattleStart() {
        this.flash();
        AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(1));
        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(AbstractDungeon.player, 1));
        AbstractDungeon.actionManager.addToBottom(new GremlinSwapAction(true));
    }

//    @Override
//    public void onMonsterDeath(final AbstractMonster m) {
//        if (m.currentHealth == 0 && !AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
//            this.flash();
//            AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(m, this));
//            AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(1));
//            AbstractDungeon.actionManager.addToBottom(new DrawCardAction(AbstractDungeon.player, 1));
//            AbstractDungeon.actionManager.addToBottom(new GremlinSwapAction(true));
//        }
//    }
}

