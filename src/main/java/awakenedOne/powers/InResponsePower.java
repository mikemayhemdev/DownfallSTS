package awakenedOne.powers;

import awakenedOne.cards.tokens.spells.AbstractSpellCard;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static awakenedOne.AwakenedOneMod.SPELLCARD;

public class InResponsePower extends AbstractAwakenedPower {
    // intellij stuff buff
    public static final String NAME = InResponsePower.class.getSimpleName();
    public static final String POWER_ID = makeID(NAME);

    public InResponsePower(int amount) {
        super(NAME, PowerType.BUFF, false, AbstractDungeon.player, null, amount);
    }

    @Override
    public void onAfterCardPlayed(AbstractCard card) {
        if (card instanceof AbstractSpellCard || card.hasTag(SPELLCARD)) {
            flash();
            addToBot(new GainBlockAction(owner, amount));
        }
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];

    }

}