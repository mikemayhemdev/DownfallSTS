//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package charbosses.powers.bossmechanicpowers;

import charbosses.cards.AbstractBossCard;
import charbosses.powers.cardpowers.EnemyAccuracyPower;
import charbosses.powers.cardpowers.EnemyMantraPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.combat.TimeWarpTurnEndEffect;

public class WatcherDivinityPower extends AbstractBossMechanicPower {
    public static final String POWER_ID = "downfall:WatcherDivinityPower";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESC;

    public WatcherDivinityPower(AbstractCreature owner) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = 0;
        this.updateDescription();
        loadRegion("curiosity");
        this.type = PowerType.BUFF;
    }

    @Override
    public void atStartOfTurn() {
        addToBot(new ReducePowerAction(this.owner, this.owner, EnemyMantraPower.POWER_ID, 5));
    }

    public void updateDescription() {
        this.description = DESC[0];
    }

    public void onAfterUseCard(AbstractCard card, UseCardAction action) {
        if (!(card instanceof AbstractBossCard)) {
            this.addToBot(new ApplyPowerAction(this.owner, this.owner, new EnemyMantraPower(this.owner, 1), 1));

        }
    }

    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
        NAME = powerStrings.NAME;
        DESC = powerStrings.DESCRIPTIONS;
    }
}
