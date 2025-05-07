package awakenedOne.powers;

import awakenedOne.cards.tokens.spells.AbstractSpellCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

//unused

public class MazalethsMadnessPower extends AbstractAwakenedPower {
    // intellij stuff buff
    public static final String NAME = MazalethsMadnessPower.class.getSimpleName();
    public static final String POWER_ID = makeID(NAME);

    public MazalethsMadnessPower(int amount) {
        super(NAME, PowerType.BUFF, false, AbstractDungeon.player, null, amount);
    }

    @Override
    public void onAfterCardPlayed(AbstractCard card) {
        if (card instanceof AbstractSpellCard) {
            flash();
            addToBot(new DamageRandomEnemyAction(new DamageInfo(owner, amount, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        }
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

}