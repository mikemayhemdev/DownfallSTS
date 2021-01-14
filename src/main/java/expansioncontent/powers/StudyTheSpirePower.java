package expansioncontent.powers;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.NonStackablePower;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import expansioncontent.actions.RandomCardWithTagAction;
import expansioncontent.expansionContentMod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import slimebound.SlimeboundMod;
import downfall.util.TextureLoader;


public class StudyTheSpirePower extends AbstractPower implements NonStackablePower {

    public static final String POWER_ID = expansionContentMod.makeID("StudyTheSpirePower");
    public static final String NAME = CardCrawlGame.languagePack.getPowerStrings(POWER_ID).NAME;
    public static final String DESCRIPTIONS[] = CardCrawlGame.languagePack.getPowerStrings(POWER_ID).DESCRIPTIONS;
    public static final Logger logger = LogManager.getLogger(SlimeboundMod.class.getName());
    private static final Texture tex84 = TextureLoader.getTexture(expansionContentMod.getModID() + "Resources/images/powers/StudyAutomaton84.png");
    private static final Texture tex32 = TextureLoader.getTexture(expansionContentMod.getModID() + "Resources/images/powers/StudyAutomaton32.png");
    public static PowerType POWER_TYPE = PowerType.BUFF;

    private boolean upgraded;

    public StudyTheSpirePower(AbstractCreature owner, int amount, boolean upgraded) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.upgraded = upgraded;
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);
        this.type = POWER_TYPE;
        this.amount = amount;
        updateDescription();
    }

    @Override
    public boolean isStackable(AbstractPower power) {
        if (power instanceof StudyTheSpirePower) {
            return ((StudyTheSpirePower) power).upgraded == upgraded;
        }
        return false;
    }

    public void updateDescription() {
        if (upgraded) {
            if (this.amount == 1) {
                this.description = DESCRIPTIONS[3];
            } else {
                this.description = (DESCRIPTIONS[4] + this.amount + DESCRIPTIONS[2]);
            }
        }
        else {
            if (this.amount == 1) {
                this.description = DESCRIPTIONS[0];
            } else {
                this.description = (DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2]);
            }
        }
    }


    public void atStartOfTurn() {
        flash();
        AbstractDungeon.actionManager.addToBottom(new RandomCardWithTagAction(false, expansionContentMod.STUDY));
        if (this.amount <= 1) {
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, StudyTheSpirePower.POWER_ID));
        } else {
            AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(this.owner, this.owner, StudyTheSpirePower.POWER_ID, 1));
        }
    }
}



