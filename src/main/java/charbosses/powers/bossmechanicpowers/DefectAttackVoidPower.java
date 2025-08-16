//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package charbosses.powers.bossmechanicpowers;

import collector.powers.DoomPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hermit.util.Wiz;

public class DefectAttackVoidPower extends AbstractBossMechanicPower {
    public static final String POWER_ID = "downfall:DefectAttackVoidPower";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESC;

    public DefectAttackVoidPower(AbstractCreature owner) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = 0;
        this.updateDescription();
        loadRegion("curiosity");
        this.type = PowerType.BUFF;
    }


    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target) {
        if (damageAmount > 0 && target != this.owner && info.type == DamageInfo.DamageType.NORMAL) {
            this.flash();
            Wiz.atb(new MakeTempCardInDiscardAction(new VoidCard(), 1));
        }
    }


    public void updateDescription() {
        this.description = DESC[0];
    }

    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
        NAME = powerStrings.NAME;
        DESC = powerStrings.DESCRIPTIONS;
    }
}
