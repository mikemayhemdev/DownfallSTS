package expansioncontent.powers;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import expansioncontent.actions.RandomCardWithTagAction;
import expansioncontent.expansionContentMod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import slimebound.SlimeboundMod;
import theHexaghost.util.TextureLoader;


public class StudySlimeBossPower extends AbstractPower {
    public static final String POWER_ID = expansionContentMod.makeID("StudySlimeBossPower");
    public static final String NAME = "Potency";
    public static final Logger logger = LogManager.getLogger(SlimeboundMod.class.getName());
    private static final Texture tex84 = TextureLoader.getTexture(expansionContentMod.getModID() + "Resources/images/powers/StudySlimeBoss84.png");
    private static final Texture tex32 = TextureLoader.getTexture(expansionContentMod.getModID() + "Resources/images/powers/StudySlimeBoss32.png");
    public static PowerType POWER_TYPE = PowerType.BUFF;
    public static String[] DESCRIPTIONS;
    private AbstractCreature source;


    public StudySlimeBossPower(AbstractCreature owner, AbstractCreature source, int amount) {

        this.name = NAME;

        this.ID = POWER_ID;


        this.owner = owner;

        this.source = source;


        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);


        this.type = POWER_TYPE;

        this.amount = amount;
        this.DESCRIPTIONS = CardCrawlGame.languagePack.getPowerStrings(this.ID).DESCRIPTIONS;

        this.name = CardCrawlGame.languagePack.getPowerStrings(this.ID).NAME;

        updateDescription();

    }


    public void updateDescription() {


        if (this.amount == 1) {
            this.description = DESCRIPTIONS[0];
        } else {
            this.description = (DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2]);
        }


    }


    public void atStartOfTurn() {

        flash();

        AbstractDungeon.actionManager.addToBottom(new RandomCardWithTagAction(false, expansionContentMod.STUDY_SLIMEBOSS));

        if (this.amount <= 1) {

            AbstractDungeon.actionManager.addToBottom(new com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction(this.owner, this.owner, StudySlimeBossPower.POWER_ID));

        } else {

            AbstractDungeon.actionManager.addToBottom(new com.megacrit.cardcrawl.actions.common.ReducePowerAction(this.owner, this.owner, StudySlimeBossPower.POWER_ID, 1));

        }
    }


}



