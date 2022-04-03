package timeeater.powers;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;

import static timeeater.TimeEaterMod.makeID;

public class ContinuancePower extends AbstractTimeEaterPower implements OnRetrieveCardPower {
    public static final String ID = makeID(ContinuancePower.class.getSimpleName());
    public static final PowerStrings strs = CardCrawlGame.languagePack.getPowerStrings(ID);

    public ContinuancePower(int amount) {
        super(ID, strs.NAME, PowerType.BUFF, false, AbstractDungeon.player, amount);
    }

    @Override
    public void receiveRetrieve(AbstractCard card) {
        flash();
        addToBot(new GainBlockAction(owner, amount));
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }
}
