package guardian.powers;


import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.powers.ThornsPower;
import guardian.relics.ObsidianScales;
import sneckomod.relics.LoadedDie;


public class LoseThornsPower extends AbstractGuardianPower {
    public static final String POWER_ID = "Guardian:LoseThornsPower";
    public static PowerType POWER_TYPE = PowerType.DEBUFF;

    public static String[] DESCRIPTIONS;

    public LoseThornsPower(AbstractCreature owner, int amount) {

        this.ID = POWER_ID;
        this.owner = owner;

        this.setImage("ThornsDown84.png", "ThornsDown32.png");

        this.type = POWER_TYPE;

        this.amount = amount;
        DESCRIPTIONS = CardCrawlGame.languagePack.getPowerStrings(this.ID).DESCRIPTIONS;

        this.name = CardCrawlGame.languagePack.getPowerStrings(this.ID).NAME;

        updateDescription();

    }


    public void updateDescription() {


        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];


    }


    public void atStartOfTurn() {

        flash();

        AbstractDungeon.actionManager.addToBottom(new com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction(this.owner, this.owner, LoseThornsPower.POWER_ID));

        if (this.owner.hasPower("Thorns")) {
            if (!this.owner.hasPower(ArtifactPower.POWER_ID)) {
                if (this.owner.getPower("Thorns").amount <= this.amount) {
                    AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, "Thorns"));
                } else {
                    AbstractDungeon.player.getPower(ThornsPower.POWER_ID).stackPower(this.amount * -1);

                }
            } else {
                this.owner.getPower(ArtifactPower.POWER_ID).onSpecificTrigger();
            }
            ObsidianScales OScalesInstance = new ObsidianScales();
            if (AbstractDungeon.player.hasRelic(ObsidianScales.ID)) {
                this.addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new ThornsPower(AbstractDungeon.player, 1), 1));
                OScalesInstance.flash();
            }
        }
    }

}



