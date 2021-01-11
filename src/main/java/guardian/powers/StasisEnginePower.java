package guardian.powers;


import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;


public class StasisEnginePower extends AbstractGuardianTwoAmountPower {
    public static final String POWER_ID = "Guardian:StasisEnginePower";
    public static PowerType POWER_TYPE = PowerType.BUFF;

    public static String[] DESCRIPTIONS;

    private boolean usedThisTurn  = false;

    public StasisEnginePower(AbstractCreature owner) {

        this.ID = POWER_ID;
        this.owner = owner;
        this.setImage("StasisEngine84.png", "StasisEngine32.png");
        this.type = POWER_TYPE;
        this.amount = 1;
        this.amount2 = 0;
        DESCRIPTIONS = CardCrawlGame.languagePack.getPowerStrings(this.ID).DESCRIPTIONS;
        this.name = CardCrawlGame.languagePack.getPowerStrings(this.ID).NAME;

        updateDescription();
    }


    public void updateDescription() {
        if (this.amount == 1) {
            this.description = DESCRIPTIONS[0] + DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2] + DESCRIPTIONS[4];
        } else {
            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[3] + DESCRIPTIONS[4];

        }

    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        super.onUseCard(card, action);
        if (!usedThisTurn) {
            if (card.cost == 0 || card.freeToPlayOnce || (card.isCostModifiedForTurn && card.costForTurn == 0)) {
                this.amount2++;
                if (this.amount2 >= 3) {
                    this.amount2 = 0;
                    AbstractDungeon.actionManager.addToTop(new DrawCardAction(AbstractDungeon.player, this.amount));
                    AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(this.amount));
                    this.flash();
                    usedThisTurn = true;
                }
            }
        }
    }

    @Override
    public void atStartOfTurn() {
        usedThisTurn = false;
        this.amount2 = 0;
        updateDescription();
    }

}