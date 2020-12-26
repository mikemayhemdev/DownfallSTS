package automaton.cardmods;

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
        }
    }

    @Override
    public void onApplyPowers(AbstractCard card) {
        stored().applyPowers();
        card.initializeDescription();
    }

    @Override
    public float modifyDamage(float damage, DamageInfo.DamageType type, AbstractCard card, AbstractMonster target) {
        stored().calculateCardDamage(target);
        card.initializeDescription();
        return damage;
    }

    public static String getDynamicValue(AbstractCard card, char key) {
        switch (key) {
            case 'B':
                if (card.isBlockModified) {
                    if (card.block >= card.baseBlock) {
                        return "[#7fff00]" + card.block + "[]";
                    }
                    return "[#ff6563]" + card.block + "[]";
                }
                return Integer.toString(card.baseBlock);
            case 'D':
                if (card.isDamageModified) {
                    if (card.damage >= card.baseDamage) {
                        return "[#7fff00]" + card.damage + "[]";
                    }

                    return "[#ff6563]" + card.damage + "[]";
                }

                return Integer.toString(card.baseDamage);
            case 'M':
                if (card.isMagicNumberModified) {
                    if (card.magicNumber >= card.baseMagicNumber) {
                        return "[#7fff00]" + card.magicNumber + "[]";
                    }

                    return "[#ff6563]" + card.magicNumber + "[]";
                }

                return Integer.toString(card.baseMagicNumber);
            case 'A':
                if (card instanceof AbstractBronzeCard) {
                    if (((AbstractBronzeCard) card).isAutoModified) {
                        if (((AbstractBronzeCard) card).auto >= ((AbstractBronzeCard) card).baseAuto) {
                            return "[#7fff00]" + ((AbstractBronzeCard) card).auto + "[]";
                        }

                        return "[#ff6563]" + ((AbstractBronzeCard) card).auto + "[]";
                    }

                    return Integer.toString(((AbstractBronzeCard) card).baseAuto);
                }
            default:
                return Integer.toString(-99);
        }
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
        if (card.rawDescription.contains(" NL bronze:Encode.")) {
            x = x.replace(" NL bronze:Encode.", "");
        }
        if (x.contains("!D!")) {
            x = x.replaceAll("!D!", getDynamicValue(card, 'D'));
        }
        if (x.contains("!B!")) {
            x = x.replaceAll("!B!", getDynamicValue(card, 'B'));
        }
        if (x.contains("!M!")) {
            x = x.replaceAll("!M!", getDynamicValue(card, 'M'));
        }
        if (x.contains("!bauto!")) {
            x = x.replaceAll("!bauto!", getDynamicValue(card, 'A'));
        }
        return x;
    }

    public boolean isFinalCardEffectsFunction(AbstractCard card) {
        boolean yesIAmTheFinalCardWoo = false;
        for (AbstractCardModifier c : CardModifierManager.getModifiers(card, CardEffectsCardMod.ID)) {
            // Does JAVA let me do this? I sure hope so
            yesIAmTheFinalCardWoo = c == this;
        }
        return yesIAmTheFinalCardWoo;
    }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        String x = getRealDesc(stored());
        if (!x.equals("") && !isFinalCardEffectsFunction(card)) {
            x += " NL ";
        }
        return rawDescription + x;
    }

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
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
