//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package charbosses.powers.bossmechanicpowers;

import charbosses.actions.unique.EnemyChangeStanceAction;
import charbosses.bosses.AbstractCharBoss;
import charbosses.cards.AbstractBossCard;
import charbosses.stances.EnCalmStance;
import charbosses.stances.EnRealWrathStance;
import charbosses.stances.EnWrathStance;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.LoseDexterityPower;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import static awakenedOne.util.Wiz.atb;

public class WatcherTrackerSkillsPower extends AbstractBossMechanicPower {
    public static final String POWER_ID = "downfall:WatcherTrackerSkillsPower";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;

    private boolean used = false;

    public WatcherTrackerSkillsPower(AbstractCreature owner, int newAmount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = newAmount;
        this.updateDescription();
        loadRegion("curiosity");
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

    @Override
    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
        this.updateDescription();
    }

    @Override
    public void onAfterUseCard(final AbstractCard card, final UseCardAction action) {
        if (card instanceof AbstractBossCard) {
            return;
        }
        if (!card.type.equals(AbstractCard.CardType.ATTACK)) {
            this.flash();
            stackPower(1);
            if (owner.hasPower(WatcherTrackerAttacksPower.POWER_ID)){
                if (owner.getPower(WatcherTrackerAttacksPower.POWER_ID).amount <= this.amount){
                    atb(new EnemyChangeStanceAction(EnCalmStance.STANCE_ID));
                }
            }
        }

    }



    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}
