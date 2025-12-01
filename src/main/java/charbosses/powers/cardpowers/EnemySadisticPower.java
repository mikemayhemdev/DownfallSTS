//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package charbosses.powers.cardpowers;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.AbstractPower.PowerType;
import com.megacrit.cardcrawl.powers.FrailPower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.relics.Ginger;
import com.megacrit.cardcrawl.relics.Turnip;
import downfall.actions.WaitForEscapeAction;

public class EnemySadisticPower extends AbstractPower {
    public static final String POWER_ID = "Sadistic";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;

    public EnemySadisticPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = "Sadistic";
        this.owner = owner;
        this.amount = amount;
        this.updateDescription();
        this.loadRegion("sadistic");
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

    public void onApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
        if (!(power.ID == WeakPower.POWER_ID && AbstractDungeon.player.hasRelic(Ginger.ID)) && !(power.ID == FrailPower.POWER_ID && AbstractDungeon.player.hasRelic(Turnip.ID))) {
            if (power.type == PowerType.DEBUFF && !power.ID.equals("Shackled") && source == this.owner && target != this.owner && !target.hasPower("Artifact")) {
                this.flash();
                this.addToBot(new DamageAction(target, new DamageInfo(this.owner, this.amount, DamageType.THORNS), AttackEffect.FIRE));
            }
        }

    }

    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("Sadistic");
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}
