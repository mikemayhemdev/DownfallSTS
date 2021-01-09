package guardian.powers;


import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;


public class RevengePower extends AbstractGuardianPower implements DefensiveModeBooster {
    public static final String POWER_ID = "Guardian:RevengePower";
    public static PowerType POWER_TYPE = PowerType.BUFF;

    public static String[] DESCRIPTIONS;
    private AbstractCreature source;


    public RevengePower(AbstractCreature owner, AbstractCreature source, int amount) {

        this.ID = POWER_ID;
        this.owner = owner;
        this.source = source;
        this.setImage("Revenge84.png", "Revenge32.png");
        this.type = POWER_TYPE;
        this.amount = amount;
        DESCRIPTIONS = CardCrawlGame.languagePack.getPowerStrings(this.ID).DESCRIPTIONS;
        this.name = CardCrawlGame.languagePack.getPowerStrings(this.ID).NAME;

        updateDescription();

    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

    @Override
    public void onEnter() {
        flash();
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(owner, owner, new StrengthPower(owner, amount), amount));

    }

    @Override
    public void onLeave() {
        flash();
        AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(owner, owner, StrengthPower.POWER_ID, amount));
    }
}