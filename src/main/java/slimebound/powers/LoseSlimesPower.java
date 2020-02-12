package slimebound.powers;


import com.evacipated.cardcrawl.mod.stslib.actions.defect.EvokeSpecificOrbAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
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


public class LoseSlimesPower extends AbstractPower {
    public static final String POWER_ID = "Slimebound:LoseSlimesPower";
    public static final String NAME = "Potency";
    public static final String IMG = "powers/nostalgia.png";
    public static final Logger logger = LogManager.getLogger(SlimeboundMod.class.getName());
    public static PowerType POWER_TYPE = PowerType.DEBUFF;
    public static String[] DESCRIPTIONS;
    private static boolean naturalclear = false;
    private AbstractCreature source;


    public LoseSlimesPower(AbstractCreature owner, AbstractCreature source, int amount) {

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


        if (this.amount > 1) {
            this.description = "#b" + this.amount + DESCRIPTIONS[1];

        } else {
            this.description = DESCRIPTIONS[0];


        }

    }


    public void atStartOfTurn() {

        flash();


        if (this.amount <= 1) {

            this.naturalclear = true;
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, LoseSlimesPower.POWER_ID));
            killSlimes();
        } else {

            AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(this.owner, this.owner, LoseSlimesPower.POWER_ID, 1));

        }

    }


    public void killSlimes() {
        if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            if (!AbstractDungeon.player.orbs.isEmpty()) {
                for (AbstractOrb o : AbstractDungeon.player.orbs) {

                    if (o instanceof SpawnedSlime) {

                        AbstractDungeon.actionManager.addToBottom(new EvokeSpecificOrbAction(o));

                    }
                }

            }
        }
    }


}



