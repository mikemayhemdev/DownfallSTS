package slimebound.powers;


import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import slimebound.SlimeboundMod;
import slimebound.actions.PreventCurrentOverMaxHealthAction;


public class DuplicatedFormNoHealPower extends AbstractPower {
    public static final String POWER_ID = "Slimebound:DuplicatedFormNoHealPower";
    public static final String NAME = "Potency";
    public static final String IMG = "powers/HalvedS.png";
    public static final Logger logger = LogManager.getLogger(SlimeboundMod.class.getName());
    public static PowerType POWER_TYPE = PowerType.DEBUFF;
    public static String[] DESCRIPTIONS;
    private boolean victoryUsed;
    private boolean beingRemoved;


    public DuplicatedFormNoHealPower(AbstractCreature owner, AbstractCreature source, int amount) {

        this.name = NAME;

        this.ID = POWER_ID;


        this.owner = owner;

        this.img = new com.badlogic.gdx.graphics.Texture(SlimeboundMod.getResourcePath(IMG));

        this.type = POWER_TYPE;

        this.amount = amount;
        DESCRIPTIONS = CardCrawlGame.languagePack.getPowerStrings(this.ID).DESCRIPTIONS;

        this.name = CardCrawlGame.languagePack.getPowerStrings(this.ID).NAME;

        updateDescription();

    }

    public int onHeal(int healAmount) {
        if (!this.beingRemoved && this.amount > 0 && (AbstractDungeon.currMapNode != null) && (AbstractDungeon.getCurrRoom().phase == com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase.COMBAT)) {
            //flash();
            int currentHealth = this.owner.currentHealth;
            int maxHealth = this.owner.maxHealth - this.amount;
            double currentPct = currentHealth * 1.001 / maxHealth * 1.001;
            logger.info("Split: Current health: " + currentHealth);
            logger.info("Split: Max health: " + maxHealth);
            logger.info("Split: Current percentage: " + currentPct);
            if (currentHealth + healAmount > maxHealth && !victoryUsed) {
                logger.info("Split: Capping HP returned.");
                return (maxHealth) - currentHealth;
            } else {
                logger.info("Split: Full heal allowed.");
                return healAmount;
            }
        }
        return healAmount;
    }


    public void updateDescription() {


        this.description = (DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1]);


    }


    public void onInitialApplication() {
        AbstractPlayer p = AbstractDungeon.player;

        //if (p.currentHealth > (p.maxHealth/2)){
        //  AbstractDungeon.actionManager.addToBottom(new LoseHPAction(AbstractDungeon.player,AbstractDungeon.player,p.currentHealth-(p.maxHealth/2)));
        AbstractDungeon.player.damage(new DamageInfo(AbstractDungeon.player, this.amount, DamageInfo.DamageType.HP_LOSS));

        updateCurrentHealth();

    }

    public void updateCurrentHealth() {
        AbstractDungeon.actionManager.addToBottom(new PreventCurrentOverMaxHealthAction(this.amount));
    }

    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
        //SlimeboundMod.logger.info("Stacking Split: " + stackAmount);
        if (stackAmount > 0){
            AbstractDungeon.player.damage(new DamageInfo(AbstractDungeon.player, stackAmount, DamageInfo.DamageType.HP_LOSS));
        }
        updateCurrentHealth();
    }

    public void reducePower(int stackAmount) {
        super.reducePower(stackAmount);
        //SlimeboundMod.logger.info("Reducing Split: " + stackAmount);
        if (stackAmount > 0) {
            logger.info("Split is returning HP (normal): " + stackAmount);
            this.owner.heal(stackAmount);
        }

    }

    public void onRemove() {
        this.beingRemoved = true;
        logger.info("Split is returning HP (removed): " + this.amount);
        this.owner.heal(this.amount);
        //restoreMaxHP();
    }

    public void onVictory() {
        if (!victoryUsed) {
            logger.info("Split is returning HP (victory): " + this.amount);
            victoryUsed = true;
            this.owner.heal(this.amount);
            this.amount = 0;
        }
    }
}



