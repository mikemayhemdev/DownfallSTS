package guardian.powers;


import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import guardian.actions.PlaceCardsInHandIntoStasisAction;


public class FuturePlansPower extends AbstractGuardianPower {
    public static final String POWER_ID = "Guardian:FuturePlansPower";
    public static PowerType POWER_TYPE = PowerType.BUFF;

    public static String[] DESCRIPTIONS;


    public FuturePlansPower(AbstractCreature owner, int amount) {

        this.ID = POWER_ID;
        this.owner = owner;
        this.setImage("FuturePlans84.png", "FuturePlans32.png");
        this.type = POWER_TYPE;
        this.amount = amount;
        this.DESCRIPTIONS = CardCrawlGame.languagePack.getPowerStrings(this.ID).DESCRIPTIONS;
        this.name = CardCrawlGame.languagePack.getPowerStrings(this.ID).NAME;

        updateDescription();

    }

    public void updateDescription() {
        if (this.amount == 1) {
            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
        } else {
            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[2];
        }

    }

    public void atEndOfTurn(boolean isPlayer) {
        if (isPlayer && !AbstractDungeon.player.hand.isEmpty() && AbstractDungeon.player.hasEmptyOrb()) {
            AbstractDungeon.actionManager.addToBottom(new PlaceCardsInHandIntoStasisAction(this.owner, this.amount));
        }

    }

}



