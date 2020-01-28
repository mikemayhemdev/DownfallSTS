package charbosses.powers.cardpowers;

import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.powers.AbstractPower;

import charbosses.bosses.AbstractCharBoss;
import charbosses.cards.AbstractBossCard;

import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.actions.utility.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.core.*;

public class EnemyAfterImagePower extends AbstractPower
{
    public static final String POWER_ID = "EvilWithin:Enemy After Image";
    private static final PowerStrings powerStrings;
    
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
        }
        else {
            this.addToBot(new GainBlockAction(AbstractCharBoss.boss, AbstractCharBoss.boss, this.amount));
        }
        this.flash();
    }
    
    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("After Image");
    }
}
