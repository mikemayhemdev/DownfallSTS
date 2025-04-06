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

public class EnemyElectroPower extends AbstractPower {
    public static final String POWER_ID = "Electro";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;

    public EnemyElectroPower(AbstractCreature owner) {
        this.name = NAME;
        this.ID = "Electro";
        this.owner = owner;
        this.updateDescription();
        this.loadRegion("mastery");
    }

    public void updateDescription() {
        /* 23 */     this.description = DESCRIPTIONS[0];
        /*    */   }


    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("Electro");
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}

