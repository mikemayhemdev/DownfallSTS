package automaton.powers;

import automaton.cardmods.CardEffectsCardMod;
import automaton.cards.FunctionCard;
import automaton.vfx.FineTuningEffect;
import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class VerifyPower extends AbstractAutomatonPower implements OnCompilePower {
    public static final String NAME = "Verify";
    public static final String POWER_ID = makeID(NAME);
    public static final PowerType TYPE = PowerType.BUFF;
    public static final boolean TURN_BASED = false;

    private int activationsThisTurn = 0;

    public VerifyPower(int amount) {
        super(NAME, TYPE, TURN_BASED, AbstractDungeon.player, null, amount);
    }

    @Override
    public void atStartOfTurn() {
        activationsThisTurn = 0;
    }

    @Override
    public void receiveCompile(AbstractCard function, boolean forGameplay) {
        if (activationsThisTurn < amount && forGameplay) {
            for (AbstractCardModifier m : CardModifierManager.getModifiers(function, CardEffectsCardMod.ID)) {
                if (m instanceof CardEffectsCardMod) {
                    ((CardEffectsCardMod) m).stored().fineTune(true);
                }
            }
            activationsThisTurn++;
        }
    }

    @Override
    public void onCardDraw(AbstractCard card) {
        if (card instanceof FunctionCard) {
            if (activationsThisTurn < amount) {
                AbstractDungeon.effectList.add(new FineTuningEffect(card));
                for (AbstractCardModifier m : CardModifierManager.getModifiers(card, CardEffectsCardMod.ID)) {
                    if (m instanceof CardEffectsCardMod) {
                        ((CardEffectsCardMod) m).stored().fineTune(false);
                    }
                }
                activationsThisTurn++;
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
