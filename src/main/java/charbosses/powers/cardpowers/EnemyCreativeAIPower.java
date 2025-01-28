package charbosses.powers.cardpowers;

import charbosses.bosses.AbstractCharBoss;
import charbosses.cards.AbstractBossCard;
import charbosses.ui.EnemyEnergyPanel;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class EnemyCreativeAIPower extends AbstractPower {
    public static final String POWER_ID = "Creative AI";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;

    public EnemyCreativeAIPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = "Creative AI";
        this.owner = owner;
        this.amount = amount;
        this.updateDescription();
        this.loadRegion("ai");
    }

    public void updateDescription() {
           if (this.amount > 1) {
                 this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[2];
                } else {
                  this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
                 }
         }


    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("Creative AI");
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}

