package slimebound.powers;


import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.powers.AbstractPower;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import slimebound.SlimeboundMod;
import slimebound.cards.SlimeBeam;


public class NextTurnGainHyperBeam extends AbstractPower {
    public static final String POWER_ID = "Slimebound:NextTurnGainHyperBeam";
    public static final String NAME = "Potency";
    public static PowerType POWER_TYPE = PowerType.BUFF;
    public static final String IMG = "powers/PrepareCardS.png";
    public static final Logger logger = LogManager.getLogger(SlimeboundMod.class.getName());

    public static String[] DESCRIPTIONS;
    private AbstractCreature source;


    public NextTurnGainHyperBeam(AbstractCreature owner, AbstractCreature source, int amount) {

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


        if (this.amount <= 1) {
            this.description = DESCRIPTIONS[0];
        } else {
            this.description = DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2];
        }


    }


    public void atStartOfTurn() {

        flash();
        AbstractCard c;
        c = CardLibrary.getCard(SlimeBeam.ID).makeCopy();

        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(c, this.amount));
        AbstractDungeon.actionManager.addToBottom(new com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction(this.owner, this.owner, NextTurnGainHyperBeam.POWER_ID));

    }


}



