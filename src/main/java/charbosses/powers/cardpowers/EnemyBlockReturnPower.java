package charbosses.powers.cardpowers;


import charbosses.bosses.AbstractCharBoss;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.AbstractPower.PowerType;

public class EnemyBlockReturnPower extends AbstractPower {
    public static final String POWER_ID = "BlockReturnPower";
    private static final PowerStrings powerStrings;

    public EnemyBlockReturnPower(AbstractCreature owner, int blockAmt) {
        this.name = powerStrings.NAME;
        this.ID = "BlockReturnPower";
        this.owner = owner;
        this.amount = blockAmt;
        this.updateDescription();
        this.loadRegion("talk_to_hand");
        this.type = PowerType.DEBUFF;
    }

    public void stackPower(int stackAmount) {
        this.fontScale = 8.0F;
        this.amount += stackAmount;
        this.updateDescription();
    }

    public int onAttacked(DamageInfo info, int damageAmount) {
        if (info.type != DamageType.THORNS && info.type != DamageType.HP_LOSS && info.owner != null && info.owner != this.owner) {
            this.flash();
            this.addToTop(new GainBlockAction(AbstractCharBoss.boss, this.amount, Settings.FAST_MODE));
        }

        return damageAmount;
    }

    public void updateDescription() {
        this.description = powerStrings.DESCRIPTIONS[0] + this.amount + powerStrings.DESCRIPTIONS[1];
    }

    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("BlockReturnPower");
    }
}
