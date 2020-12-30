package collector.util;

import basemod.abstracts.DynamicVariable;
import collector.cards.AbstractCollectorCard;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class CollectorSecondDamage extends DynamicVariable {

    //For in-depth comments, check the other variable(DefaultCustomVariable). It's nearly identical.

    @Override
    public String key() {
        return "collector:AltD";
        // This is what you put between "!!" in your card strings to actually display the number.
        // You can name this anything (no spaces), but please pre-phase it with your mod name as otherwise mod conflicts can occur.
        // Remember, we're using makeID so it automatically puts "theDefault:" (or, your id) before the name.
    }

    @Override
    public boolean isModified(AbstractCard card) {
        return ((AbstractCollectorCard) card).isCollectorSecondDamageModified;

    }

    @Override
    public int value(AbstractCard card) {
        return ((AbstractCollectorCard) card).CollectorSecondDamage;
    }

    @Override
    public int baseValue(AbstractCard card) {
        return ((AbstractCollectorCard ) card).BaseCollectorSecondDamage;
    }

    @Override
    public boolean upgraded(AbstractCard card) {
        return ((AbstractCollectorCard ) card).upgradedCollectorSecondDamage;
    }
}