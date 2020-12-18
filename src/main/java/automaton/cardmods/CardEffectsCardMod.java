package automaton.cardmods;

import automaton.cards.AbstractBronzeCard;
import basemod.ReflectionHacks;
import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardSave;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static automaton.FunctionHelper.WITH_DELIMITER;

@AbstractCardModifier.SaveIgnore // Unfortunately, this card mod can't save since it contains AbstractCard, a field too large to save
public class CardEffectsCardMod extends BronzeCardMod {
    public AbstractCard stored;

    public static String ID = "bronze:CardEffectsCardMod";

    public CardEffectsCardMod(AbstractCard q) {
        stored = q;
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
            String[] splitText = card.rawDescription.split(String.format(WITH_DELIMITER, " NL bronze:Compile"));
            String compileText = splitText[1] + splitText[2];
            x = card.rawDescription.replaceAll(compileText, "");
        } //TODO: This entire thing is terrible and placeholder. Make it good eventually!
        System.out.println(x);
        int y = ((AbstractBronzeCard)card).auto;
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
        if (card instanceof AbstractBronzeCard && stored instanceof AbstractBronzeCard) {
            ((AbstractBronzeCard) card).baseAuto = ((AbstractBronzeCard) card).auto = ((AbstractBronzeCard) stored).baseAuto;
        }
        card.applyPowers();
        card.calculateCardDamage(target instanceof AbstractMonster ? (AbstractMonster)target : null);
        stored.use(AbstractDungeon.player, target instanceof AbstractMonster ? (AbstractMonster) target : null);
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new CardEffectsCardMod(stored);
    }

    @Override
    public String identifier(AbstractCard card) {
        return ID;
    }
}
