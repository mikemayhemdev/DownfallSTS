package automaton.cardmods;

import basemod.ReflectionHacks;
import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

@AbstractCardModifier.SaveIgnore // Unfortunately, this card mod can't save since it contains AbstractCard, a field too large to save
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
    public void onApplyPowers(AbstractCard card) {
        stored.applyPowers();
    }

    @Override
    public float modifyDamage(float damage, DamageInfo.DamageType type, AbstractCard card, AbstractMonster target) {
        stored.calculateCardDamage(target);
        return damage;
    }

    public static String getRealDesc(AbstractCard card) {
        return card.rawDescription.replaceAll("!D!", String.valueOf(card.damage)) .replaceAll("!B!", String.valueOf(card.block).replaceAll("!M!", String.valueOf(card.magicNumber)));
    }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        stored.applyPowers();
        return rawDescription + getRealDesc(stored) + " NL ";
    }

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        card.baseDamage = stored.baseDamage;
        ReflectionHacks.setPrivate(card, AbstractCard.class, "isMultiDamage", ReflectionHacks.getPrivate(stored, AbstractCard.class, "isMultiDamage"));
        card.baseBlock = stored.baseBlock;
        System.out.print(stored.baseMagicNumber);
        card.baseMagicNumber = card.magicNumber = stored.baseMagicNumber;
        System.out.print(card.baseMagicNumber + " , " + card.magicNumber);
        card.applyPowers();
        card.calculateCardDamage(target instanceof AbstractMonster ? (AbstractMonster)target : null);
        stored.use(AbstractDungeon.player, target instanceof AbstractMonster ? (AbstractMonster) target : null);
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new CardEffectsCardMod(stored);
    }
}
