package guardian.helpers;

import basemod.abstracts.DynamicVariable;
import com.megacrit.cardcrawl.cards.AbstractCard;
import guardian.cards.AbstractGuardianCard;

public class SecondaryMagicVariable extends DynamicVariable
{
    @Override
    public String key()
    {
        return "GuardianSecondM";
    }

    @Override
    public boolean isModified(AbstractCard card)
    {
        if (card instanceof AbstractGuardianCard){
            AbstractGuardianCard asc = (AbstractGuardianCard)card;
            return asc.isSecondaryMModified;} else {return false;}
    }

    @Override
    public int value(AbstractCard card)
    {
        if (card instanceof AbstractGuardianCard){
            AbstractGuardianCard asc = (AbstractGuardianCard)card;
            return asc.secondaryM;} else {return 0;}    }

    @Override
    public int baseValue(AbstractCard card)
    {
        if (card instanceof AbstractGuardianCard){
            AbstractGuardianCard asc = (AbstractGuardianCard)card;
            return asc.secondaryM;} else {return 0;}    }

    @Override
    public boolean upgraded(AbstractCard card)
    {
        if (card instanceof AbstractGuardianCard){
            AbstractGuardianCard asc = (AbstractGuardianCard)card;
            return asc.isSecondaryMModified;} else {return false;}    }
}