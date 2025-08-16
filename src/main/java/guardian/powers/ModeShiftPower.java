package guardian.powers;

import champ.powers.EnergizedDurationPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.BufferPower;
import com.megacrit.cardcrawl.powers.EnergizedBluePower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import guardian.relics.ModeShifterPlus;
import guardian.stances.DefensiveMode;
import sneckomod.relics.LoadedDie;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.actionManager;


public class ModeShiftPower extends AbstractGuardianPower {
    public static final String POWER_ID = "Guardian:ModeShiftPower";
    private static final int STARTING_AMOUNT = 20;
    private static final int AMOUNT_GAIN_PER_ACTIVATION = 10;
    private static final int MAX_AMOUNT = 50;
    private static final int BLOCK_ON_TRIGGER = 16;
    public static PowerType POWER_TYPE = PowerType.BUFF;
    public static String[] DESCRIPTIONS;
    private AbstractCreature source;
    private boolean active;
    private int activations = 0;
    private int nextamount = 0;

    public ModeShiftPower(AbstractCreature owner, AbstractCreature source, int amount) {
        this.ID = POWER_ID;
        this.owner = owner;
        this.source = source;
        this.loadRegion("modeShift");
        this.type = POWER_TYPE;
        this.amount = STARTING_AMOUNT;
        this.active = true;
        this.nextamount = STARTING_AMOUNT + (this.activations * AMOUNT_GAIN_PER_ACTIVATION);
        DESCRIPTIONS = CardCrawlGame.languagePack.getPowerStrings(this.ID).DESCRIPTIONS;
        this.name = CardCrawlGame.languagePack.getPowerStrings(this.ID).NAME;
        updateDescription();
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

    public void onSpecificTrigger(int brace) {
        this.amount -= brace;
        updateDescription();
        flash();
        if (this.amount <= 0) {
            actionManager.addToBottom(new GainBlockAction(this.owner, this.owner, BLOCK_ON_TRIGGER));
            actionManager.addToBottom(new ChangeStanceAction(DefensiveMode.STANCE_ID));

            ModeShifterPlus modeShifterPlusInstance = new ModeShifterPlus();
            if (AbstractDungeon.player.hasRelic(ModeShifterPlus.ID)) {
                modeShifterPlusInstance.onTrigger();
            }


        int turns;
         if (AbstractDungeon.actionManager.turnHasEnded)
               turns = 2;
        else
               turns = 1;
            AbstractPlayer p = AbstractDungeon.player;

            actionManager.addToBottom(new ApplyPowerAction(p, p, new DontLeaveDefensiveModePower(p, turns), turns));

            if (AbstractDungeon.player.hasPower(RevengePower.POWER_ID)) {
                RevengePower revengePower =
                        (RevengePower) AbstractDungeon.player.getPower(RevengePower.POWER_ID);

                if (revengePower != null) {
                    revengePower.onActivateCallR(p);
                }
            }

                if (AbstractDungeon.player.hasPower(SpikerProtocolPower.POWER_ID)) {
                    SpikerProtocolPower spikerProtocolPower =
                            (SpikerProtocolPower) AbstractDungeon.player.getPower(SpikerProtocolPower.POWER_ID);

                    if (spikerProtocolPower != null) {
                        spikerProtocolPower.onActivateCallS(p);
                    }
                }


                    if (AbstractDungeon.player.hasPower(EvasiveProtocolPower.POWER_ID)) {
                        EvasiveProtocolPower evasiveProtocolPower =
                                (EvasiveProtocolPower) AbstractDungeon.player.getPower(EvasiveProtocolPower.POWER_ID);

                        if (evasiveProtocolPower != null) {
                            evasiveProtocolPower.onActivateCallE(p);
                        }


                    }



            this.activations++;
            this.amount += Math.min(STARTING_AMOUNT + (AMOUNT_GAIN_PER_ACTIVATION * activations), MAX_AMOUNT); //Set max of 40 Brace
            updateDescription();
            if (this.amount <= 0){
                onSpecificTrigger(0);
            }

        }
    }

    @Override
    public int onLoseHp(int damageAmount) {

        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT && this.active && !AbstractDungeon.player.hasPower(BufferPower.POWER_ID)) {
            onSpecificTrigger(damageAmount);
        }

        return super.onLoseHp(damageAmount);
    }
}