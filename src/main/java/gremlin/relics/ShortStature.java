package gremlin.relics;

import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class ShortStature extends AbstractGremlinRelic {
    public static final String ID = getID("ShortStature");
    private static final RelicStrings strings = CardCrawlGame.languagePack.getRelicStrings(ID);
    private static final AbstractRelic.RelicTier TIER = RelicTier.RARE;
    private static final String IMG = "relics/short_stature.png";
    private static final AbstractRelic.LandingSound SOUND = LandingSound.FLAT;

    public ShortStature() {
        super(ID, IMG, TIER, SOUND);
        this.counter = -1;
    }

    @Override
    public String getUpdatedDescription() {
        if (this.counter == -1) {
            return strings.DESCRIPTIONS[0];
        }
        else {
            return strings.DESCRIPTIONS[1];
        }
    }


    public void atBattleStart() {
        this.counter = -1;
        this.grayscale = false;
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, getUpdatedDescription()));
        this.initializeTips();
    }

    public void onTrigger() {
        this.flash();
        this.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        int healAmt = AbstractDungeon.player.maxHealth / 2;
        if (healAmt < 1) {
            healAmt = 1;
        }

        AbstractDungeon.player.heal(healAmt, true);
        this.counter = -2;
        this.grayscale = true;
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, getUpdatedDescription()));
        this.initializeTips();
    }
}


