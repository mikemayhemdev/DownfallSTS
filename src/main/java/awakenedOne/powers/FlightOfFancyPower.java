package awakenedOne.powers;

import awakenedOne.cardmods.FlyingModifier;
import basemod.helpers.CardModifierManager;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class FlightOfFancyPower extends AbstractAwakenedPower {
    // intellij stuff buff
    public static final String NAME = FlightOfFancyPower.class.getSimpleName();
    public static final String POWER_ID = makeID(NAME);

    public FlightOfFancyPower(int amount) {
        super(NAME, PowerType.BUFF, false, AbstractDungeon.player, null, amount);
    }

    private int triggersThisTurn = 0;

    @Override
    public void atStartOfTurn() {
        triggersThisTurn = 0;
    }

    @Override
    public void onCardDraw(AbstractCard card) {
        if (triggersThisTurn < amount && !CardModifierManager.hasModifier(card, FlyingModifier.MOD_ID)) {
            flash();
            CardModifierManager.addModifier(card, new FlyingModifier());
            card.superFlash(Color.SKY);
            triggersThisTurn += 1;
        }
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }
}