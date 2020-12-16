package automaton.cardmods;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class DealDamageMod extends AbstractAutomatonMod {

    //These don't need on remove stuff.

    private int damageAdd;

    public DealDamageMod(int damage) {
        this.damageAdd = damage;
    }

    @Override
    public void onInitialApplication(AbstractCard card) {
        if (card.baseDamage == -1) {
            card.baseDamage = damageAdd;
        }
        else {
            card.baseDamage += damageAdd;
        }
        if (card.target != AbstractCard.CardTarget.ENEMY) {
            card.target = AbstractCard.CardTarget.ENEMY; // This could have more nuance?
        }
        if (card.type != AbstractCard.CardType.ATTACK) {
            card.type = AbstractCard.CardType.ATTACK;
        }
    }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        return rawDescription + " NL Deal !D! damage.";
    }

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        atb(new DamageAction(target, new DamageInfo(AbstractDungeon.player, card.damage, card.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new DealDamageMod(damageAdd);
    }
}
