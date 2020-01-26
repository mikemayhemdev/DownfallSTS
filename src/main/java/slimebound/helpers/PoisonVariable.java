package slimebound.helpers;

import basemod.abstracts.DynamicVariable;
import com.megacrit.cardcrawl.cards.AbstractCard;
import slimebound.cards.AbstractSlimeboundCard;

public class PoisonVariable extends DynamicVariable
{
    @Override
    public String key()
    {
        return "SlimeboundSlimePoison";
    }

    @Override
    public boolean isModified(AbstractCard card)
    {
        if (card instanceof AbstractSlimeboundCard){
        AbstractSlimeboundCard asc = (AbstractSlimeboundCard)card;
        return asc.isPoisonModified;} else {return false;}
    }

    @Override
    public int value(AbstractCard card)
    {
        if (card instanceof AbstractSlimeboundCard){
            AbstractSlimeboundCard asc = (AbstractSlimeboundCard)card;
            return asc.poison;} else {return 0;}    }

    @Override
    public int baseValue(AbstractCard card)
    {
        if (card instanceof AbstractSlimeboundCard){
            AbstractSlimeboundCard asc = (AbstractSlimeboundCard)card;
            return asc.poison;} else {return 0;}    }

    @Override
    public boolean upgraded(AbstractCard card)
    {
        if (card instanceof AbstractSlimeboundCard){
            AbstractSlimeboundCard asc = (AbstractSlimeboundCard)card;
            return asc.upgradePoison;} else {return false;}    }
}