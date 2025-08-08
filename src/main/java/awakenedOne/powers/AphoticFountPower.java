package awakenedOne.powers;

import awakenedOne.cards.tokens.spells.AphoticShield;
import awakenedOne.ui.OrbitingSpells;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.NonStackablePower;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class AphoticFountPower extends AbstractAwakenedPower {
    // intellij stuff buff
    public static final String NAME = AphoticFountPower.class.getSimpleName();
    public static final String POWER_ID = makeID(NAME);

    public AphoticFountPower() {
        super(NAME, PowerType.BUFF, false, AbstractDungeon.player, null, 1);
        updateDescription();
    }

    @Override
    public void onSpecificTrigger() {
        for (int i = 0; i < amount; i++)
            OrbitingSpells.addSpellCard(new AphoticShield());
    }

    public void updateDescription() {
        description = (DESCRIPTIONS[0] + amount + DESCRIPTIONS[1]);
    }
}