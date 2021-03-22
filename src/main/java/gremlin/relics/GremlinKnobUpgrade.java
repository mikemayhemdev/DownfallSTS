package gremlin.relics;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import gremlin.actions.GremlinSwapAction;

public class GremlinKnobUpgrade extends AbstractGremlinRelic {
    public static final String ID = getID("GremlinKnobUpgrade");
    private static final RelicStrings strings = CardCrawlGame.languagePack.getRelicStrings(ID);
    private static final AbstractRelic.RelicTier TIER = RelicTier.BOSS;
    private static final String IMG = "relics/gremlinKnobUpgrade.png";
    private static final AbstractRelic.LandingSound SOUND = LandingSound.FLAT;

    public GremlinKnobUpgrade() {
        super(ID, IMG, TIER, SOUND);
    }

    @Override
    public String getUpdatedDescription() {
        return strings.DESCRIPTIONS[0] + colorifyName(new GremlinKnob().name, 'r') +strings.DESCRIPTIONS[1];
    }

    private static String colorifyName(String s, char color) {
        StringBuilder sb = new StringBuilder();
        for (String part:s.split(" ")) {
            sb.append("#");
            sb.append(color);
            sb.append(part);
            sb.append(" ");
        }
        sb.setLength(sb.length() - 1);
        return sb.toString();
    }

    @Override
    public void obtain() {
        if (AbstractDungeon.player.hasRelic(GremlinKnob.ID)) {
            for (int i = 0; i < AbstractDungeon.player.relics.size(); ++i) {
                if (AbstractDungeon.player.relics.get(i).relicId.equals(GremlinKnob.ID)) {
                    instantObtain(AbstractDungeon.player, i, true);
                    break;
                }
            }
        } else {
            super.obtain();
        }
    }

    public boolean canSpawn() {
        return AbstractDungeon.player.hasRelic(GremlinKnob.ID);
    }

    public void atTurnStart() {
        this.counter = -1;
        this.grayscale = false;
    }

    @Override
    public void onShuffle() {
        if (this.counter == -1) {
            flash();
            AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(1));
            AbstractDungeon.actionManager.addToBottom(new DrawCardAction(AbstractDungeon.player, 1));
            AbstractDungeon.actionManager.addToBottom(new GremlinSwapAction(true));
            this.counter = -2;
            this.grayscale = true;
        }
    }
}

