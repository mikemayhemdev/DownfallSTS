package automaton.cardmods;

import automaton.cards.AbstractBronzeCard;
import basemod.ReflectionHacks;
import basemod.abstracts.AbstractCardModifier;
import com.google.gson.annotations.Expose;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardSave;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static automaton.FunctionHelper.WITH_DELIMITER;


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
        String x = card.rawDescription;
        if (card.rawDescription.contains(" NL bronze:Compile")) {
            String[] splitText = x.split(String.format(WITH_DELIMITER, " NL bronze:Compile"));
            String compileText = splitText[1] + splitText[2];
            x = x.replace(compileText, "");
        } //TODO: This entire thing is terrible and placeholder. Make it good eventually!
        else if (card.rawDescription.contains("bronze:Compile")) {
            x = ""; // It's over!! If you only have Compile effects, you're gone!!!!!
        } // IT NEVER ENDS!!!!!
        if (card.rawDescription.contains(" π")) {
            String[] splitText = x.split(String.format(WITH_DELIMITER, " π"));
            String compileText = splitText[1] + splitText[2];
            x = x.replace(compileText, "");
        } // This one is for cards with other text that doesn't need to be on the Function.
        if (card.rawDescription.contains(" NL \u00A0 ")) {
            String[] splitText = x.split(String.format(WITH_DELIMITER, " NL \u00A0 "));
            String compileText = splitText[0] + splitText[1];
            x = x.replace(compileText, "");
        } // And for non-Function-relevant text before the main card effects.
        return x.replaceAll("!D!", String.valueOf(card.damage)) .replaceAll("!B!", String.valueOf(card.block)).replaceAll("!M!", String.valueOf(card.magicNumber)).replaceAll("!bauto!", (String.valueOf( ((AbstractBronzeCard)card).auto)));
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
