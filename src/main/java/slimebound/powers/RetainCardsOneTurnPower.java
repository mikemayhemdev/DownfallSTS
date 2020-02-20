package slimebound.powers;

import com.megacrit.cardcrawl.actions.unique.RetainCardsAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import slimebound.SlimeboundMod;

/*    */
/*    */
/*    */
/*    */


public class RetainCardsOneTurnPower extends AbstractPower {
    public static final String POWER_ID = "Slimebound:RetainCardsOneTurnPower";
    public static final String NAME = "Potency";
    public static final String IMG = "powers/retaingreen.png";
    public static final Logger logger = LogManager.getLogger(SlimeboundMod.class.getName());
    public static PowerType POWER_TYPE = PowerType.BUFF;
    public static String[] DESCRIPTIONS;
    private AbstractCreature source;


    public RetainCardsOneTurnPower(AbstractCreature owner, int amount) {

        this.name = NAME;

        this.ID = POWER_ID;


        this.owner = owner;

        this.source = source;


        this.img = new com.badlogic.gdx.graphics.Texture(SlimeboundMod.getResourcePath(IMG));

        this.type = POWER_TYPE;

        this.amount = amount;
        DESCRIPTIONS = CardCrawlGame.languagePack.getPowerStrings(this.ID).DESCRIPTIONS;

        this.name = CardCrawlGame.languagePack.getPowerStrings(this.ID).NAME;

        updateDescription();
    }

    public void updateDescription() {
        if (this.amount == 1) {
            this.description = (DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1]);
        } else {
            this.description = (DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[2]);
        }
    }

    public void atEndOfTurn(boolean isPlayer) {
        if ((isPlayer) && (!AbstractDungeon.player.hand.isEmpty()) && (!AbstractDungeon.player.hasRelic("Runic Pyramid")) &&
                (!AbstractDungeon.player.hasPower("Equilibrium"))) {
            AbstractDungeon.actionManager.addToBottom(new RetainCardsAction(this.owner, this.amount));
            AbstractDungeon.actionManager.addToBottom(new com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction(this.owner, this.owner, RetainCardsOneTurnPower.POWER_ID));

        }
    }
}

