package awakenedOne.powers;

import awakenedOne.cards.tokens.spells.AphoticShield;
import awakenedOne.ui.OrbitingSpells;
import awakenedOne.util.Wiz;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;

public class AphoticFountPower extends AbstractAwakenedPower {
    // intellij stuff buff
    public static final String NAME = AphoticFountPower.class.getSimpleName();
    public static final String POWER_ID = makeID(NAME);

    public AphoticFountPower(int amount) {
        super(NAME, PowerType.BUFF, false, AbstractDungeon.player, null, amount);
        updateDescription();
    }

    @Override
    public void onSpecificTrigger() {
        Wiz.applyToSelf(new PlatedArmorPower(AbstractDungeon.player, amount));
    }

    public void updateDescription() {
        description = (DESCRIPTIONS[0] + amount + DESCRIPTIONS[1]);
    }
}