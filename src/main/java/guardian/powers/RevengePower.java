package guardian.powers;


import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.OnMyBlockBrokenPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;


public class RevengePower extends AbstractGuardianTwoAmountPower {
    public static final String POWER_ID = "Guardian:RevengePower";
    public static PowerType POWER_TYPE = PowerType.BUFF;

    public static String[] DESCRIPTIONS;
    private AbstractCreature source;

    public int usedThisTurn = 0;


    public RevengePower(AbstractCreature owner, AbstractCreature source, int amount) {

        this.ID = POWER_ID;
        this.owner = owner;
        this.source = source;
        this.setImage("Revenge84.png", "Revenge32.png");
        this.type = POWER_TYPE;
        this.amount = amount;
        this.amount2 = 0;
        this.DESCRIPTIONS = CardCrawlGame.languagePack.getPowerStrings(this.ID).DESCRIPTIONS;
        this.name = CardCrawlGame.languagePack.getPowerStrings(this.ID).NAME;

        updateDescription();

    }


    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1] + (this.amount2 * this.amount) + DESCRIPTIONS[2];

    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        super.atEndOfTurn(isPlayer);
        this.amount2++;
        updateDescription();
    }

    @Override
    public void onRemove() {
        int strengthgain = this.amount2 * this.amount;
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.owner, this.owner, new StrengthPower(this.owner, strengthgain), strengthgain));

    }
}