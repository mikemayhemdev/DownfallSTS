package charbosses.powers.bossmechanicpowers;

import charbosses.bosses.AbstractCharBoss;
import charbosses.bosses.Hermit.CharBossHermit;
import charbosses.bosses.Hermit.NewAge.ArchetypeAct2WheelOfFateNewAge;
import charbosses.bosses.Hermit.Simpler.ArchetypeAct2WheelOfFateSimple;
import charbosses.cards.AbstractBossCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import downfall.downfallMod;
import expansioncontent.expansionContentMod;

public class HermitWheelOfFortune extends AbstractBossMechanicPower {
    public static final String POWER_ID = "downfall:HermitWheelOfFortune";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESC;

    public HermitWheelOfFortune(AbstractCreature owner) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        if (downfallMod.useLegacyBosses){
            this.amount = 2;
        } else {
            this.amount = 0;
        }
        this.updateDescription();
        loadRegion("curiosity");
        this.type = PowerType.BUFF;
    }

    public void updateDescription() {
        if (!downfallMod.useLegacyBosses){
            this.description = DESC[1];
        } else {
            this.description = DESC[0];
        }
    }

    @Override
    public void onAfterUseCard(AbstractCard card, UseCardAction action) {
        if (this.owner instanceof CharBossHermit && !(card instanceof AbstractBossCard) && card.type == AbstractCard.CardType.ATTACK) {
            if (((CharBossHermit) this.owner).chosenArchetype instanceof ArchetypeAct2WheelOfFateNewAge || (((CharBossHermit) this.owner).chosenArchetype instanceof ArchetypeAct2WheelOfFateSimple && ((CharBossHermit) this.owner).chosenArchetype.turn >= 1)) {
                addToBot(new ReInitializeHandAction(this.owner, this));
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



class ReInitializeHandAction extends AbstractGameAction {
    private final AbstractPower power;

    ReInitializeHandAction(AbstractCreature hermit, AbstractPower power) {
        this.source = hermit;
        this.target = hermit;
        this.power = power;
    }

    @Override
    public void update() {
        if (!this.shouldCancelAction()) {
            power.flash();
            if (AbstractCharBoss.boss.chosenArchetype instanceof ArchetypeAct2WheelOfFateNewAge) ((ArchetypeAct2WheelOfFateNewAge) ((CharBossHermit) source).chosenArchetype).reInitializeHand();
            if (AbstractCharBoss.boss.chosenArchetype instanceof ArchetypeAct2WheelOfFateSimple) ((ArchetypeAct2WheelOfFateSimple) ((CharBossHermit) source).chosenArchetype).reInitializeHand();

        }
        isDone = true;
    }
}
