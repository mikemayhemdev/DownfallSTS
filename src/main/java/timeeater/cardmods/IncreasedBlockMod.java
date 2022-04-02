package timeeater.cardmods;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class IncreasedBlockMod extends AbstractCardModifier {
    int amount;

    public IncreasedBlockMod(int amount) {
        this.amount = amount;
    }

    @Override
    public float modifyBlock(float block, AbstractCard card) {
        return block + amount;
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new IncreasedBlockMod(amount);
    }
}
