package automaton.cardmods;

import automaton.AutomatonMod;
import automaton.AutomatonTextHelper;
import automaton.FunctionHelper;
import automaton.cards.AbstractBronzeCard;
import automaton.cards.FunctionCard;
import basemod.ReflectionHacks;
import basemod.abstracts.AbstractCardModifier;
import basemod.abstracts.CustomCard;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardSave;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static automaton.FunctionHelper.WITH_DELIMITER;

public class CardEffectsCardMod extends BronzeCardMod {

    public CardSave store;
    public int addName;
    public boolean doUseEffects = true;

    public AbstractBronzeCard stored() {
        if (FunctionHelper.cardModsInfo.containsKey(this)) {
            return (AbstractBronzeCard) FunctionHelper.cardModsInfo.get(this);
        }
        AbstractCard q = CardLibrary.getCopy(store.id, store.upgrades, store.misc);
        q.resetAttributes();
        FunctionHelper.cardModsInfo.put(this, q);
        return (AbstractBronzeCard) q;
    }

    public static String ID = "bronze:CardEffectsCardMod";

    public CardEffectsCardMod(AbstractCard q, int addName) {
        this.addName = addName;
        FunctionHelper.cardModsInfo.put(this, q);
        store = new CardSave(q.cardID, q.timesUpgraded, q.misc);
    }

    public CardEffectsCardMod(CardSave c, int addName) {
        this.addName = addName;
        store = c;
        stored();
    }

    @Override
    public void onInitialApplication(AbstractCard card) {
        if (card instanceof FunctionCard) {
            switch (addName) {
                case 0:
                    ((FunctionCard) card).textPrefix += stored().getAdjective();
                    ((FunctionCard) card).doNothingSpecificInParticular();
                    break;
                case 1:
                    ((FunctionCard) card).textPrefix += stored().getNoun();
                    ((FunctionCard) card).doNothingSpecificInParticular();
                    break;
                case 2:
                case 3:
                    ((FunctionCard) card).textPrefix += stored().getBonusChar();
                    ((FunctionCard) card).doNothingSpecificInParticular();
                    break;
                default:
                    break;
            }

            CustomCard q = stored();
            if ((q.target == AbstractCard.CardTarget.SELF_AND_ENEMY || q.target == AbstractCard.CardTarget.ENEMY) && (card.target != AbstractCard.CardTarget.SELF_AND_ENEMY && card.target != AbstractCard.CardTarget.ENEMY)) {
                card.target = AbstractCard.CardTarget.SELF_AND_ENEMY;
            }

            if (q.type == AbstractCard.CardType.ATTACK && card.type != AbstractCard.CardType.ATTACK) {
                card.type = AbstractCard.CardType.ATTACK;
            }

            if (q.type == AbstractCard.CardType.SKILL) {
                ((FunctionCard) card).setBackgroundTexture("bronzeResources/images/512/bg_skill_function.png", "bronzeResources/images/1024/bg_skill_function.png");
            } else {
                ((FunctionCard) card).setBackgroundTexture("bronzeResources/images/512/bg_attack_function.png", "bronzeResources/images/1024/bg_attack_function.png");
            }

            if (q.cost > card.cost) {
                card.cost = q.cost;
                card.costForTurn = q.cost;
            }
        }
    }

    @Override
    public void onApplyPowers(AbstractCard card) {
        if (doUseEffects) {
            stored().applyPowers();
            card.initializeDescription();
        }
    }

    @Override
    public float modifyDamage(float damage, DamageInfo.DamageType type, AbstractCard card, AbstractMonster target) {
        if (doUseEffects) {
            stored().calculateCardDamage(target);
            card.initializeDescription();
        }
        return damage;
    }
    public boolean isFinalCardEffectsFunction(AbstractCard card) {
        boolean yesIAmTheFinalCardWoo = false;
        for (AbstractCardModifier c : CardModifierManager.getModifiers(card, CardEffectsCardMod.ID)) {
            if (c instanceof CardEffectsCardMod) {
                if (c == this) {
                    yesIAmTheFinalCardWoo = true;
                } else if (((CardEffectsCardMod) c).stored().hasTag(AutomatonMod.ADDS_NO_CARDTEXT) && yesIAmTheFinalCardWoo) {
                } else {
                    yesIAmTheFinalCardWoo = false;
                }
            }
        }
        return yesIAmTheFinalCardWoo;
    }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        String x = AutomatonTextHelper.getInSequenceDescription(stored());
        if (!x.equals("") && !isFinalCardEffectsFunction(card)) {
            x += " NL ";
        }
        return rawDescription + x;
    }

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        if (doUseEffects) {
            card.baseDamage = stored().baseDamage;
            ReflectionHacks.setPrivate(card, AbstractCard.class, "isMultiDamage", ReflectionHacks.getPrivate(stored(), AbstractCard.class, "isMultiDamage"));
            card.baseBlock = stored().baseBlock;
            card.baseMagicNumber = card.magicNumber = stored().baseMagicNumber;
            if (card instanceof AbstractBronzeCard) {
                ((AbstractBronzeCard) card).baseAuto = ((AbstractBronzeCard) card).auto = stored().baseAuto;
            }
            card.applyPowers();
            card.calculateCardDamage(target instanceof AbstractMonster ? (AbstractMonster) target : null);
            stored().use(AbstractDungeon.player, target instanceof AbstractMonster ? (AbstractMonster) target : null);
        }
    }

    @Override
    public AbstractCardModifier makeCopy() {
        if (FunctionHelper.cardModsInfo.containsKey(this)) {
            AbstractCard q = FunctionHelper.cardModsInfo.get(this).makeStatEquivalentCopy();
            q.resetAttributes();
            return new CardEffectsCardMod(q, addName);
        }
        return new CardEffectsCardMod(store, addName);
    }

    @Override
    public String identifier(AbstractCard card) {
        return ID;
    }
}
