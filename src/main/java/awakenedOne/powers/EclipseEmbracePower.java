package awakenedOne.powers;

import automaton.cards.goodstatus.IntoTheVoid;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.DrawCardNextTurnPower;
import com.megacrit.cardcrawl.powers.EnergizedBluePower;

public class EclipseEmbracePower extends AbstractAwakenedPower {
    // intellij stuff buff
    public static final String NAME = EclipseEmbracePower.class.getSimpleName();
    public static final String POWER_ID = makeID(NAME);

    public EclipseEmbracePower(int amount) {
        super(NAME, PowerType.BUFF, false, AbstractDungeon.player, null, amount);
    }

    @Override
    public void onExhaust(AbstractCard card) {
        if (card instanceof VoidCard || card instanceof IntoTheVoid) {
            this.flash();
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new EnergizedBluePower(AbstractDungeon.player, amount)));
            applyToSelf(new DrawCardNextTurnPower(AbstractDungeon.player, amount));
        }
    }

    @Override
    public void updateDescription() {
        if (amount == 1) {
            description = DESCRIPTIONS[0];
        } else {
            description = DESCRIPTIONS[1] + amount + DESCRIPTIONS[2] + amount + DESCRIPTIONS[3];
        }
    }
}