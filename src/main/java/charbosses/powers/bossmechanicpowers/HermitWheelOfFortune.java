package charbosses.powers.bossmechanicpowers;

import charbosses.bosses.Hermit.CharBossHermit;
import charbosses.bosses.Hermit.NewAge.ArchetypeAct2WheelOfFateNewAge;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class HermitWheelOfFortune extends AbstractBossMechanicPower {
    public static final String POWER_ID = "downfall:HermitWheelOfFortune";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESC;

    public HermitWheelOfFortune(AbstractCreature owner) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = 2;
        this.updateDescription();
        loadRegion("curiosity");
        this.type = PowerType.BUFF;
    }

    public void updateDescription() {
        this.description = DESC[0];
    }

    @Override
    public void onAfterUseCard(AbstractCard card, UseCardAction action) {
        if (this.owner instanceof CharBossHermit && card.type == AbstractCard.CardType.ATTACK) {
            if (((CharBossHermit) this.owner).chosenArchetype instanceof ArchetypeAct2WheelOfFateNewAge) {
                flash();
                ((ArchetypeAct2WheelOfFateNewAge) ((CharBossHermit) this.owner).chosenArchetype).reInitializeHand();
            }
        }
    }

    @Override
    public void onSpecificTrigger() {
        flash();
        addToBot(new ApplyPowerAction(owner, owner, new StrengthPower(owner, amount), amount));
    }

    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
        NAME = powerStrings.NAME;
        DESC = powerStrings.DESCRIPTIONS;
    }
}
