//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package expansioncontent.powers;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class EvilWithinPower extends AbstractPower {
    public static final String POWER_ID = "expansioncontent:EvilWithinPower";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final int DEBUFF_AMT = 5;
    private int damage;

    public EvilWithinPower(AbstractCreature owner, int damage) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.type = PowerType.BUFF;
        this.amount = 5;
        this.isTurnBased = false;
        this.updateDescription();
        this.loadRegion("sadistic");
    }

    public void updateDescription() {
        if (this.amount == 1) {
            this.description = DESCRIPTIONS[0] + this.damage + DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[3];
        } else {
            this.description = DESCRIPTIONS[0] + this.damage + DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2];
        }

    }

    public void stackPower(int stackAmount) {
        this.fontScale = 8.0F;
        this.damage += stackAmount;
        this.updateDescription();
    }

    public void onApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
        if (power.type == PowerType.DEBUFF && !power.ID.equals("Shackled") && source == this.owner && target != this.owner && !target.hasPower("Artifact")) {
            --this.amount;
            if (this.amount == 0) {
                this.flash();
                this.amount = 5;
                this.addToBot(new DamageAllEnemiesAction(AbstractDungeon.player, DamageInfo.createDamageMatrix(this.damage, true), DamageType.THORNS, AttackEffect.SLASH_DIAGONAL));
            }
            this.updateDescription();
        }
    }
}
