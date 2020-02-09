package charbosses.powers.cardpowers;

import charbosses.bosses.AbstractCharBoss;
import charbosses.cards.AbstractBossCard;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class EnemyAfterImagePower extends AbstractPower {
    public static final String POWER_ID = "EvilWithin:Enemy After Image";
    private static final PowerStrings powerStrings;

    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("After Image");
    }

    public EnemyAfterImagePower(final AbstractCreature owner, final int amount) {
        this.name = EnemyAfterImagePower.powerStrings.NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.updateDescription();
        this.loadRegion("afterImage");
    }

    @Override
    public void updateDescription() {
        this.description = EnemyAfterImagePower.powerStrings.DESCRIPTIONS[0] + this.amount + EnemyAfterImagePower.powerStrings.DESCRIPTIONS[1];
    }

    @Override
    public void onUseCard(final AbstractCard card, final UseCardAction action) {
        if (!(card instanceof AbstractBossCard)) {
            return;
        }
        if (Settings.FAST_MODE) {
            this.addToBot(new GainBlockAction(AbstractCharBoss.boss, AbstractCharBoss.boss, this.amount, true));
        } else {
            this.addToBot(new GainBlockAction(AbstractCharBoss.boss, AbstractCharBoss.boss, this.amount));
        }
        this.flash();
    }
}
