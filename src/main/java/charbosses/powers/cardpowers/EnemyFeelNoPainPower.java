//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package charbosses.powers.cardpowers;

import charbosses.cards.AbstractBossCard;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class EnemyFeelNoPainPower extends AbstractPower {
    public static final String POWER_ID = "Feel No Pain";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;

    public EnemyFeelNoPainPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = "Feel No Pain";
        this.owner = owner;
        this.amount = amount;
        this.updateDescription();
        this.loadRegion("noPain");
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

    public void onExhaust(AbstractCard card) {
        if (card instanceof AbstractBossCard) {
            this.flash();
            this.addToBot(new GainBlockAction(this.owner, this.amount, Settings.FAST_MODE));
        }
    }

    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("Feel No Pain");
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}
