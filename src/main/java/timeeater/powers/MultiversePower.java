package timeeater.powers;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import timeeater.TimeEaterChar;

import static timeeater.TimeEaterMod.makeID;

public class MultiversePower extends AbstractTimeEaterPower implements OnSuspendCardPower {
    public static final String ID = makeID(MultiversePower.class.getSimpleName());
    public static final PowerStrings strs = CardCrawlGame.languagePack.getPowerStrings(ID);

    public MultiversePower(int amount) {
        super(ID,  PowerType.BUFF, false, AbstractDungeon.player, amount);
    }

    @Override
    public void receiveSuspended(AbstractCard card) {
        if (card.color != TimeEaterChar.Enums.MAGENTA){
            flash();
            AbstractCard q = card.makeStatEquivalentCopy();
            q.updateCost(-amount);
            addToBot(new MakeTempCardInHandAction(q));
        }
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }
}
