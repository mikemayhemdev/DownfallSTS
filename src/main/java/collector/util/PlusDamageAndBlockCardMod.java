package collector.util;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class PlusDamageAndBlockCardMod extends AbstractCardModifier {
    int bonus;
    @Override
    public AbstractCardModifier makeCopy() {
        return new PlusDamageAndBlockCardMod(bonus);
    }

    public PlusDamageAndBlockCardMod(int amount){
        bonus = amount;
    }


    public float modifyDamage(float damage, DamageInfo.DamageType type, AbstractCard card, AbstractMonster target) {
        return damage+bonus;
    }
    public float modifyBlock(float block, AbstractCard card) {
        return block+bonus;
    }
}
