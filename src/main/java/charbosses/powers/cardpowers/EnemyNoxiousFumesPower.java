package charbosses.powers.cardpowers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.NoxiousFumesPower;
import charbosses.powers.general.EnemyPoisonPower;

public class EnemyNoxiousFumesPower extends AbstractPower {
    public static final String POWER_ID = "downfall:Enemy Noxious Fumes";
    private static final PowerStrings powerStrings;

    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings(NoxiousFumesPower.POWER_ID);
    }

    public EnemyNoxiousFumesPower(final AbstractCreature owner, final int bladeAmt) {
        this.name = EnemyNoxiousFumesPower.powerStrings.NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = bladeAmt;
        this.updateDescription();
        this.loadRegion("fumes");
    }

    @Override
    public void atStartOfTurn() {
        if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {// 27
            this.flash();// 28
            addToBot(new ApplyPowerAction(AbstractDungeon.player, owner, new EnemyPoisonPower(AbstractDungeon.player, owner, amount), amount));
        }
    }

    @Override
    public void stackPower(final int stackAmount) {
        this.fontScale = 8.0f;
        this.amount += stackAmount;
    }

    @Override
    public void updateDescription() {
        this.description = EnemyNoxiousFumesPower.powerStrings.DESCRIPTIONS[0] + this.amount + EnemyNoxiousFumesPower.powerStrings.DESCRIPTIONS[1];
    }
}
