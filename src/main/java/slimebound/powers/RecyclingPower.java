package slimebound.powers;


import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import slimebound.SlimeboundMod;
import slimebound.actions.ReturnRandom0Cost;


public class RecyclingPower extends AbstractPower {
    public static final String POWER_ID = "Slimebound:RecyclingPower";
    public static final String NAME = "Potency";
    public static final String IMG = "powers/RecyclingS.png";
    public static final Logger logger = LogManager.getLogger(SlimeboundMod.class.getName());
    public static PowerType POWER_TYPE = PowerType.BUFF;
    public static String[] DESCRIPTIONS;
    private AbstractCreature source;


    public RecyclingPower(AbstractCreature owner, AbstractCreature source, int amount) {

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

    /*
    public void onExhaust(AbstractCard card) {
        if (card.cost == 0) {
            this.flash();
            AbstractDungeon.actionManager.addToBottom(new HealAction(this.owner, this.owner, this.amount));
        }

    }
    */

    public void atStartOfTurn() {
        flash();
        AbstractDungeon.actionManager.addToBottom(new ReturnRandom0Cost(amount));
    }


    public void updateDescription() {


        if (this.amount == 1) {
            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
        } else {
            this.description = (DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[2]);
        }

    }


}




