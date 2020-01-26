package slimebound.helpers;

import basemod.abstracts.DynamicVariable;
import com.megacrit.cardcrawl.cards.AbstractCard;
import slimebound.cards.AbstractSlimeboundCard;

public class SelfDamageVariable extends DynamicVariable
{
    @Override
    public String key()
    {
        return "SlimeboundSelfharm";
    }

    @Override
    public boolean isModified(AbstractCard card)
    {
        if (card instanceof AbstractSlimeboundCard){
        AbstractSlimeboundCard asc = (AbstractSlimeboundCard)card;
        return asc.isSelfDamageModified;} else {return false;}
    }

    @Override
    public int value(AbstractCard card)
    {
        if (card instanceof AbstractSlimeboundCard){
            AbstractSlimeboundCard asc = (AbstractSlimeboundCard)card;
            return asc.selfDamage;} else {return 0;}    }

    @Override
    public int baseValue(AbstractCard card)
    {
        if (card instanceof AbstractSlimeboundCard){
            AbstractSlimeboundCard asc = (AbstractSlimeboundCard)card;
            return asc.selfDamage;} else {return 0;}    }

    @Override
    public boolean upgraded(AbstractCard card)
    {
        if (card instanceof AbstractSlimeboundCard){
            AbstractSlimeboundCard asc = (AbstractSlimeboundCard)card;
            return asc.upgradeSelfDamage;} else {return false;}    }
}