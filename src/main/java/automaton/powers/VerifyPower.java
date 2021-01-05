package automaton.powers;

import automaton.cardmods.CardEffectsCardMod;
import automaton.cards.FunctionCard;
import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class VerifyPower extends AbstractAutomatonPower implements OnCompilePower {
    public static final String NAME = "Verify";
    public static final String POWER_ID = makeID(NAME);
    public static final PowerType TYPE = PowerType.BUFF;
    public static final boolean TURN_BASED = false;

    public VerifyPower(int amount) {
        super(NAME, TYPE, TURN_BASED, AbstractDungeon.player, null, amount);
    }

    @Override
    public void onAfterCardPlayed(AbstractCard function) {
        if (function instanceof FunctionCard) {
            for (int i = 0; i < amount; i++) {
                for (AbstractCardModifier m : CardModifierManager.getModifiers(function, CardEffectsCardMod.ID)) {
                    if (m instanceof CardEffectsCardMod) { // always true
                        ((CardEffectsCardMod) m).stored().fineTune();
                    }
                }
            }
            function.superFlash();
        }
    }

    @Override
    public void receiveCompile(AbstractCard function, boolean forGameplay) {
        if (function instanceof FunctionCard && forGameplay) {
            for (int i = 0; i < amount; i++) {
                for (AbstractCardModifier m : CardModifierManager.getModifiers(function, CardEffectsCardMod.ID)) {
                    if (m instanceof CardEffectsCardMod) { // always true
                        ((CardEffectsCardMod) m).stored().fineTune();
                    }
                }
            }
            function.superFlash();
        }
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }
}
