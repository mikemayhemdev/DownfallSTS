package slimebound.powers;


import com.evacipated.cardcrawl.mod.stslib.powers.abstracts.TwoAmountPower;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import slimebound.SlimeboundMod;
import slimebound.actions.CommandAction;
import slimebound.actions.TrigggerSpecificSlimeAttackAction;


public class ComboAttackPower extends TwoAmountPower {
    public static final String POWER_ID = "Slimebound:ComboAttackPower";
    public static final String NAME = "Potency";
    public static final String IMG = "powers/SplitForLessS.png";
    public static final Logger logger = LogManager.getLogger(SlimeboundMod.class.getName());
    public static PowerType POWER_TYPE = PowerType.BUFF;
    public static String[] DESCRIPTIONS;
    private int timesTriggeredThisTurn;
    private AbstractCreature source;


    public ComboAttackPower(AbstractCreature owner, AbstractCreature source, int amount) {

        this.name = NAME;

        this.ID = POWER_ID;


        this.owner = owner;

        this.source = source;


        this.img = new com.badlogic.gdx.graphics.Texture(SlimeboundMod.getResourcePath(IMG));

        this.type = POWER_TYPE;

        this.amount = amount;
        this.amount2 = this.amount;
        DESCRIPTIONS = CardCrawlGame.languagePack.getPowerStrings(this.ID).DESCRIPTIONS;

        this.name = CardCrawlGame.languagePack.getPowerStrings(this.ID).NAME;

        updateDescription();

    }


    public void updateDescription() {


        if (this.amount == 1) {
            this.description = DESCRIPTIONS[0];
        } else {
            this.description = (DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2]);
        }

        if (this.amount2 == 0) {
            this.description += DESCRIPTIONS[6];
        } else if (this.amount == 1) {
            this.description += DESCRIPTIONS[3] + this.amount2 + DESCRIPTIONS[5];
        } else {
            this.description += DESCRIPTIONS[3] + this.amount2 + DESCRIPTIONS[4];
        }
    }


    public void onAfterCardPlayed(AbstractCard usedCard) {
        super.onAfterCardPlayed(usedCard);
        if (usedCard.target == AbstractCard.CardTarget.ENEMY || usedCard.target == AbstractCard.CardTarget.ALL_ENEMY) {
            if (this.amount2 > 0) {
                if (SlimeboundMod.getLeadingSlime() != null)
                {
                    flash();
                    com.megacrit.cardcrawl.dungeons.AbstractDungeon.actionManager.addToBottom(new CommandAction());
                    this.amount2--;
                    updateDescription();
                }
            }
        }
    }

    public void stackPower(int stackAmount) {
        this.fontScale = 8.0F;
        this.amount += stackAmount;
        this.amount2 += stackAmount;
        updateDescription();
    }

    public void atStartOfTurn() {

        //flash();
        this.amount2 = this.amount;
        updateDescription();
        //  AbstractDungeon.actionManager.addToBottom(new RandomLickCardAction(false));

    }


}



