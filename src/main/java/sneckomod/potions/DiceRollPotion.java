package sneckomod.potions;

import basemod.abstracts.CustomPotion;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.shrines.WeMeetAgain;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import sneckomod.SneckoMod;

import java.util.ArrayList;

public class DiceRollPotion extends CustomPotion {
    public static final String POTION_ID = "sneckomod:DiceRollPotion";
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);
    public static final String NAME = potionStrings.NAME;
    public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;

    public DiceRollPotion() {
        super(NAME, POTION_ID, PotionRarity.RARE, PotionSize.SPHERE, PotionColor.FAIRY);
        this.labOutlineColor = SneckoMod.placeholderColor;
    }

    @Override
    public void initializeData() {
        this.potency = getPotency();
        this.description = DESCRIPTIONS[0];
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
    }

    @Override
    public void use(AbstractCreature target) {
        ArrayList<AbstractCard> cardsToReward = new ArrayList<>();
        AbstractCard chosenCard = null;

        while (cardsToReward.size() < 3) {
            AbstractCard newCard = SneckoMod.getOffClassCardMatchingPredicate(
                    c -> c.rarity == AbstractCard.CardRarity.UNCOMMON);

            newCard.upgrade();
            if (!cardListDuplicate(cardsToReward, newCard)) {
                cardsToReward.add(newCard);
                if (chosenCard == null) {
                    chosenCard = newCard;
                }
            }
        }
        SneckoMod.addGift(cardsToReward);
    }

    public static boolean cardListDuplicate(ArrayList<AbstractCard> cardsList, AbstractCard card) {
        for (AbstractCard alreadyHave : cardsList) {
            if (alreadyHave.cardID.equals(card.cardID)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean canUse() {
        if (!(AbstractDungeon.getCurrRoom().event instanceof WeMeetAgain)){
            return true;
        } else {
            return false;
        }
    }

    @Override
    public CustomPotion makeCopy() {
        return new DiceRollPotion();
    }

    @Override
    public int getPotency(int ascensionLevel) {
        return 1;
    }
}
