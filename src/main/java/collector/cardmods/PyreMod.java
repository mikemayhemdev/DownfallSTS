package collector.cardmods;

import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static collector.CollectorMod.makeID;

public class PyreMod extends AbstractCardModifier {
    public static final String ID = makeID("PyreMod");

    @Override
    public String identifier(AbstractCard card) {
        return ID;
    }

    public int exhaustAmt;

    public PyreMod(int exhaustAmt) {
        this.exhaustAmt = exhaustAmt;
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new PyreMod(exhaustAmt);
    }

    @Override
    public boolean canPlayCard(AbstractCard card) {
        return AbstractDungeon.player.hand.size() - 1 >= exhaustAmt;
    }

    @Override
    public boolean shouldApply(AbstractCard card) {
        return !CardModifierManager.hasModifier(card, ID);
    }
}
