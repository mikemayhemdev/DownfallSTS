//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package charbosses.powers.bossmechanicpowers;

import charbosses.bosses.Hermit.CharBossHermit;
import charbosses.bosses.Hermit.NewAge.ArchetypeAct2WheelOfFateNewAge;
import charbosses.cards.AbstractBossCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class HermitWheelOfFortune extends AbstractTwoAmountBossMechanicPower {
    public static final String POWER_ID = "downfall:HermitWheelOfFortune";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESC;

    public static final int CARDS_TO_STR = 13;

    public HermitWheelOfFortune(AbstractCreature owner) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = 0;
        amount2 = CARDS_TO_STR;
        this.updateDescription();
        loadRegion("curiosity");
        this.type = PowerType.BUFF;
    }

    public void updateDescription() {
        this.description = DESC[0] + CARDS_TO_STR + DESC[1];
    }

    @Override
    public void onCardDraw(AbstractCard card) {
        if (card instanceof AbstractBossCard) {
            amount2 -= 1;
            if (amount2 == 0) {
                amount2 = CARDS_TO_STR;
                addToBot(new ApplyPowerAction(owner, owner, new StrengthPower(owner, 1), 1));
            }
            updateDescription();
        }
    }

    @Override
    public int onAttacked(DamageInfo info, int damageAmount) {
        if (this.owner instanceof CharBossHermit && info.type == DamageInfo.DamageType.NORMAL) {
            if (((CharBossHermit) this.owner).chosenArchetype instanceof ArchetypeAct2WheelOfFateNewAge) {
                flash();
                ((ArchetypeAct2WheelOfFateNewAge) ((CharBossHermit) this.owner).chosenArchetype).reInitializeHand();
                amount2 -= 1;
                if (amount2 == 0) {
                    amount2 = CARDS_TO_STR;
                    addToBot(new ApplyPowerAction(owner, owner, new StrengthPower(owner, 1), 1));
                }
                updateDescription();
            }
        }
        return damageAmount;
    }

    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
        NAME = powerStrings.NAME;
        DESC = powerStrings.DESCRIPTIONS;
    }
}
