package automaton.powers;

import automaton.cardmods.CardEffectsCardMod;
import automaton.cards.FunctionCard;
import automaton.vfx.FineTuningEffect;
import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class VerifyPower extends AbstractAutomatonPower {
    public static final String NAME = "Verify";
    public static final String POWER_ID = makeID(NAME);
    public static final PowerType TYPE = PowerType.BUFF;
    public static final boolean TURN_BASED = false;

    public VerifyPower(int amount) {
        super(NAME, TYPE, TURN_BASED, AbstractDungeon.player, null, amount);
    }

    @Override
    public void onCardDraw(AbstractCard card) {
        if (card instanceof FunctionCard) {
            for (int i = 0; i < amount; i++) {
                AbstractDungeon.effectList.add(new FineTuningEffect(card));
                for (AbstractCardModifier m : CardModifierManager.getModifiers(card, CardEffectsCardMod.ID)) {
                    if (m instanceof CardEffectsCardMod) {
                        ((CardEffectsCardMod) m).stored().fineTune(false);
                    }
                }
            }
        }
    }

    @Override
    public void updateDescription() {
        if (amount == 1) {
            description = DESCRIPTIONS[0];
        } else
            description = DESCRIPTIONS[1] + amount + DESCRIPTIONS[2];

    }
}
