package awakenedOne.powers;

import awakenedOne.cards.tokens.spells.AphoticShield;
import awakenedOne.ui.OrbitingSpells;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.NonStackablePower;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class AphoticFountPower extends AbstractAwakenedPower implements NonStackablePower {
    // intellij stuff buff
    public static final String NAME = AphoticFountPower.class.getSimpleName();
    public static final String POWER_ID = makeID(NAME);
    private final boolean upgraded;

    public AphoticFountPower(boolean upgraded) {
        super(NAME, PowerType.BUFF, false, AbstractDungeon.player, null, 1);
        updateDescription();
        this.upgraded = upgraded;
    }

    @Override
    public void onSpecificTrigger() {
        AbstractCard toAdd = new AphoticShield();
        if (upgraded) toAdd.upgrade();
        for (int i = 0; i < amount; i++)
            OrbitingSpells.spellCards.add(new AphoticShield());
    }

    @Override
    public boolean isStackable(AbstractPower power) {
        if (power instanceof AphoticFountPower) return ((AphoticFountPower) power).upgraded == upgraded;
        return false;
    }

    public void updateDescription() {
        description = (DESCRIPTIONS[0] + amount + DESCRIPTIONS[1] + (upgraded ? DESCRIPTIONS[2] : "") + DESCRIPTIONS[3]);
    }
}