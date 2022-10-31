package champ.potions;


import basemod.abstracts.CustomPotion;
import champ.ChampMod;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import downfall.util.SelectCardsCenteredAction;

import java.util.ArrayList;


public class OpenerPotion extends CustomPotion {
    public static final String POTION_ID = "champ:OpenerPotion";
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);
    public static final String NAME = potionStrings.NAME;
    public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;

    public OpenerPotion() {
        super(NAME, POTION_ID, PotionRarity.COMMON, PotionSize.H, PotionColor.SMOKE);
        this.isThrown = false;
        this.targetRequired = false;
        this.labOutlineColor = ChampMod.potionLabColor;
    }


    public void initializeData() {
        this.potency = getPotency();
        if (AbstractDungeon.player != null && potency > 1) {
            if (potency > 2) {
                this.description = potionStrings.DESCRIPTIONS[0] + potionStrings.DESCRIPTIONS[1] + potency + potionStrings.DESCRIPTIONS[3];
            } else {
                this.description = potionStrings.DESCRIPTIONS[0] + potionStrings.DESCRIPTIONS[1] + potency + potionStrings.DESCRIPTIONS[2];
            }
        } else {
            this.description = potionStrings.DESCRIPTIONS[0];
        }
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
    }

    public void use(AbstractCreature target) {
        ArrayList<AbstractCard> cardsList = generateColorlessCardChoices();
        addToBot(new SelectCardsCenteredAction(cardsList, "Choose.", (cards) -> {
            for (int i = 0; i < potency; i++) {
                AbstractCard card = cards.get(0).makeStatEquivalentCopy();
                card.setCostForTurn(0);
                AbstractDungeon.actionManager.addToTop(new MakeTempCardInHandAction(card));
            }
        }));
    }

    private ArrayList<AbstractCard> generateColorlessCardChoices() {
        ArrayList<AbstractCard> choiceList = new ArrayList<>();
        ArrayList<AbstractCard> bersOpenersList = new ArrayList<>();
        ArrayList<AbstractCard> defOpenersList = new ArrayList<>();
        for (AbstractCard q : CardLibrary.getAllCards()) {
            if (q.hasTag(ChampMod.OPENERBERSERKER)) bersOpenersList.add(q.makeCopy());
            if (q.hasTag(ChampMod.OPENERDEFENSIVE)) defOpenersList.add(q.makeCopy());
        }
        choiceList.add(bersOpenersList.get(AbstractDungeon.cardRandomRng.random(bersOpenersList.size() - 1)));
        choiceList.add(defOpenersList.get(AbstractDungeon.cardRandomRng.random(defOpenersList.size() - 1)));
        return choiceList;
    }

    public CustomPotion makeCopy() {
        return new OpenerPotion();
    }

    public int getPotency(int ascensionLevel) {
        return 1;
    }
}


