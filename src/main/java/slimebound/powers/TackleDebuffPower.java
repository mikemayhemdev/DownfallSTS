package slimebound.powers;


import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.powers.AbstractPower;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import slimebound.SlimeboundMod;


public class TackleDebuffPower extends AbstractPower {
    public static final String POWER_ID = "Slimebound:TackleDebuffPower";
    public static final String NAME = "TackleDebuffPower";
    public static final String IMG = "powers/TackleDebuff.png";
    public static final Logger logger = LogManager.getLogger(SlimeboundMod.class.getName());
    public static PowerType POWER_TYPE = PowerType.DEBUFF;
    public static String[] DESCRIPTIONS;
    public boolean doubleUp = false;
    public boolean triggered = false;
    private AbstractCreature source;


    public TackleDebuffPower(AbstractCreature owner, AbstractCreature source, int amount) {

        this.name = NAME;

        this.ID = POWER_ID;
        this.amount = amount;

        this.owner = owner;

        this.source = source;


        this.img = new com.badlogic.gdx.graphics.Texture(SlimeboundMod.getResourcePath(IMG));

        this.type = POWER_TYPE;

        this.DESCRIPTIONS = CardCrawlGame.languagePack.getPowerStrings(this.ID).DESCRIPTIONS;

        this.name = CardCrawlGame.languagePack.getPowerStrings(this.ID).NAME;

        updateDescription();


    }


    public void updateDescription() {

        this.description = (DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1]);


    }


}





