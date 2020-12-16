package automaton.cardmods;

import automaton.actions.RepeatCardAction;
import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class CardEffectsCardMod extends BronzeCardMod {
    private AbstractCard stored;

    public CardEffectsCardMod(AbstractCard q) {
        stored = q;
    }

    @Override
    public void onInitialApplication(AbstractCard card) {
        card.modifyCostForCombat(stored.cost);
    }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        return rawDescription + stored.rawDescription;
    }

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        stored.use(AbstractDungeon.player, target instanceof AbstractMonster ? (AbstractMonster)target : null);
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new CardEffectsCardMod(stored);
    }
}
