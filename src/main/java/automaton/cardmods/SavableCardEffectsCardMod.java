package automaton.cardmods;

import basemod.ReflectionHacks;
import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardSave;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.monsters.AbstractMonster;


public class SavableCardEffectsCardMod extends BronzeCardMod {
    public transient AbstractCard stored;
    public CardSave save;

    public static String ID = "bronze:CardEffectsCardMod";

    public SavableCardEffectsCardMod(CardSave q) {
        stored = CardLibrary.getCopy(q.id, q.upgrades, q.misc);
        save = q;
    }

    @Override
    public void onInitialApplication(AbstractCard card) {
        card.cost += stored.cost;
        card.costForTurn += stored.cost;
    }

    @Override
    public void onApplyPowers(AbstractCard card) {
        stored.applyPowers();
        card.initializeDescription();
    }

    @Override
    public float modifyDamage(float damage, DamageInfo.DamageType type, AbstractCard card, AbstractMonster target) {
        stored.calculateCardDamage(target);
        return damage;
    }

    public static String getRealDesc(AbstractCard card) {
        return CardEffectsCardMod.getRealDesc(card);
    }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        return rawDescription + getRealDesc(stored) + " NL ";
    }

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        card.baseDamage = stored.baseDamage;
        ReflectionHacks.setPrivate(card, AbstractCard.class, "isMultiDamage", ReflectionHacks.getPrivate(stored, AbstractCard.class, "isMultiDamage"));
        card.baseBlock = stored.baseBlock;
        card.baseMagicNumber = card.magicNumber = stored.baseMagicNumber;
        card.applyPowers();
        card.calculateCardDamage(target instanceof AbstractMonster ? (AbstractMonster) target : null);
        stored.use(AbstractDungeon.player, target instanceof AbstractMonster ? (AbstractMonster) target : null);
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new SavableCardEffectsCardMod(save);
    }

    @Override
    public String identifier(AbstractCard card) {
        return ID;
    }
}
