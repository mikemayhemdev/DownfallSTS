package gremlin.powers;

import com.megacrit.cardcrawl.cards.AbstractCard;

public interface ConditionalModifyBlockPower {
    float conditionalModifyBlock(float blockAmount, AbstractCard card);
}
