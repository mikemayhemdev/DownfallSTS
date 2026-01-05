package slimebound.powers;


import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.AbstractPower;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import slimebound.SlimeboundMod;
import slimebound.orbs.SpawnedSlime;


public class PotencyPower extends AbstractPower {
    public static final String POWER_ID = "Slimebound:PotencyPower";
    public static final String NAME = "Potency";
    public static final String IMG = "powers/potency.png";
    public static final Logger logger = LogManager.getLogger(SlimeboundMod.class.getName());
    public static PowerType POWER_TYPE = PowerType.BUFF;
    public static String[] DESCRIPTIONS;
    private AbstractCreature source;


    public PotencyPower(AbstractCreature owner, AbstractCreature source, int amount) {

        this.name = NAME;

        this.ID = POWER_ID;
        this.canGoNegative = true;

        this.owner = owner;

        this.source = source;


        this.img = new com.badlogic.gdx.graphics.Texture(slimebound.SlimeboundMod.getResourcePath(IMG));

        this.type = POWER_TYPE;

        this.amount = amount;
        DESCRIPTIONS = CardCrawlGame.languagePack.getPowerStrings(this.ID).DESCRIPTIONS;

        this.name = CardCrawlGame.languagePack.getPowerStrings(this.ID).NAME;

        updateDescription();

    }

    public void stackPower(int stackAmount) {
        this.fontScale = 8.0F;
        this.amount += stackAmount;// 39
        if (this.amount == 0) {// 41
            this.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));// 42
        }
    }// 52


    public void updateDescription() {
        if(this.amount<0){
            this.type = PowerType.DEBUFF;
        }else{
            this.type = PowerType.BUFF;
        }
        this.description = (DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1]+ (int) Math.ceil((this.amount / 2.0)) + DESCRIPTIONS[2]);
    }

}



