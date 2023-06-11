package collector.powers;

import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static collector.util.Wiz.applyToEnemy;
import static collector.util.Wiz.atb;

public class NextAttackAppliesDoomPower extends AbstractCollectorPower {
    public static final String NAME = "FeelMyPain";
    public static final String POWER_ID = makeID(NAME);
    public static final PowerType TYPE = PowerType.BUFF;
    public static final boolean TURN_BASED = false;

    public NextAttackAppliesDoomPower(int amount) {
        super(NAME, TYPE, TURN_BASED, AbstractDungeon.player, null, amount);
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.type == AbstractCard.CardType.ATTACK && action.target != null && card.target == AbstractCard.CardTarget.ENEMY || card.target == AbstractCard.CardTarget.SELF_AND_ENEMY) {
            flash();
            applyToEnemy((AbstractMonster) action.target, new DoomPower((AbstractMonster) action.target, amount));
            atb(new RemoveSpecificPowerAction(owner, owner, this));
        }
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }

    //TODO: Figure out how it works with aoe cards. Check against Bard's Magical Weapon power
}