package charbosses.relics;

import charbosses.bosses.AbstractCharBoss;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.OddlySmoothStone;
import hermit.relics.CharredGlove;

public class CBR_CharredGlove extends AbstractCharbossRelic {
    public static final String ID = "CharredGlove";

    public CBR_CharredGlove() {
        super(new CharredGlove());
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public void atTurnStart() {
        for (AbstractCard q : AbstractCharBoss.boss.hand.group) {
            if (q.type == AbstractCard.CardType.CURSE || q.color == AbstractCard.CardColor.CURSE) {
                flash();
                addToBot(new ApplyPowerAction(AbstractCharBoss.boss, AbstractCharBoss.boss, new StrengthPower(AbstractCharBoss.boss, 4), 4));
                addToBot(new ApplyPowerAction(AbstractCharBoss.boss, AbstractCharBoss.boss, new LoseStrengthPower(AbstractCharBoss.boss, 4), 4));
            }
        }
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_CharredGlove();
    }
}
