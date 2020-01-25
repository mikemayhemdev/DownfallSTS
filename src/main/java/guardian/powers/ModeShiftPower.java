package guardian.powers;


import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.BlurPower;
import com.megacrit.cardcrawl.powers.BufferPower;
import guardian.actions.SwitchToDefenseModeAction;
import guardian.cards.ConstructionForm;


public class ModeShiftPower extends AbstractGuardianPower {
    public static final String POWER_ID = "Guardian:ModeShiftPower";
    public static PowerType POWER_TYPE = PowerType.BUFF;

    public static String[] DESCRIPTIONS;
    private AbstractCreature source;
    private boolean active;
    private static final int STARTINGAMOUNT = 10;
    private static final int AMOUNTGAINPERACTIVATION = 5;
    private static final int BLOCKONTRIGGER = 10;
    private int activations = 0;
    private int nextamount = 0;

    public ModeShiftPower(AbstractCreature owner, AbstractCreature source, int amount) {

        this.ID = POWER_ID;
        this.owner = owner;
        this.source = source;
        this.loadRegion("modeShift");
        this.type = POWER_TYPE;
        this.amount = STARTINGAMOUNT;
        this.active = true;
        this.nextamount = this.STARTINGAMOUNT + (this.activations * AMOUNTGAINPERACTIVATION);
        this.DESCRIPTIONS = CardCrawlGame.languagePack.getPowerStrings(this.ID).DESCRIPTIONS;
        this.name = CardCrawlGame.languagePack.getPowerStrings(this.ID).NAME;

        updateDescription();

    }

    public void updateDescription() {
        if (this.active){
            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];

        } else {
            this.description = DESCRIPTIONS[2];

        }

    }

    @Override
    public int onLoseHp(int damageAmount) {
        if (this.active && !AbstractDungeon.player.hasPower(ConstructModePower.POWER_ID) && !AbstractDungeon.player.hasPower(BufferPower.POWER_ID)) {
            this.amount -= damageAmount;
            if (this.amount <= 0) {
                this.amount = 0;
                this.active = false;
                AbstractDungeon.actionManager.addToBottom(new GainBlockAction(this.owner, this.owner, BLOCKONTRIGGER));

                AbstractDungeon.actionManager.addToBottom(new SwitchToDefenseModeAction(AbstractDungeon.player));
                AbstractDungeon.actionManager.addToBottom(new SwitchToDefenseModeAction(AbstractDungeon.player));
                AbstractDungeon.actionManager.addToBottom(new SwitchToDefenseModeAction(AbstractDungeon.player));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new BlurPower(AbstractDungeon.player, 1), 1));

                this.activations++;

            }
        }

        return super.onLoseHp(damageAmount);
    }

    @Override
    public void atStartOfTurn() {
        super.atStartOfTurn();
        if (!this.active) {
            this.active = true;
            this.amount = this.STARTINGAMOUNT + (this.activations * AMOUNTGAINPERACTIVATION);
        }
    }
}