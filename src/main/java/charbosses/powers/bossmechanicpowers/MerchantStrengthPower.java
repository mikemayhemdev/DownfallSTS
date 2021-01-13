package charbosses.powers.bossmechanicpowers;

import charbosses.bosses.AbstractCharBoss;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.FocusPower;


public class MerchantStrengthPower extends AbstractBossMechanicPower {
    public static final String POWER_ID = "downfall:MerchantStrengthPower";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public MerchantStrengthPower(AbstractCreature owner) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = 1;
        updateDescription();
        loadRegion("curiosity");
        this.type = PowerType.BUFF;
    }

    public void updateDescription() {
        this.description = (DESCRIPTIONS[0]);
    }

}

