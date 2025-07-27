package awakenedOne.powers;

import awakenedOne.actions.ConjureAction;
import awakenedOne.cards.tokens.spells.AbstractSpellCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static awakenedOne.util.Wiz.atb;

public class EmpressPower extends AbstractAwakenedPower {
    // intellij stuff buff
    public static final String NAME = EmpressPower.class.getSimpleName();
    public static final String POWER_ID = makeID(NAME);

    public EmpressPower(AbstractCreature owner, int amount) {
        super(NAME, PowerType.BUFF, false, AbstractDungeon.player, null, amount);
        updateDescription();
    }

    @Override
    public void onAfterCardPlayed(AbstractCard card) {
        if (card.type == AbstractCard.CardType.POWER) {
            for (int i = 0; i < this.amount; i++) {
                flash();
                atb(new ConjureAction(false));
            }
        }
    }

    public void updateDescription() {
        if (this.amount == 1) {
            description = DESCRIPTIONS[0];
        } else {
            description = DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2];
        }
    }

}