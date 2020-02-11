package guardian.powers;


import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;


public class BlockOnCardUsePower extends AbstractGuardianPower {
    public static final String POWER_ID = "Guardian:BlockOnCardUsePower";
    public static PowerType POWER_TYPE = PowerType.BUFF;
    public static String[] DESCRIPTIONS;


    public BlockOnCardUsePower(AbstractCreature owner, int amount) {

        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.setImage("DefenseModePower84.png", "DefenseModePower32.png");
        this.type = POWER_TYPE;
        this.DESCRIPTIONS = CardCrawlGame.languagePack.getPowerStrings(this.ID).DESCRIPTIONS;
        this.name = CardCrawlGame.languagePack.getPowerStrings(this.ID).NAME;

        updateDescription();

    }


    public void updateDescription() {

        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }


    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        super.onUseCard(card, action);
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(this.owner, this.owner, this.amount));

    }

    public void atEndOfRound() {
        AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));


    }

}



