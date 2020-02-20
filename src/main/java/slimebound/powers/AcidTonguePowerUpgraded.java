package slimebound.powers;


import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.AbstractPower;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import slimebound.SlimeboundMod;
import slimebound.cards.AbstractSlimeboundCard;
import slimebound.orbs.SlimingSlime;


public class AcidTonguePowerUpgraded extends AbstractPower {
    public static final String POWER_ID = "Slimebound:AcidTonguePowerUpgraded";
    public static final String NAME = "Potency";
    public static final String IMG = "powers/AcidTongueP.png";
    public static final Logger logger = LogManager.getLogger(SlimeboundMod.class.getName());
    public static PowerType POWER_TYPE = PowerType.BUFF;
    public static String[] DESCRIPTIONS;
    private AbstractCreature source;


    public AcidTonguePowerUpgraded(AbstractCreature owner, AbstractCreature source, int amount) {

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

    public void updateSlimedEffects() {

        for (AbstractCard c : AbstractDungeon.player.drawPile.group) {
            if (c instanceof AbstractSlimeboundCard) {
                ((AbstractSlimeboundCard) c).upgradeSlimed(0);
            }
        }
        for (AbstractCard c : AbstractDungeon.player.exhaustPile.group) {
            if (c instanceof AbstractSlimeboundCard) {
                ((AbstractSlimeboundCard) c).upgradeSlimed(0);
            }
        }
        for (AbstractCard c : AbstractDungeon.player.discardPile.group) {
            if (c instanceof AbstractSlimeboundCard) {
                ((AbstractSlimeboundCard) c).upgradeSlimed(0);
            }
        }
        for (AbstractCard c : AbstractDungeon.player.hand.group) {
            if (c instanceof AbstractSlimeboundCard) {
                ((AbstractSlimeboundCard) c).upgradeSlimed(0);
            }
        }


        if (this.owner.hasPower(SlimedThornsPower.POWER_ID)) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.owner, this.owner, new SlimedThornsPower(this.owner, this.owner, this.amount), this.amount));

        }

        for (AbstractOrb o : AbstractDungeon.player.orbs) {
            if (o instanceof SlimingSlime) {
                ((SlimingSlime) o).updateSlimedNumber();

            }
        }
    }

    public void onInitialApplication() {
        updateSlimedEffects();

    }

    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
        updateSlimedEffects();
    }

    public void updateDescription() {


        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];


    }


}




