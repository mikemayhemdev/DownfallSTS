package guardian.powers;


import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import guardian.actions.ReduceRightMostStasisAction;


public class AutomayhemPower extends AbstractGuardianPower {
    public static final String POWER_ID = "Guardian:AutomayhemPower";
    public static PowerType POWER_TYPE = PowerType.BUFF;

    public static String[] DESCRIPTIONS;
    private AbstractCreature source;


    public AutomayhemPower(AbstractCreature owner, AbstractCreature source, int amount) {

        this.ID = POWER_ID;
        this.owner = owner;
        this.source = source;
        this.setImage("TimeSifter84.png", "TimeSifter32.png");
        this.type = POWER_TYPE;
        this.amount = amount;
        DESCRIPTIONS = CardCrawlGame.languagePack.getPowerStrings(this.ID).DESCRIPTIONS;
        this.name = CardCrawlGame.languagePack.getPowerStrings(this.ID).NAME;

        updateDescription();

    }

    public void updateDescription() {
        if (this.amount == 1) {
            this.description = DESCRIPTIONS[0];

        } else {
            this.description = "#b" + this.amount + DESCRIPTIONS[1];

        }

    }

    @Override
    public void atStartOfTurnPostDraw() {
        super.atStartOfTurnPostDraw();

            if (AbstractDungeon.player.orbs.size() > 0) {

                for (int i = 0; i < this.amount; i++) {
                    AbstractDungeon.actionManager.addToBottom(new ReduceRightMostStasisAction(true));

                }

            }

    }

}