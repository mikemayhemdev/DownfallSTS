package slimebound.powers;


import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import slimebound.SlimeboundMod;


public class NextTurnGainStrengthPower extends AbstractPower {
    public static final String POWER_ID = "Slimebound:NextTurnGainStrengthPower";
    public static final String NAME = "Potency";
    public static final String IMG = "powers/DelayedAttackSmall.png";
    public static final Logger logger = LogManager.getLogger(SlimeboundMod.class.getName());
    public static PowerType POWER_TYPE = PowerType.BUFF;
    public static String[] DESCRIPTIONS;
    private AbstractCreature source;


    public NextTurnGainStrengthPower(AbstractCreature owner, AbstractCreature source, int amount) {

        this.name = NAME;

        this.ID = POWER_ID;


        this.owner = owner;

        this.source = source;


        this.img = new com.badlogic.gdx.graphics.Texture(SlimeboundMod.getResourcePath(IMG));

        this.type = POWER_TYPE;

        this.amount = amount;
        this.DESCRIPTIONS = CardCrawlGame.languagePack.getPowerStrings(this.ID).DESCRIPTIONS;

        this.name = CardCrawlGame.languagePack.getPowerStrings(this.ID).NAME;

        updateDescription();

    }


    public void updateDescription() {


        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];


    }


    public void atStartOfTurn() {

        flash();

        AbstractDungeon.actionManager.addToBottom(new com.megacrit.cardcrawl.actions.common.ApplyPowerAction(this.owner, this.owner, new StrengthPower(this.owner, this.amount), this.amount));


        AbstractDungeon.actionManager.addToBottom(new com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction(this.owner, this.owner, NextTurnGainStrengthPower.POWER_ID));


    }


}



