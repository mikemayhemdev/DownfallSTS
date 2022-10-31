package gremlin.powers;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public interface DamageConditionalGivePower {
    float atDamageConditionalGive(float damage, AbstractCard ca, AbstractMonster mo, DamageInfo.DamageType type);

    float atFinalDamageConditionalGive(float damage, AbstractCard ca, AbstractMonster mo, DamageInfo.DamageType type);

}
