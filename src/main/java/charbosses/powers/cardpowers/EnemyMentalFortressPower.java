//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package charbosses.powers.cardpowers;

import charbosses.cards.AbstractBossCard;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.stances.AbstractStance;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

public class EnemyMentalFortressPower extends AbstractPower {
    public static final Logger logger = LogManager.getLogger(EnemyMentalFortressPower.class.getName());

    public static final String POWER_ID = "downfall_Charboss:MentalFortressPower";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;

    public EnemyMentalFortressPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = "Mental Fortress";
        this.owner = owner;
        this.amount = amount;
        this.updateDescription();
        this.loadRegion("mental_fortress");
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

    public void onChangeStance(AbstractStance oldStance, AbstractStance newStance) {
        logger.info(this.toString());
        logger.info(oldStance + " " + newStance);
        logger.info(oldStance.ID + " " + newStance.ID);
        logger.info(oldStance.name + " " + newStance.name);
        String oldStanceId = Optional.ofNullable(oldStance).map(a -> a.ID).orElse("");
        String newStanceId = Optional.ofNullable(newStance).map(a -> a.ID).orElse("");
        logger.info(oldStanceId + " " + newStanceId);
        if (!oldStanceId.equals(newStanceId)) {
            this.flash();
            this.addToBot(new GainBlockAction(this.owner, this.owner, this.amount));
            logger.info("gained block");
        }

    }

    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("Controlled");
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}
