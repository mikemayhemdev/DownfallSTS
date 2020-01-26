package slimebound.powers;


import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import slimebound.SlimeboundMod;
import slimebound.cards.AbstractSlimeboundCard;


public class TackleSelfDamagePreventPower extends AbstractPower {
    public static final String POWER_ID = "Slimebound:TackleSelfDamagePreventPower";
    public static final String NAME = "TackleDebuffPower";
    public static PowerType POWER_TYPE = PowerType.DEBUFF;
    public static final String IMG = "powers/recklessPower.png";
    public boolean doubleUp = false;
    public static final Logger logger = LogManager.getLogger(SlimeboundMod.class.getName());
    public static String[] DESCRIPTIONS;
    private AbstractCreature source;
    public boolean triggered = false;


    public TackleSelfDamagePreventPower(AbstractCreature owner, AbstractCreature source, int amount) {

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


    public void updateTackleEffects(){


        for (AbstractCard c : AbstractDungeon.player.drawPile.group) {
            if (c instanceof AbstractSlimeboundCard){
                if (c.hasTag(SlimeboundMod.TACKLE)) {
                    ((AbstractSlimeboundCard) c).upgradeSelfDamage(3);
                }
            }
        }
        for (AbstractCard c : AbstractDungeon.player.exhaustPile.group) {
            if (c instanceof AbstractSlimeboundCard){
                if (c.hasTag(SlimeboundMod.TACKLE)) {
                    ((AbstractSlimeboundCard) c).upgradeSelfDamage(3);
                }
            }
        }
        for (AbstractCard c : AbstractDungeon.player.discardPile.group) {
            if (c instanceof AbstractSlimeboundCard){
                if (c.hasTag(SlimeboundMod.TACKLE)) {
                    ((AbstractSlimeboundCard) c).upgradeSelfDamage(3);
                }
            }
        }
        for (AbstractCard c : AbstractDungeon.player.hand.group) {
            if (c instanceof AbstractSlimeboundCard) {
                if (c.hasTag(SlimeboundMod.TACKLE)) {
                    ((AbstractSlimeboundCard) c).upgradeSelfDamage(3);
                }
            }
        }

    }


    public void onInitialApplication() {
        super.onInitialApplication();
        updateTackleEffects();

    }

    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
        updateTackleEffects();
    }

    public void reducePower(int stackAmount) {
        super.reducePower(stackAmount);
        updateTackleEffects();
    }

    public void onRemove() {
        super.onRemove();
        this.amount = 0;
        updateTackleEffects();
    }
}





