package slimebound.powers;


import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.AbstractPower;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import slimebound.SlimeboundMod;
import slimebound.orbs.AttackSlime;
import slimebound.orbs.SpawnedSlime;


public class BuffAttackSlimesPower extends AbstractPower {
    public static final String POWER_ID = "Slimebound:BuffAttackSlimesPower";
    public static final String NAME = "Potency";
    public static final String IMG = "powers/BuffAttackSlimes.png";
    public static final Logger logger = LogManager.getLogger(SlimeboundMod.class.getName());
    public static PowerType POWER_TYPE = PowerType.BUFF;
    public static String[] DESCRIPTIONS;
    private AbstractCreature source;


    public BuffAttackSlimesPower(AbstractCreature owner, AbstractCreature source, int amount) {

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

    public void onInitialApplication() {


        for (AbstractOrb o : AbstractDungeon.player.orbs) {
            if (o instanceof AttackSlime) {
                SpawnedSlime s = (SpawnedSlime) o;
                s.applyFocus();
            }
        }
    }


    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);

        for (AbstractOrb o : AbstractDungeon.player.orbs) {
            if (o instanceof AttackSlime) {
                SpawnedSlime s = (SpawnedSlime) o;
                s.applyFocus();
            }
        }

    }

    public void updateDescription() {


        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];


    }


}




