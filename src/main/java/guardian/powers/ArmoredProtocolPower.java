package guardian.powers;


import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.MetallicizePower;
import guardian.stances.DefensiveMode;


public class ArmoredProtocolPower extends AbstractGuardianPower {
    public static final String POWER_ID = "Guardian:ArmoredProtocolPower";
    public static PowerType POWER_TYPE = PowerType.BUFF;
    public static String[] DESCRIPTIONS;

    public ArmoredProtocolPower(AbstractCreature owner, int amount) {

        this.ID = POWER_ID;
        this.owner = owner;
        this.setImage("OffenseModePower84.png", "OffenseModePower32.png");
        this.type = POWER_TYPE;
        DESCRIPTIONS = CardCrawlGame.languagePack.getPowerStrings(this.ID).DESCRIPTIONS;

        this.name = CardCrawlGame.languagePack.getPowerStrings(this.ID).NAME;
        this.amount = amount;

        updateDescription();
    }


    public void atEndOfTurnPreEndTurnCards(boolean isPlayer) {
        flash();
        if (AbstractDungeon.player.stance instanceof DefensiveMode) {
            addToBot(new com.megacrit.cardcrawl.actions.common.GainBlockAction(this.owner, this.owner, this.amount));
        }
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }
}
