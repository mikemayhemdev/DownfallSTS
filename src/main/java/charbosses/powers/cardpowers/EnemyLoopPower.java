package charbosses.powers.cardpowers;

import charbosses.bosses.AbstractCharBoss;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class EnemyLoopPower extends AbstractPower {
    public static final String POWER_ID = "Loop";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;

    public EnemyLoopPower(AbstractCreature owner, int amt) {
        this.name = NAME;
        this.ID = "Loop";
        this.owner = owner;
        this.amount = amt;
        this.updateDescription();
        this.loadRegion("loop");
    }

    public void atStartOfTurn() {
        if (!AbstractCharBoss.boss.orbs.isEmpty()) {
            this.flash();

            for(int i = 0; i < this.amount; ++i) {
                ((AbstractOrb)AbstractCharBoss.boss.orbs.get(0)).onStartOfTurn();
                ((AbstractOrb)AbstractCharBoss.boss.orbs.get(0)).onEndOfTurn();
            }
        }

    }

    public void updateDescription() {
        if (this.amount <= 1) {
            this.description = DESCRIPTIONS[0];
        } else {
            this.description = DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2];
        }
    }


    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("Loop");
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}
