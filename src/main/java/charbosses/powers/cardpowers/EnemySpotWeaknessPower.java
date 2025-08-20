package charbosses.powers.cardpowers;
import charbosses.bosses.AbstractCharBoss;
import charbosses.cards.AbstractBossCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.GainStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EnemySpotWeaknessPower extends AbstractPower {
    private static final Logger logger = LogManager.getLogger(com.megacrit.cardcrawl.powers.FlameBarrierPower.class.getName());
    public static final String POWER_ID = "Spot Weakness";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    public boolean isActive;
    public int theoreticalGain;

    public EnemySpotWeaknessPower(AbstractCreature owner, int strengthGain) {
        this.name = NAME;
        this.ID = "Spot Weakness";
        this.owner = owner;
        this.amount = strengthGain;
        this.updateDescription();
        this.loadRegion("curiosity");
        isActive = false;
    }

    public void stackPower(int stackAmount) {
        if (this.amount == -1) {
            logger.info(this.name + " does not stack");
        } else {
            this.fontScale = 8.0F;
            this.amount += stackAmount;
            this.updateDescription();
        }
    }

    @Override
    public void onUseCard(final AbstractCard card, final UseCardAction action) {
        if (card instanceof AbstractBossCard) {
            return;
        }
        if (card.type.equals(AbstractCard.CardType.ATTACK) && !isActive) {
            this.flash();
            isActive = true;
            //amount = theoreticalGain;
            updateDescription();
        }
    }

    public void atStartOfTurn() {
        if (isActive) {
            this.addToBot(new ApplyPowerAction(AbstractCharBoss.boss, AbstractCharBoss.boss, new StrengthPower(AbstractCharBoss.boss, this.amount)));
            isActive = false;
        }
        this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, "Spot Weakness"));
    }

    public void updateDescription() {
        if (isActive) {
            this.description = DESCRIPTIONS[2] + this.amount + DESCRIPTIONS[1];
        } else {
            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
        }
    }

    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("downfall:SpotWeakness");
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}
