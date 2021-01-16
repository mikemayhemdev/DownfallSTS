package automaton.potions;


import automaton.FunctionHelper;
import automaton.actions.AddToFuncAction;
import automaton.cards.SpaghettiCode;
import basemod.abstracts.CustomPotion;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import downfall.util.SelectCardsCenteredAction;

import java.util.ArrayList;

import static automaton.cards.AbstractBronzeCard.masterUI;
import static automaton.cards.SpaghettiCode.getRandomEncodeChoices;


public class BuildAFunctionPotion extends CustomPotion {
    public static final String POTION_ID = "bronze:BuildAFunctionPotion";
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);
    public static final String NAME = potionStrings.NAME;
    public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;

    public BuildAFunctionPotion() {
        super(NAME, POTION_ID, PotionRarity.RARE, PotionSize.BOTTLE, PotionColor.ANCIENT);
        this.isThrown = false;
        this.targetRequired = false;
    }

    public void initializeData() {
        this.potency = getPotency();
        if (AbstractDungeon.player != null && potency > 1) {
            if (potency > 2) {
                this.description = potionStrings.DESCRIPTIONS[1] + (potency - 1) + potionStrings.DESCRIPTIONS[3];
            } else {
                this.description = potionStrings.DESCRIPTIONS[1] + (potency - 1) + potionStrings.DESCRIPTIONS[2];
            }
        } else {
            this.description = potionStrings.DESCRIPTIONS[0];
        }
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
    }

    public void use(AbstractCreature target) {
        for (int i = 0; i < (FunctionHelper.max() - FunctionHelper.held.size()); i++) {
            ArrayList<AbstractCard> cardsList = getRandomEncodeChoices();
            addToBot(new SelectCardsCenteredAction(cardsList, 1, masterUI.TEXT[7], (cards) -> {
                AbstractDungeon.actionManager.addToTop(new AddToFuncAction(cards.get(0), null));
            }));
        }
        if (potency > 1) {
            FunctionHelper.doExtraNonSpecificCopy = potency - 1;
        }
    }

    public CustomPotion makeCopy() {
        return new BuildAFunctionPotion();
    }

    public int getPotency(int ascensionLevel) {
        return 1;
    }
}
